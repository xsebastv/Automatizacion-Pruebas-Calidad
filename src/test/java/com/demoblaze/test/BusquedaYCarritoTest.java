package com.demoblaze.test;

import com.demoblaze.pages.CartPage;
import com.demoblaze.pages.ProductDetailPage;
import com.demoblaze.pages.ProductPage;
import com.demoblaze.utils.ExcelReader;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Test de Búsqueda y Agregado al Carrito
 * Lee productos desde Excel, los busca y agrega al carrito
 */
public class BusquedaYCarritoTest extends BaseTest {

    private ProductPage productPage;
    private ProductDetailPage productDetailPage;
    private CartPage cartPage;
    private ExcelReader excelReader;
    private String excelPath;
    private List<String> productosAgregados;

    @BeforeClass
    public void setupTest() {
        productPage = new ProductPage(driver);
        productDetailPage = new ProductDetailPage(driver);
        cartPage = new CartPage(driver);
        excelPath = "src/main/resources/testData.xlsx";
        excelReader = new ExcelReader(excelPath);
        productosAgregados = new ArrayList<>();
        
        logWriter.logSection("PRUEBA DE BÚSQUEDA Y AGREGADO AL CARRITO");
    }

    @Test(priority = 1, description = "Buscar productos desde Excel y agregarlos al carrito")
    public void testBuscarYAgregarProductos() {
        // Leer productos desde Excel
        List<Map<String, String>> productos = excelReader.readProductosBusqueda();
        
        logWriter.logMessage("Productos a buscar y agregar: " + productos.size());
        
        SoftAssert softAssert = new SoftAssert();
        
        for (Map<String, String> producto : productos) {
            String categoria = producto.get("Categoria");
            String subCategoria = producto.get("SubCategoria");
            String nombreProducto = producto.get("Producto");
            String cantidad = producto.get("Cantidad");
            
            logWriter.logMessage("\n--- Procesando producto: " + nombreProducto + " ---");
            
            try {
                // Navegar a la página principal
                productPage.navigateToHomePage();
                Thread.sleep(1000);
                
                // Opción 1: Buscar por categoría si está especificada
                if (categoria != null && !categoria.isEmpty()) {
                    productPage.searchByCategory(categoria, subCategoria);
                }
                
                // Opción 2: Buscar por nombre de producto
                productPage.searchProduct(nombreProducto);
                Thread.sleep(1000);
                
                // Verificar que el producto aparece en los resultados
                boolean productoEncontrado = productPage.isProductDisplayed(nombreProducto);
                
                if (productoEncontrado) {
                    logWriter.logMessage("Producto encontrado: " + nombreProducto);
                    softAssert.assertTrue(true, "Producto encontrado: " + nombreProducto);
                    
                    // Hacer clic en el producto para ir a la página de detalle
                    productPage.clickOnProduct(nombreProducto);
                    Thread.sleep(1000);
                    
                    // Agregar al carrito con la cantidad especificada
                    boolean agregadoExitoso = productDetailPage.addToCartWithQuantity(cantidad);
                    
                    if (agregadoExitoso) {
                        productosAgregados.add(nombreProducto);
                        logWriter.logProductoAgregado(
                            categoria != null ? categoria : "N/A",
                            subCategoria != null ? subCategoria : "N/A",
                            nombreProducto,
                            cantidad,
                            true
                        );
                        softAssert.assertTrue(true, "Producto agregado al carrito: " + nombreProducto);
                    } else {
                        logWriter.logProductoAgregado(
                            categoria != null ? categoria : "N/A",
                            subCategoria != null ? subCategoria : "N/A",
                            nombreProducto,
                            cantidad,
                            false
                        );
                        softAssert.fail("No se pudo agregar al carrito: " + nombreProducto);
                    }
                } else {
                    logWriter.logMessage("Producto NO encontrado: " + nombreProducto);
                    logWriter.logProductoAgregado(
                        categoria != null ? categoria : "N/A",
                        subCategoria != null ? subCategoria : "N/A",
                        nombreProducto,
                        cantidad,
                        false
                    );
                    softAssert.fail("Producto no encontrado en resultados: " + nombreProducto);
                }
                
                Thread.sleep(1000);
                
            } catch (Exception e) {
                logWriter.logMessage("Excepción al procesar producto " + nombreProducto + ": " + e.getMessage());
                logWriter.logProductoAgregado(
                    categoria != null ? categoria : "N/A",
                    subCategoria != null ? subCategoria : "N/A",
                    nombreProducto,
                    cantidad,
                    false
                );
                softAssert.fail("Excepción: " + e.getMessage());
            }
        }
        
        // Resumen de productos agregados
        logWriter.logMessage("\n=== RESUMEN ===");
        logWriter.logMessage("Total productos procesados: " + productos.size());
        logWriter.logMessage("Total productos agregados al carrito: " + productosAgregados.size());
        
        softAssert.assertAll();
    }

    @Test(priority = 2, description = "Verificar productos en el carrito", 
          dependsOnMethods = "testBuscarYAgregarProductos")
    public void testVerificarProductosEnCarrito() {
        logWriter.logSection("VERIFICACIÓN DE PRODUCTOS EN EL CARRITO");
        
        // Navegar al carrito
        cartPage.navigateToCart();
        
        try {
            Thread.sleep(2000);
            
            // Verificar si el carrito está vacío
            if (cartPage.isCartEmpty()) {
                logWriter.logMessage("El carrito está vacío");
                logWriter.logVerificacionCarrito(productosAgregados.size(), 0, false);
                Assert.fail("El carrito está vacío, pero se esperaban productos");
                return;
            }
            
            // Obtener productos en el carrito
            List<String> productosEnCarrito = cartPage.getProductNamesInCart();
            int productosEncontrados = productosEnCarrito.size();
            
            logWriter.logMessage("Productos en el carrito: " + productosEncontrados);
            for (String producto : productosEnCarrito) {
                logWriter.logMessage("  - " + producto);
            }
            
            // Verificar que los productos agregados están en el carrito
            SoftAssert softAssert = new SoftAssert();
            
            for (String productoEsperado : productosAgregados) {
                boolean encontrado = cartPage.isProductInCart(productoEsperado);
                
                if (encontrado) {
                    String cantidad = cartPage.getProductQuantity(productoEsperado);
                    logWriter.logMessage("✓ Producto verificado: " + productoEsperado + " (Cantidad: " + cantidad + ")");
                    softAssert.assertTrue(true, "Producto en carrito: " + productoEsperado);
                } else {
                    logWriter.logMessage("✗ Producto NO encontrado: " + productoEsperado);
                    softAssert.fail("Producto no encontrado en carrito: " + productoEsperado);
                }
            }
            
            // Log de verificación final
            boolean verificacionExitosa = productosAgregados.size() == productosEncontrados;
            logWriter.logVerificacionCarrito(
                productosAgregados.size(),
                productosEncontrados,
                verificacionExitosa
            );
            
            softAssert.assertAll();
            
        } catch (Exception e) {
            logWriter.logMessage("Error al verificar carrito: " + e.getMessage());
            logWriter.logVerificacionCarrito(productosAgregados.size(), 0, false);
            Assert.fail("Excepción al verificar carrito: " + e.getMessage());
        }
    }
}

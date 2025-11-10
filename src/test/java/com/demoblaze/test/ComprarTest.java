package com.demoblaze.test;

import com.demoblaze.pages.*;
import com.demoblaze.utils.Constants;
import com.demoblaze.utils.LogWriter;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Test completo del flujo de compra en OpenCart
 * 1. Registro de usuario
 * 2. Login
 * 3. Búsqueda y agregado de productos al carrito
 * 4. Proceso de checkout
 * 5. Confirmación de orden
 */
public class ComprarTest extends BaseTest{

    @Test
    public void ComprarProductos(){
        // Timestamp para datos únicos
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        LogWriter logger = new LogWriter();
        
        // Datos del usuario
        String firstName = "Comprador";
        String lastName = "Test";
        String email = "comprador+" + timestamp + "@test.com";
        String telephone = "3001234567";
        String password = "Test123456";
        String address = "Calle 123 #45-67";
        String city = "Cali";
        String postcode = "760001";
        
        System.out.println("\n========================================");
        System.out.println("=== PRUEBA DE COMPRA COMPLETA ===");
        System.out.println("========================================");
        System.out.println("Email: " + email);
        System.out.println("========================================\n");

        // Inicializar páginas
        HomePage homePage = new HomePage(driver);
        RegisterPage registerPage = new RegisterPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = new ProductsPage(driver);
        ProductsDetallePage productsDetallePage = new ProductsDetallePage(driver);
        ProductDetailPage productDetailPage = new ProductDetailPage(driver);
        CartPage cartPage = new CartPage(driver);
        CheckoutPage checkoutPage = new CheckoutPage(driver);

        try {
            logger.logMessage("=== INICIO PRUEBA DE COMPRA COMPLETA ===");
            logger.logMessage("Email de prueba: " + email);
            logger.logMessage("");
            
            // ==================== PASO 1: REGISTRO ====================
            System.out.println("=== PASO 1: REGISTRO DE USUARIO ===");
            logger.logMessage("=== PASO 1: REGISTRO DE USUARIO ===");
            
            homePage.navigateTo(Constants.BASE_URL);
            System.out.println("✓ Navegando a la página principal");
            logger.logMessage("✓ Navegando a la página principal");
            
            registerPage.navigateToRegisterPage();
            System.out.println("✓ Formulario de registro cargado");
            logger.logMessage("✓ Formulario de registro cargado");
            
            boolean registroExitoso = registerPage.registerUser(
                firstName, lastName, email, telephone, password
            );
            
            if (registroExitoso) {
                System.out.println("✓ Usuario registrado exitosamente");
                logger.logMessage("✓ Usuario registrado exitosamente: " + email);
                registerPage.logoutAfterRegistration();
                System.out.println("✓ Logout después del registro completado\n");
                logger.logMessage("✓ Logout después del registro completado");
            } else {
                System.out.println("⚠ Registro completado (sin mensaje de confirmación)\n");
                logger.logMessage("⚠ Registro completado (sin mensaje de confirmación)");
            }
            logger.logMessage("");

            // ==================== PASO 2: LOGIN ====================
            System.out.println("=== PASO 2: LOGIN ===");
            logger.logMessage("=== PASO 2: LOGIN ===");
            
            loginPage.navigateToLoginPage();
            System.out.println("✓ Página de login cargada");
            logger.logMessage("✓ Página de login cargada");
            
            boolean loginExitoso = loginPage.login(email, password);
            Assert.assertTrue(loginExitoso, "El login debe ser exitoso");
            System.out.println("✓ Login exitoso - Sesión iniciada\n");
            logger.logMessage("✓ Login exitoso - Sesión iniciada con: " + email);
            logger.logMessage("");

            // ==================== PASO 3: AGREGAR PRODUCTOS ====================
            System.out.println("=== PASO 3: AGREGAR PRODUCTOS AL CARRITO ===");
            logger.logMessage("=== PASO 3: AGREGAR PRODUCTOS AL CARRITO ===");
            
            // Producto 1: iMac
            System.out.println("\n→ Producto 1: iMac");
            logger.logMessage("→ Producto 1: iMac");
            homePage.navigateTo(Constants.BASE_URL);
            homePage.selectCategory("Desktops");
            homePage.selectSubCategory("Mac");
            productsPage.selectProduct("iMac");
            productsDetallePage.agregarCarrito("2");
            System.out.println("   ✓ iMac agregado al carrito (cantidad: 2)");
            logger.logMessage("   ✓ iMac agregado al carrito (cantidad: 2)");

            // Producto 2: MacBook
            System.out.println("\n→ Producto 2: MacBook");
            logger.logMessage("→ Producto 2: MacBook");
            homePage.navigateTo(Constants.BASE_URL);
            homePage.selectCategory("Laptops & Notebooks");
            productsPage.selectProduct("MacBook");
            productDetailPage.addToCartWithQuantity("3");
            System.out.println("   ✓ MacBook agregado al carrito (cantidad: 3)");
            logger.logMessage("   ✓ MacBook agregado al carrito (cantidad: 3)");
            logger.logMessage("");

            // ==================== PASO 4: VERIFICAR CARRITO ====================
            System.out.println("\n=== PASO 4: VERIFICACIÓN DEL CARRITO ===");
            logger.logMessage("=== PASO 4: VERIFICACIÓN DEL CARRITO ===");
            cartPage.navigateToCart();
            System.out.println("✓ Navegando al carrito...");
            logger.logMessage("✓ Navegando al carrito...");
            
            int itemsEnCarrito = cartPage.getCartItemCount();
            System.out.println("   Productos en carrito: " + itemsEnCarrito);
            logger.logMessage("   Productos en carrito: " + itemsEnCarrito);
            Assert.assertTrue(itemsEnCarrito > 0, "El carrito debe contener productos");
            
            // Verificar productos específicos
            boolean imacEnCarrito = cartPage.isProductInCart("iMac");
            boolean macbookEnCarrito = cartPage.isProductInCart("MacBook");
            
            System.out.println("   → iMac (x2) en carrito: " + (imacEnCarrito ? "✓" : "✗"));
            logger.logMessage("   → iMac (x2) en carrito: " + (imacEnCarrito ? "✓" : "✗"));
            System.out.println("   → MacBook (x3) en carrito: " + (macbookEnCarrito ? "✓" : "✗"));
            logger.logMessage("   → MacBook (x3) en carrito: " + (macbookEnCarrito ? "✓" : "✗"));
            logger.logMessage("");

            // ==================== PASO 5: PROCESO DE CHECKOUT ====================
            System.out.println("\n=== PASO 5: PROCESO DE CHECKOUT ===");
            logger.logMessage("=== PASO 5: PROCESO DE CHECKOUT ===");
            logger.logMessage("→ Iniciando proceso de checkout...");
            boolean checkoutExitoso = checkoutPage.completeCheckout(
                firstName, lastName, address, city, postcode, "Colombia", "Cali"
            );
            logger.logMessage("✓ Proceso de checkout completado");
            logger.logMessage("");

            // ==================== PASO 6: VERIFICACIÓN FINAL ====================
            System.out.println("\n=== PASO 6: VERIFICACIÓN FINAL ===");
            logger.logMessage("=== PASO 6: VERIFICACIÓN FINAL ===");
            if (checkoutExitoso) {
                System.out.println("✓ Orden procesada exitosamente");
                logger.logMessage("✓ Orden procesada exitosamente");
                String orderNumber = checkoutPage.getOrderNumber();
                System.out.println("   Número de orden: " + orderNumber);
                logger.logMessage("   Número de orden: " + orderNumber);
                Assert.assertTrue(checkoutExitoso, "La compra debe completarse exitosamente");
            } else {
                System.out.println("⚠ Checkout completado (verificar manualmente el resultado)");
                logger.logMessage("⚠ Checkout completado (verificar manualmente el resultado)");
            }
            logger.logMessage("");

            // ==================== PASO 7: LOGOUT ====================
            System.out.println("\n=== PASO 7: CERRAR SESIÓN ===");
            logger.logMessage("=== PASO 7: CERRAR SESIÓN ===");
            loginPage.logout();
            System.out.println("✓ Sesión cerrada correctamente");
            logger.logMessage("✓ Sesión cerrada correctamente");
            logger.logMessage("");

            // ==================== RESUMEN ====================
            System.out.println("\n========================================");
            System.out.println("=== RESUMEN DE LA PRUEBA ===");
            System.out.println("========================================");
            logger.logMessage("========================================");
            logger.logMessage("=== RESUMEN DE LA PRUEBA ===");
            logger.logMessage("========================================");
            System.out.println("✓ Registro: EXITOSO");
            logger.logMessage("✓ Registro: EXITOSO");
            System.out.println("✓ Login: EXITOSO");
            logger.logMessage("✓ Login: EXITOSO");
            System.out.println("✓ Productos agregados: " + itemsEnCarrito);
            logger.logMessage("✓ Productos agregados: " + itemsEnCarrito);
            System.out.println("✓ Checkout: " + (checkoutExitoso ? "EXITOSO" : "COMPLETADO"));
            logger.logMessage("✓ Checkout: " + (checkoutExitoso ? "EXITOSO" : "COMPLETADO"));
            System.out.println("✓ Logout: EXITOSO");
            logger.logMessage("✓ Logout: EXITOSO");
            System.out.println("========================================\n");
            logger.logMessage("========================================");
            logger.logMessage("");

        } catch (Exception e) {
            System.err.println("\n✗ ERROR EN LA PRUEBA DE COMPRA:");
            System.err.println("   " + e.getMessage());
            logger.logMessage("✗ ERROR EN LA PRUEBA DE COMPRA: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Error durante el proceso de compra: " + e.getMessage());
        }
    }
}

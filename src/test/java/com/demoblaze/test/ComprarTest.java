package com.demoblaze.test;

import com.demoblaze.pages.*;
import com.demoblaze.utils.ExcelReader;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

/**
 * ComprarTest - Flujo E2E de compra en OpenCart
 * 1) (Idempotente) Registra/usa un usuario del Excel
 * 2) Hace login
 * 3) Agrega productos con cantidades específicas
 * 4) Verifica carrito
 * 5) Completa el checkout
 */
public class ComprarTest extends BaseTest {

    private LoginPage loginPage;
    private RegisterPage registerPage;
    private ProductPage productPage;
    private ProductDetailPage productDetailPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;
    private ExcelReader excelReader;

    private String email;
    private String password;

    @BeforeClass
    public void setupTest() {
        loginPage = new LoginPage(driver);
        registerPage = new RegisterPage(driver);
        productPage = new ProductPage(driver);
        productDetailPage = new ProductDetailPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);
        excelReader = new ExcelReader("src/main/resources/testData.xlsx");

        logWriter.logSection("PRUEBA E2E: COMPRA COMPLETA");

        // Tomar un usuario del Excel (tercero si existe, para evitar colisión con LoginTest)
        List<Map<String, String>> usuarios = excelReader.readUsuariosRegistro();
        Map<String, String> user = usuarios.size() >= 3 ? usuarios.get(2) : usuarios.get(0);
        email = user.get("E-Mail");
        password = user.get("Password");
    }

    @Test(priority = 1, description = "Registrar (si es necesario) y hacer login")
    public void testRegistroYLogin() throws InterruptedException {
        // Asegurar no hay sesión activa
        if (loginPage.isUserLoggedIn()) {
            logWriter.logMessage("Sesión activa detectada. Realizando logout previo...");
            loginPage.logout();
            Thread.sleep(1500);
        }

        // Intentar registrar al usuario (idempotente: si ya existe, continúa)
        try {
            registerPage.navigateToRegisterPage();
            boolean registrado = registerPage.registerUser(
                "Comprador", "E2E", email, "3000000000", password
            );
            logWriter.logMessage(registrado ? "Usuario registrado/usable: " + email
                                            : "Usuario ya existente o no registrado, se intentará login: " + email);
        } catch (Exception ignored) {
            logWriter.logMessage("Continuando sin registrar (posible usuario ya existente): " + email);
        }

        // Login
        loginPage.navigateToLoginPage();
        boolean loginOk = loginPage.login(email, password);
        logWriter.logMessage(loginOk ? "✓ Login exitoso: " + email : "✗ Login fallido: " + email);
        Assert.assertTrue(loginOk, "El login debe ser exitoso para continuar la compra");
    }

    @Test(priority = 2, description = "Agregar productos al carrito (2× iMac, 3× MacBook)", dependsOnMethods = "testRegistroYLogin")
    public void testAgregarProductos() throws InterruptedException {
        // Producto 1: iMac ×2
        productPage.navigateToHomePage();
        productPage.searchByCategory("Desktops", "Mac"); // navegación tentativa
        productPage.searchProduct("iMac");
        productPage.clickOnProduct("iMac");
        boolean added1 = productDetailPage.addToCartWithQuantity("2");
        logWriter.logProductoAgregado("Desktops", "Mac", "iMac", "2", added1);
        Assert.assertTrue(added1, "Debe agregarse iMac ×2 al carrito");

        // Producto 2: MacBook ×3
        productPage.navigateToHomePage();
        productPage.searchByCategory("Laptops & Notebooks", "");
        productPage.searchProduct("MacBook");
        productPage.clickOnProduct("MacBook");
        boolean added2 = productDetailPage.addToCartWithQuantity("3");
        logWriter.logProductoAgregado("Laptops & Notebooks", "", "MacBook", "3", added2);
        Assert.assertTrue(added2, "Debe agregarse MacBook ×3 al carrito");

        // Verificación en carrito
        cartPage.navigateToCart();
        Thread.sleep(1500);
        Assert.assertTrue(cartPage.isProductInCart("iMac"), "iMac debe estar en el carrito");
        Assert.assertTrue(cartPage.isProductInCart("MacBook"), "MacBook debe estar en el carrito");

        String q1 = cartPage.getProductQuantity("iMac");
        String q2 = cartPage.getProductQuantity("MacBook");
        logWriter.logMessage("Cantidades en carrito → iMac: " + q1 + ", MacBook: " + q2);
    }

    @Test(priority = 3, description = "Checkout y confirmación de orden", dependsOnMethods = "testAgregarProductos")
    public void testCheckout() {
        // Asumimos que estamos logueados y con carrito lleno
        cartPage.navigateToCart();

        // Datos de facturación (sencillos, válidos para demo)
        String firstName = "Comprador";
        String lastName = "E2E";
        String address = "Calle 5 #10-20";
        String city = "Cali";
        String postcode = "760001";
        String country = "Colombia";           // País visible en dropdown
        String zone = "Valle del Cauca";       // Región específica

        boolean ok = checkoutPage.completeCheckout(
            firstName, lastName, address, city, postcode, country, zone
        );

        logWriter.logMessage(ok ? "✓ Compra confirmada exitosamente" : "✗ La compra no pudo completarse");
        Assert.assertTrue(ok, "La orden debe ser confirmada correctamente");
    }
}

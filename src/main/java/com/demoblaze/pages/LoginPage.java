package com.demoblaze.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Página de Login de OpenCart
 */
public class LoginPage extends BasePage {

    // Localizadores - Combinando ID (rápido) y XPath (flexible)
    @FindBy(name = "email") // name es único y estable
    private WebElement emailInput;

    @FindBy(name = "password") // name es único y estable
    private WebElement passwordInput;

    @FindBy(xpath = "//input[@value='Login']") // XPath para atributo value
    private WebElement loginButton;

    @FindBy(xpath = "//div[contains(@class, 'alert-success')]") // XPath para clase parcial
    private WebElement successMessage;

    @FindBy(xpath = "//div[contains(@class, 'alert-danger')]") // XPath para mensajes de error
    private WebElement errorMessage;

    @FindBy(linkText = "My Account") // linkText es directo para enlaces
    private WebElement myAccountLink;

    @FindBy(linkText = "Logout") // linkText es directo para enlaces
    private WebElement logoutLink;

    // Constructor
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navega a la página de login
     */
    public void navigateToLoginPage() {
        navigateTo("https://opencart.abstracta.us/index.php?route=account/login");
    }

    /**
     * Ingresa el email
     */
    public void enterEmail(String email) {
        writeText(emailInput, email);
    }

    /**
     * Ingresa la contraseña
     */
    public void enterPassword(String password) {
        writeText(passwordInput, password);
    }

    /**
     * Hace clic en el botón Login
     */
    public void clickLogin() {
        clickElement(loginButton);
        waitHelper.customWait(2000); // Espera para la respuesta
    }

    /**
     * Realiza el login completo
     */
    public boolean login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLogin();
        return isLoginSuccessful();
    }

    /**
     * Verifica si el login fue exitoso
     * Login exitoso significa:
     * - Aparece el link "Logout" en el menú (usuario logueado)
     * - URL contiene "account/account" (página de cuenta)
     * - NO hay mensaje de error
     */
    public boolean isLoginSuccessful() {
        try {
            waitHelper.customWait(1500);
            
            // Primero verificar si hay mensaje de error (login fallido)
            if (isErrorMessageDisplayed()) {
                System.out.println("⚠ Login fallido - mensaje de error detectado");
                return false;
            }
            
            // Verificar si está en página de login (no cambió de página = error)
            String currentUrl = getCurrentUrl();
            if (currentUrl.contains("account/login")) {
                System.out.println("⚠ Login fallido - sigue en página de login");
                return false;
            }
            
            // CAMBIO CRÍTICO: Verificar el link "Logout" en lugar de "My Account"
            // porque "My Account" siempre está visible
            boolean hasLogout = isElementDisplayed(logoutLink);
            boolean isAccountPage = currentUrl.contains("account/account");
            
            if (hasLogout && isAccountPage) {
                System.out.println("✓ Login exitoso - Link 'Logout' visible y en página de cuenta");
                return true;
            }
            
            System.out.println("⚠ Login fallido - no cumple criterios de éxito (Logout: " + hasLogout + ", AccountPage: " + isAccountPage + ")");
            return false;
            
        } catch (Exception e) {
            System.out.println("⚠ Login fallido - excepción: " + e.getMessage());
            return false;
        }
    }

    /**
     * Verifica si hay mensaje de error
     */
    public boolean isErrorMessageDisplayed() {
        try {
            return isElementDisplayed(errorMessage);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Obtiene el mensaje de error
     */
    public String getErrorMessage() {
        if (isErrorMessageDisplayed()) {
            return getText(errorMessage);
        }
        return "";
    }

    /**
     * Realiza logout navegando directamente a la URL de logout
     * y borrando todas las cookies para forzar cierre de sesión
     */
    public void logout() {
        try {
            System.out.println("   → Cerrando sesión (con limpieza de cookies)...");
            
            // Navegar directamente a la URL de logout
            navigateTo("https://opencart.abstracta.us/index.php?route=account/logout");
            waitHelper.customWait(1500);
            
            // BORRAR TODAS LAS COOKIES para forzar cierre de sesión
            driver.manage().deleteAllCookies();
            System.out.println("   → Cookies eliminadas");
            waitHelper.customWait(1000);
            
            // Navegar a home para refrescar el estado
            navigateTo("https://opencart.abstracta.us/");
            waitHelper.customWait(1500);
            
            // Verificar que realmente se hizo logout verificando el link "Logout"
            try {
                waitHelper.customWait(1000);
                
                // Verificar que NO esté el link "Logout" (= sesión cerrada)
                boolean stillLoggedIn = isElementDisplayed(logoutLink);
                if (!stillLoggedIn) {
                    System.out.println("✓ Logout exitoso - link 'Logout' desapareció correctamente");
                } else {
                    System.out.println("⚠ ADVERTENCIA: Link 'Logout' aún visible - sesión podría seguir activa");
                }
            } catch (Exception e) {
                // Si hay excepción al buscar el link, probablemente no está (logout exitoso)
                System.out.println("✓ Logout exitoso - link 'Logout' no encontrado");
            }
            
        } catch (Exception e) {
            System.out.println("⚠ Error al hacer logout: " + e.getMessage());
            // Respaldo: borrar cookies y navegar a home
            try {
                driver.manage().deleteAllCookies();
                navigateTo("https://opencart.abstracta.us/");
                waitHelper.customWait(1500);
                System.out.println("   → Respaldo ejecutado - cookies borradas");
            } catch (Exception ex) {
                System.out.println("⚠ Respaldo de logout también falló");
            }
        }
    }
    
    /**
     * Verifica si el usuario está logueado
     * Un usuario está logueado si el link "Logout" está visible en el menú desplegable
     * (el link "My Account" siempre está visible, logueado o no)
     */
    public boolean isUserLoggedIn() {
        try {
            // CAMBIO CRÍTICO: Verificar si existe el link "Logout" en lugar de "My Account"
            // porque "My Account" siempre está visible
            waitHelper.customWait(500);
            boolean hasLogout = isElementDisplayed(logoutLink);
            if (hasLogout) {
                System.out.println("   [Debug] Link 'Logout' detectado - usuario SÍ está logueado");
            } else {
                System.out.println("   [Debug] Link 'Logout' NO detectado - usuario NO está logueado");
            }
            return hasLogout;
        } catch (Exception e) {
            System.out.println("   [Debug] Excepción al verificar login - asumiendo NO logueado");
            return false;
        }
    }
}

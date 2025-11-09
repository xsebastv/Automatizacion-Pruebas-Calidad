package com.demoblaze.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Página de Login de OpenCart
 */
public class LoginPage extends BasePage {

    // Localizadores
    @FindBy(css = "input[name='email']")
    private WebElement emailInput;

    @FindBy(css = "input[name='password']")
    private WebElement passwordInput;

    @FindBy(css = "input[value='Login']")
    private WebElement loginButton;

    @FindBy(css = "div.alert.alert-success")
    private WebElement successMessage;

    @FindBy(css = "div.alert.alert-danger")
    private WebElement errorMessage;

    @FindBy(linkText = "My Account")
    private WebElement myAccountLink;

    @FindBy(linkText = "Logout")
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
     * - Aparece el link "My Account" 
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
            
            // Verificar éxito: link My Account visible Y página account/account
            boolean hasMyAccountLink = isElementDisplayed(myAccountLink);
            boolean isAccountPage = currentUrl.contains("account/account");
            
            if (hasMyAccountLink && isAccountPage) {
                System.out.println("✓ Login exitoso - My Account visible y en página correcta");
                return true;
            }
            
            System.out.println("⚠ Login fallido - no cumple criterios de éxito");
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
            
            // Verificar que realmente se hizo logout
            try {
                // Refrescar la página para asegurar que el estado se actualice
                driver.navigate().refresh();
                waitHelper.customWait(1000);
                
                boolean stillLoggedIn = isElementDisplayed(myAccountLink);
                if (!stillLoggedIn) {
                    System.out.println("✓ Logout exitoso - sesión cerrada correctamente");
                } else {
                    System.out.println("⚠ ADVERTENCIA: My Account aún visible - forzando refresh adicional");
                    driver.navigate().refresh();
                    waitHelper.customWait(1000);
                }
            } catch (Exception e) {
                // Si hay excepción, probablemente no está (logout exitoso)
                System.out.println("✓ Logout exitoso - My Account no encontrado");
            }
            
        } catch (Exception e) {
            System.out.println("⚠ Error al hacer logout: " + e.getMessage());
            // Respaldo: borrar cookies y navegar a home
            try {
                driver.manage().deleteAllCookies();
                navigateTo("https://opencart.abstracta.us/");
                driver.navigate().refresh();
                waitHelper.customWait(1500);
                System.out.println("   → Respaldo ejecutado - cookies borradas y página refrescada");
            } catch (Exception ex) {
                System.out.println("⚠ Respaldo de logout también falló");
            }
        }
    }
    
    /**
     * Verifica si el usuario está logueado
     * Un usuario está logueado si el link "My Account" está visible en el menú superior
     */
    public boolean isUserLoggedIn() {
        try {
            // Refrescar la página para obtener el estado actualizado del DOM
            driver.navigate().refresh();
            waitHelper.customWait(1000);
            
            boolean hasLink = isElementDisplayed(myAccountLink);
            if (hasLink) {
                System.out.println("   [Debug] My Account link detectado - usuario logueado");
            } else {
                System.out.println("   [Debug] My Account link NO detectado - usuario NO logueado");
            }
            return hasLink;
        } catch (Exception e) {
            System.out.println("   [Debug] Excepción al verificar login - asumiendo NO logueado");
            return false;
        }
    }
}

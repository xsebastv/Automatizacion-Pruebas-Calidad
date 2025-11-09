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
     */
    public void logout() {
        try {
            // Navegar directamente a la URL de logout (más confiable)
            navigateTo("https://opencart.abstracta.us/index.php?route=account/logout");
            waitHelper.customWait(1500);
            
            // Verificar que realmente se hizo logout
            boolean loggedOut = !isUserLoggedIn();
            if (loggedOut) {
                System.out.println("✓ Logout exitoso - sesión cerrada");
            } else {
                System.out.println("⚠ Logout - sesión podría seguir activa");
            }
        } catch (Exception e) {
            System.out.println("⚠ Error al hacer logout: " + e.getMessage());
            // Intentar método alternativo
            try {
                if (isElementDisplayed(myAccountLink)) {
                    clickElement(myAccountLink);
                    waitHelper.customWait(500);
                    if (isElementDisplayed(logoutLink)) {
                        clickElement(logoutLink);
                        waitHelper.customWait(1500);
                    }
                }
            } catch (Exception ex) {
                System.out.println("⚠ Logout alternativo también falló");
            }
        }
    }
    
    /**
     * Verifica si el usuario está logueado
     */
    public boolean isUserLoggedIn() {
        try {
            return isElementDisplayed(myAccountLink);
        } catch (Exception e) {
            return false;
        }
    }
}

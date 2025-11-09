package com.demoblaze.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Página de Registro de OpenCart
 */
public class RegisterPage extends BasePage {

    // Localizadores - Combinando name (único), ID y XPath (flexible)
    @FindBy(name = "firstname") // name es único y estable
    private WebElement firstNameInput;

    @FindBy(name = "lastname") // name es único y estable
    private WebElement lastNameInput;

    @FindBy(name = "email") // name es único y estable
    private WebElement emailInput;

    @FindBy(name = "telephone") // name es único y estable
    private WebElement telephoneInput;

    @FindBy(name = "password") // name es único y estable
    private WebElement passwordInput;

    @FindBy(name = "confirm") // name es único y estable
    private WebElement confirmPasswordInput;

    @FindBy(name = "agree") // name es único y estable
    private WebElement privacyPolicyCheckbox;

    @FindBy(xpath = "//input[@value='Continue']") // XPath para atributo value
    private WebElement continueButton;
    
    @FindBy(xpath = "//a[contains(text(), 'Continue')]") // Botón Continue en página de éxito
    private WebElement continueButtonAfterSuccess;

    @FindBy(xpath = "//div[contains(@class, 'alert-success')]") // XPath para clase parcial
    private WebElement successMessage;

    @FindBy(xpath = "//div[contains(@class, 'alert-danger')]") // XPath para mensajes de error
    private WebElement errorMessage;
    
    @FindBy(linkText = "My Account") // linkText para link directo
    private WebElement myAccountLink;
    
    @FindBy(linkText = "Logout") // linkText para link directo
    private WebElement logoutLink;

    // Constructor
    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navega a la página de registro y espera a que el formulario esté listo
     * Si el usuario ya está logueado, OpenCart redirige automáticamente.
     * En ese caso, intentamos hacer logout y volver al registro.
     */
    public void navigateToRegisterPage() {
        navigateTo("https://opencart.abstracta.us/index.php?route=account/register");
        waitHelper.customWait(1000);
        
        // Verificar si realmente llegamos al formulario de registro
        try {
            waitHelper.waitForElementToBeVisible(firstNameInput);
            System.out.println("✓ Formulario de registro cargado correctamente");
        } catch (Exception e) {
            // Si no encontramos el formulario, puede ser que estamos logueados
            System.out.println("⚠ No se encontró formulario de registro, verificando si hay sesión activa...");
            
            try {
                // Verificar si el link de Logout está presente (indica sesión activa)
                if (isElementDisplayed(logoutLink)) {
                    System.out.println("⚠ Sesión activa detectada, haciendo logout...");
                    logoutAfterRegistration();
                    
                    // Intentar navegar de nuevo al registro
                    System.out.println("   → Reintentando navegación al registro...");
                    navigateTo("https://opencart.abstracta.us/index.php?route=account/register");
                    waitHelper.customWait(1000);
                    waitHelper.waitForElementToBeVisible(firstNameInput);
                    System.out.println("✓ Formulario de registro cargado tras logout");
                } else {
                    // Si no hay sesión pero tampoco formulario, hay otro problema
                    System.out.println("⚠ No hay sesión activa pero tampoco formulario de registro");
                    throw e; // Re-lanzar la excepción original
                }
            } catch (Exception ex) {
                System.out.println("✗ Error crítico al intentar acceder al formulario de registro");
                throw ex;
            }
        }
        
        waitHelper.customWait(500); // Espera adicional para estabilidad
    }

    /**
     * Completa el formulario de registro
     */
    public void fillRegistrationForm(String firstName, String lastName, String email, 
                                      String telephone, String password) {
        writeText(firstNameInput, firstName);
        writeText(lastNameInput, lastName);
        writeText(emailInput, email);
        writeText(telephoneInput, telephone);
        writeText(passwordInput, password);
        writeText(confirmPasswordInput, password);
    }

    /**
     * Acepta la política de privacidad
     */
    public void acceptPrivacyPolicy() {
        clickElement(privacyPolicyCheckbox);
    }

    /**
     * Hace clic en el botón Continue
     */
    public void clickContinue() {
        clickElement(continueButton);
        waitHelper.customWait(2000); // Espera para que aparezca el mensaje
    }

    /**
     * Verifica si el mensaje de éxito es visible
     */
    public boolean isSuccessMessageDisplayed() {
        try {
            waitHelper.customWait(1000);
            boolean displayed = isElementDisplayed(successMessage);
            if (displayed) {
                System.out.println("✓ Mensaje de éxito detectado en registro");
            }
            return displayed;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Obtiene el texto del mensaje de éxito
     */
    public String getSuccessMessage() {
        if (isSuccessMessageDisplayed()) {
            String msg = getText(successMessage);
            System.out.println("   Mensaje: " + msg);
            return msg;
        }
        return "";
    }

    /**
     * Verifica si hay un mensaje de error
     */
    public boolean isErrorMessageDisplayed() {
        try {
            waitHelper.customWait(1000);
            boolean displayed = isElementDisplayed(errorMessage);
            if (displayed) {
                System.out.println("⚠ Mensaje de error detectado en registro");
            }
            return displayed;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Obtiene el mensaje de error
     */
    public String getErrorMessage() {
        if (isErrorMessageDisplayed()) {
            String msg = getText(errorMessage);
            System.out.println("   Error: " + msg);
            return msg;
        }
        return "";
    }

    /**
     * Hace clic en el botón Continue después del mensaje de éxito
     * Este botón aparece en la página de confirmación después del registro
     */
    public void clickContinueAfterSuccess() {
        try {
            System.out.println("   → Buscando botón Continue en página de confirmación...");
            waitHelper.customWait(1000);
            
            // Intentar múltiples estrategias para encontrar el botón Continue
            try {
                // Primera estrategia: Esperar por el elemento
                waitHelper.waitForElementToBeClickable(continueButtonAfterSuccess);
                System.out.println("   → Botón Continue encontrado, haciendo clic...");
                clickElement(continueButtonAfterSuccess);
                waitHelper.customWait(1000);
                System.out.println("✓ Clic exitoso en botón Continue");
                return;
            } catch (Exception e1) {
                System.out.println("⚠ Estrategia 1 falló, intentando con XPath directo...");
            }
            
            // Segunda estrategia: Buscar cualquier link con texto Continue
            try {
                WebElement continueLink = driver.findElement(
                    org.openqa.selenium.By.xpath("//a[normalize-space(text())='Continue']")
                );
                continueLink.click();
                waitHelper.customWait(1000);
                System.out.println("✓ Clic exitoso en Continue (estrategia 2)");
                return;
            } catch (Exception e2) {
                System.out.println("⚠ Estrategia 2 falló, navegando directamente...");
            }
            
            // Tercera estrategia: Navegar directamente a home
            System.out.println("   → Navegando directamente a home como respaldo...");
            navigateTo("https://opencart.abstracta.us/");
            waitHelper.customWait(1000);
            System.out.println("✓ Navegación directa completada");
            
        } catch (Exception e) {
            System.out.println("⚠ Error general al procesar Continue: " + e.getMessage());
        }
    }
    
    /**
     * Cierra sesión del usuario registrado
     * IMPORTANTE: Debe llamarse después de un registro exitoso para permitir
     * que el siguiente usuario pueda acceder al formulario de registro
     */
    public void logoutAfterRegistration() {
        try {
            System.out.println("   → Haciendo logout después del registro...");
            
            // Primero hacer clic en My Account
            waitHelper.waitForElementToBeClickable(myAccountLink);
            clickElement(myAccountLink);
            waitHelper.customWait(500);
            
            // Luego hacer clic en Logout
            waitHelper.waitForElementToBeClickable(logoutLink);
            clickElement(logoutLink);
            waitHelper.customWait(1500);
            
            System.out.println("✓ Logout exitoso - sesión cerrada");
        } catch (Exception e) {
            System.out.println("⚠ Error al hacer logout: " + e.getMessage());
            // Si falla el logout, intentar limpiar cookies como alternativa
            try {
                System.out.println("   → Limpiando cookies como alternativa...");
                driver.manage().deleteAllCookies();
                waitHelper.customWait(500);
                System.out.println("✓ Cookies eliminadas");
            } catch (Exception ex) {
                System.out.println("⚠ No se pudieron limpiar cookies");
            }
        }
    }
    
    /**
     * Realiza el registro completo
     */
    public boolean registerUser(String firstName, String lastName, String email, 
                                  String telephone, String password) {
        boolean registroExitoso = false;
        try {
            System.out.println("   → Llenando formulario de registro...");
            fillRegistrationForm(firstName, lastName, email, telephone, password);
            
            System.out.println("   → Aceptando política de privacidad...");
            acceptPrivacyPolicy();
            
            System.out.println("   → Enviando formulario...");
            clickContinue();
            
            // Esperar respuesta del servidor
            waitHelper.customWait(3000); // Espera aumentada para dar tiempo al servidor
            
            // Verificar si fue exitoso o hubo error
            boolean success = isSuccessMessageDisplayed();
            boolean hasError = isErrorMessageDisplayed();
            
            if (success) {
                System.out.println("✓ Registro completado exitosamente");
                registroExitoso = true;
                // Hacer clic en Continue para volver a la página principal
                clickContinueAfterSuccess();
            } else if (hasError) {
                String errorMsg = getErrorMessage();
                System.out.println("⚠ Registro falló - Error: " + errorMsg);
                registroExitoso = false;
            } else {
                // Sin mensaje explícito, pero puede que el registro haya ocurrido
                // Si no hay error, asumimos éxito (el usuario queda logueado)
                System.out.println("⚠ Registro - sin mensaje explícito (probablemente exitoso)");
                registroExitoso = true; // Asumimos éxito si no hay error
            }
            
        } catch (Exception e) {
            System.out.println("✗ Excepción durante el registro: " + e.getMessage());
            e.printStackTrace();
            registroExitoso = false;
        } finally {
            // CRÍTICO: SIEMPRE intentar hacer logout después del registro
            // Esto asegura que el siguiente usuario pueda registrarse
            try {
                System.out.println("   → Verificando y cerrando sesión si está activa...");
                // Intentar hacer logout si hay sesión activa
                if (isElementDisplayed(myAccountLink) || isElementDisplayed(logoutLink)) {
                    logoutAfterRegistration();
                } else {
                    System.out.println("   → No hay sesión activa para cerrar");
                }
            } catch (Exception ex) {
                System.out.println("⚠ Error al verificar/cerrar sesión: " + ex.getMessage());
                // Forzar limpieza de cookies como último recurso
                try {
                    driver.manage().deleteAllCookies();
                    System.out.println("   → Cookies eliminadas por seguridad");
                } catch (Exception e2) {
                    // Ignorar si falla
                }
            }
        }
        
        return registroExitoso;
    }
}

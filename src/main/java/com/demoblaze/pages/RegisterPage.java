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
    
    @FindBy(xpath = "//a[contains(@class, 'btn-primary') and contains(text(), 'Continue')]") // XPath con múltiples condiciones
    private WebElement continueButtonAfterSuccess;

    @FindBy(xpath = "//div[contains(@class, 'alert-success')]") // XPath para clase parcial
    private WebElement successMessage;

    @FindBy(xpath = "//div[contains(@class, 'alert-danger')]") // XPath para mensajes de error
    private WebElement errorMessage;

    // Constructor
    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navega a la página de registro y espera a que el formulario esté listo
     */
    public void navigateToRegisterPage() {
        navigateTo("https://opencart.abstracta.us/index.php?route=account/register");
        // Esperar explícitamente a que el formulario de registro esté visible
        waitHelper.waitForElementToBeVisible(firstNameInput);
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
            waitHelper.customWait(2000); // Espera mayor para que cargue la página
            
            // Intentar esperar explícitamente por el botón
            waitHelper.waitForElementToBeClickable(continueButtonAfterSuccess);
            
            if (isElementDisplayed(continueButtonAfterSuccess)) {
                System.out.println("   → Haciendo clic en Continue...");
                clickElement(continueButtonAfterSuccess);
                waitHelper.customWait(1500);
                System.out.println("✓ Clic exitoso en botón Continue - volviendo a home");
            } else {
                System.out.println("⚠ Botón Continue no visible");
            }
        } catch (Exception e) {
            System.out.println("⚠ Error al buscar botón Continue: " + e.getMessage());
            // Intentar con URL directa como respaldo
            try {
                System.out.println("   → Intentando navegación directa a home...");
                navigateTo("https://opencart.abstracta.us/");
                waitHelper.customWait(1000);
            } catch (Exception ex) {
                System.out.println("⚠ Navegación directa también falló");
            }
        }
    }
    
    /**
     * Realiza el registro completo
     */
    public boolean registerUser(String firstName, String lastName, String email, 
                                  String telephone, String password) {
        try {
            System.out.println("   → Llenando formulario de registro...");
            fillRegistrationForm(firstName, lastName, email, telephone, password);
            
            System.out.println("   → Aceptando política de privacidad...");
            acceptPrivacyPolicy();
            
            System.out.println("   → Enviando formulario...");
            clickContinue();
            
            // Esperar respuesta del servidor
            waitHelper.customWait(1500);
            
            // Verificar si fue exitoso o hubo error
            boolean success = isSuccessMessageDisplayed();
            boolean hasError = isErrorMessageDisplayed();
            
            if (success) {
                System.out.println("✓ Registro completado exitosamente");
                // Hacer clic en Continue para volver a la página principal
                clickContinueAfterSuccess();
                return true;
            } else if (hasError) {
                String errorMsg = getErrorMessage();
                System.out.println("⚠ Registro falló - Error: " + errorMsg);
                return false;
            } else {
                System.out.println("⚠ Registro - sin mensaje de éxito ni error");
                return false;
            }
            
        } catch (Exception e) {
            System.out.println("✗ Excepción durante el registro: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}

package com.demoblaze.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Página de Registro de OpenCart
 */
public class RegisterPage extends BasePage {

    // Localizadores usando @FindBy
    @FindBy(css = "input[name='firstname']")
    private WebElement firstNameInput;

    @FindBy(css = "input[name='lastname']")
    private WebElement lastNameInput;

    @FindBy(css = "input[name='email']")
    private WebElement emailInput;

    @FindBy(css = "input[name='telephone']")
    private WebElement telephoneInput;

    @FindBy(css = "input[name='password']")
    private WebElement passwordInput;

    @FindBy(css = "input[name='confirm']")
    private WebElement confirmPasswordInput;

    @FindBy(css = "input[name='agree']")
    private WebElement privacyPolicyCheckbox;

    @FindBy(css = "input[value='Continue']")
    private WebElement continueButton;

    @FindBy(css = "div.alert.alert-success")
    private WebElement successMessage;

    @FindBy(css = "div.alert.alert-danger")
    private WebElement errorMessage;

    // Constructor
    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navega a la página de registro
     */
    public void navigateToRegisterPage() {
        navigateTo("https://opencart.abstracta.us/index.php?route=account/register");
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
            waitHelper.waitForElementToBeVisible(successMessage);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Obtiene el texto del mensaje de éxito
     */
    public String getSuccessMessage() {
        if (isSuccessMessageDisplayed()) {
            return getText(successMessage);
        }
        return "";
    }

    /**
     * Verifica si hay un mensaje de error
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
     * Realiza el registro completo
     */
    public boolean registerUser(String firstName, String lastName, String email, 
                                  String telephone, String password) {
        fillRegistrationForm(firstName, lastName, email, telephone, password);
        acceptPrivacyPolicy();
        clickContinue();
        return isSuccessMessageDisplayed();
    }
}

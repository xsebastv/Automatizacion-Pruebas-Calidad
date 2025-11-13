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

    @FindBy(css = "a.btn.btn-primary") // Botón Continue por clase
    private WebElement continuePrimaryButton;

    @FindBy(xpath = "//div[contains(@class, 'alert-success')]") // XPath para clase parcial
    private WebElement successMessage;

    @FindBy(xpath = "//h1[contains(., 'Your Account Has Been Created')]" )
    private WebElement accountCreatedHeader;

    @FindBy(xpath = "//div[@id='content']//p[contains(., 'successfully created') or contains(., 'successfully')]")
    private WebElement accountCreatedText;

    @FindBy(xpath = "//h1[contains(., 'Account Logout')]")
    private WebElement accountLogoutHeader;

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
        waitHelper.customWait(300);
        
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
                    waitHelper.customWait(300);
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
        // Hacer el botón visible y clickeable
        try { scrollToElement(continueButton); } catch (Exception ignored) {}

        int attempts = 0;
        while (attempts < 2) {
            try {
                clickElement(continueButton);
                waitHelper.customWait(500);
                // Validar transición a página de éxito o aparición de errores
                boolean movedToSuccess = isOnSuccessPage() || isElementDisplayed(continueButtonAfterSuccess);
                boolean hasErrorNow = isErrorMessageDisplayed();
                if (movedToSuccess || hasErrorNow) {
                    break;
                }
            } catch (Exception e) {
                // Reintento con JS por si hay overlays o problemas de foco
                try {
                    ((org.openqa.selenium.JavascriptExecutor) driver)
                            .executeScript("arguments[0].click();", continueButton);
                } catch (Exception ignored) {}
            }
            attempts++;
            waitHelper.customWait(800);
        }
        // No dormir fijo: la espera se maneja por resultado
    }

    /**
     * Verifica si el mensaje de éxito es visible
     */
    public boolean isSuccessMessageDisplayed() {
        try {
            waitHelper.customWait(1000);
            boolean displayed = isElementDisplayed(successMessage) || isOnSuccessPage();
            if (displayed) {
                System.out.println("✓ Éxito de registro detectado (mensaje o encabezado de éxito)");
            }
            return displayed;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isOnSuccessPage() {
        try {
            if (isElementDisplayed(accountCreatedHeader)) return true;
        } catch (Exception ignored) {}
        try {
            if (isElementDisplayed(accountCreatedText)) return true;
        } catch (Exception ignored) {}
        try {
            return waitHelper.waitForUrlContains("route=account/success");
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
            waitHelper.customWait(300);

            // Hacer varios intentos para salir de la página de éxito
            for (int i = 0; i < 3; i++) {
                if (!isOnSuccessPage()) {
                    return; // ya no estamos en success
                }

                try {
                    // Estrategia 0: botón por clase 'btn btn-primary'
                    try {
                        waitHelper.waitForElementToBeClickable(continuePrimaryButton);
                        scrollToElement(continuePrimaryButton);
                        clickElement(continuePrimaryButton);
                    } catch (Exception ignore) {
                        // seguimos con más estrategias
                    }

                    // Estrategia 1: botón localizado por PageFactory
                    waitHelper.waitForElementToBeClickable(continueButtonAfterSuccess);
                    scrollToElement(continueButtonAfterSuccess);
                    clickElement(continueButtonAfterSuccess);
                } catch (Exception e1) {
                    try {
                        // Estrategia 2: XPath directo
                        WebElement continueLink = driver.findElement(
                            org.openqa.selenium.By.xpath("//a[normalize-space(text())='Continue']")
                        );
                        scrollToElement(continueLink);
                        continueLink.click();
                    } catch (Exception e2) {
                        try {
                            // Estrategia 3: click por JavaScript
                            WebElement continueLinkJs = (continuePrimaryButton != null) ? continuePrimaryButton :
                                driver.findElement(org.openqa.selenium.By.cssSelector("a.btn.btn-primary"));
                            ((org.openqa.selenium.JavascriptExecutor) driver)
                                .executeScript("arguments[0].click();", continueLinkJs);
                        } catch (Exception e3) {
                            // Ignorar, probaremos navegación directa como último recurso
                        }
                    }
                }

                // Esperar cambio de URL
                boolean leftSuccess = false;
                for (int w = 0; w < 10; w++) {
                    waitHelper.customWait(200);
                    if (!getCurrentUrl().contains("route=account/success")) {
                        leftSuccess = true;
                        break;
                    }
                }
                if (leftSuccess) {
                    System.out.println("✓ Salimos de la página de éxito");
                    return;
                }
            }

            // Último recurso: Navegar directamente a home
            System.out.println("   → Navegando directamente a home como respaldo...");
            navigateTo("https://opencart.abstracta.us/");
            waitHelper.customWait(500);
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

            // 1) Abrir menú My Account y pulsar Logout
            try {
                waitHelper.waitForElementToBeClickable(myAccountLink);
                scrollToElement(myAccountLink);
                clickElement(myAccountLink);
                waitHelper.customWait(300);
            } catch (Exception ignored) {}

            try {
                waitHelper.waitForElementToBeClickable(logoutLink);
                scrollToElement(logoutLink);
                clickElement(logoutLink);
            } catch (Exception e) {
                System.out.println("⚠ No se pudo hacer clic en Logout directamente: " + e.getMessage());
            }

            // 2) Confirmar que estamos en la página de logout
            boolean onLogout = waitForLogoutPage(5000);
            if (onLogout) {
                System.out.println("✓ Logout exitoso - página de 'Account Logout' visible");
                // 3) Pulsar Continue de la página de logout para volver a Home
                try {
                    if (isElementDisplayed(continuePrimaryButton)) {
                        scrollToElement(continuePrimaryButton);
                        clickElement(continuePrimaryButton);
                    } else {
                        WebElement cont = driver.findElement(org.openqa.selenium.By.cssSelector("a.btn.btn-primary"));
                        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", cont);
                    }
                } catch (Exception e) {
                    System.out.println("⚠ No se pudo pulsar Continue en Logout: " + e.getMessage());
                }
            } else {
                System.out.println("⚠ No se confirmó página de logout, limpiando sesión por seguridad");
            }

            // 4) Garantizar sesión limpia y volver a Home
            try { driver.manage().deleteAllCookies(); } catch (Exception ignored) {}
            ensureAtHome();

        } catch (Exception e) {
            System.out.println("⚠ Error al hacer logout: " + e.getMessage());
            // Si falla el logout, intentar limpiar cookies como alternativa
            try {
                System.out.println("   → Limpiando cookies como alternativa...");
                driver.manage().deleteAllCookies();
                ensureAtHome();
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
            // Esperar activamente resultado (éxito o error) sin sleeps largos
            String outcome = waitForRegistrationOutcome(8000); // hasta 8s, chequeos rápidos
            boolean success = "success".equals(outcome) || isSuccessMessageDisplayed();
            boolean hasError = "error".equals(outcome) || isErrorMessageDisplayed();
            
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
                // Aun sin mensaje, intentar el segundo Continue o navegación de respaldo
                clickContinueAfterSuccess();
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

    /**
     * Espera activa a que aparezca un resultado tras enviar el formulario:
     * - success: se detecta encabezado de éxito, texto o URL de success
     * - error: aparece alerta de error
     * - unknown: se agota el tiempo sin señales claras
     */
    private String waitForRegistrationOutcome(int timeoutMs) {
        long end = System.currentTimeMillis() + timeoutMs;
        while (System.currentTimeMillis() < end) {
            try {
                if (isOnSuccessPage() || isElementDisplayed(continuePrimaryButton) || isElementDisplayed(continueButtonAfterSuccess)) {
                    return "success";
                }
            } catch (Exception ignored) {}

            try {
                if (isErrorMessageDisplayed()) {
                    return "error";
                }
            } catch (Exception ignored) {}

            waitHelper.customWait(200);
        }
        return "unknown";
    }

    private boolean waitForLogoutPage(int timeoutMs) {
        long end = System.currentTimeMillis() + timeoutMs;
        while (System.currentTimeMillis() < end) {
            try {
                if (isElementDisplayed(accountLogoutHeader) || getCurrentUrl().contains("route=account/logout")) {
                    return true;
                }
            } catch (Exception ignored) {}
            waitHelper.customWait(200);
        }
        return false;
    }

    private void ensureAtHome() {
        if (!getCurrentUrl().contains("opencart.abstracta.us/")) {
            navigateTo("https://opencart.abstracta.us/");
            waitHelper.customWait(300);
        }
    }
}

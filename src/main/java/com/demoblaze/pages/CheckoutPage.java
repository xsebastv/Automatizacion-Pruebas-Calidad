package com.demoblaze.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

/**
 * Página de Checkout de OpenCart
 */
public class CheckoutPage extends BasePage {

    // Localizadores para el proceso de checkout
    @FindBy(linkText = "Checkout")
    private WebElement checkoutButton;

    @FindBy(id = "input-payment-firstname")
    private WebElement firstNameInput;

    @FindBy(id = "input-payment-lastname")
    private WebElement lastNameInput;

    @FindBy(id = "input-payment-address-1")
    private WebElement addressInput;

    @FindBy(id = "input-payment-city")
    private WebElement cityInput;

    @FindBy(id = "input-payment-postcode")
    private WebElement postcodeInput;

    @FindBy(id = "input-payment-country")
    private WebElement countrySelect;

    @FindBy(id = "input-payment-zone")
    private WebElement zoneSelect;

    @FindBy(id = "button-payment-address")
    private WebElement continuePaymentAddressButton;

    @FindBy(id = "button-shipping-address")
    private WebElement continueShippingAddressButton;

    @FindBy(id = "button-shipping-method")
    private WebElement continueShippingMethodButton;

    @FindBy(name = "agree")
    private WebElement termsCheckbox;

    @FindBy(id = "button-payment-method")
    private WebElement continuePaymentMethodButton;

    @FindBy(id = "button-confirm")
    private WebElement confirmOrderButton;

    // Constructor
    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Hace clic en el botón Checkout desde el carrito
     */
    public void clickCheckout() {
        try {
            System.out.println("   → Haciendo clic en Checkout...");
            WebElement checkoutLink = driver.findElement(By.linkText("Checkout"));
            clickElement(checkoutLink);
            waitHelper.customWait(2000);
            System.out.println("   ✓ Página de checkout cargada");
        } catch (Exception e) {
            System.out.println("   ✗ Error al hacer clic en Checkout: " + e.getMessage());
        }
    }

    /**
     * Llena el formulario de dirección de facturación
     */
    public void fillBillingDetails(String firstName, String lastName, String address, 
                                   String city, String postcode, String country, String zone) {
        try {
            System.out.println("   → Llenando datos de facturación...");
            
            // Verificar si ya existe una dirección
            try {
                if (driver.findElement(By.id("payment-existing")).isDisplayed()) {
                    System.out.println("   ℹ Dirección existente detectada, usando dirección guardada");
                    WebElement continueBtn = driver.findElement(By.id("button-payment-address"));
                    clickElement(continueBtn);
                    waitHelper.customWait(1500);
                    return;
                }
            } catch (Exception e) {
                // No hay dirección existente, continuar llenando el formulario
            }

            // Seleccionar "I want to use a new address"
            try {
                WebElement newAddressRadio = driver.findElement(
                    By.xpath("//input[@name='payment_address' and @value='new']"));
                clickElement(newAddressRadio);
                waitHelper.customWait(500);
            } catch (Exception e) {
                // Ya está seleccionado o no existe
            }

            writeText(firstNameInput, firstName);
            writeText(lastNameInput, lastName);
            writeText(addressInput, address);
            writeText(cityInput, city);
            writeText(postcodeInput, postcode);
            
            // Seleccionar país
            System.out.println("      • País: " + country);
            Select countryDropdown = new Select(countrySelect);
            countryDropdown.selectByVisibleText(country);
            waitHelper.customWait(1500); // Esperar más tiempo a que carguen las zonas
            
            // Seleccionar zona/región - seleccionar la primera opción disponible
            System.out.println("      • Seleccionando región/estado...");
            try {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                String script = 
                    "var select = document.getElementById('input-payment-zone');" +
                    "if (select && select.options.length > 1) {" +
                    "  select.selectedIndex = 1;" + // Seleccionar la primera opción real (índice 1)
                    "  select.dispatchEvent(new Event('change'));" +
                    "  return 'Región seleccionada: ' + select.options[1].text;" +
                    "}" +
                    "return 'Select no disponible o sin opciones';";
                
                String result = (String) js.executeScript(script);
                System.out.println("      ✓ " + result);
                waitHelper.customWait(500);
            } catch (Exception e) {
                System.out.println("      ⚠ Error al seleccionar región con JS, intentando método tradicional...");
                try {
                    Select zoneDropdown = new Select(zoneSelect);
                    if (zoneDropdown.getOptions().size() > 1) {
                        zoneDropdown.selectByIndex(1); // Seleccionar la primera opción real
                        System.out.println("      ✓ Región seleccionada con método tradicional: " + 
                                         zoneDropdown.getFirstSelectedOption().getText());
                    }
                } catch (Exception ex) {
                    System.out.println("      ⚠ No se pudo seleccionar región: " + ex.getMessage());
                }
            }
            
            System.out.println("   ✓ Datos de facturación completados");
        } catch (Exception e) {
            System.out.println("   ⚠ Error al llenar datos de facturación: " + e.getMessage());
        }
    }

    /**
     * Continúa con la dirección de pago
     */
    public void continueBillingAddress() {
        try {
            System.out.println("   → Continuando con dirección de pago...");
            clickElement(continuePaymentAddressButton);
            waitHelper.customWait(2000);
            System.out.println("   ✓ Dirección de pago confirmada");
        } catch (Exception e) {
            System.out.println("   ⚠ Error al continuar con dirección de pago: " + e.getMessage());
        }
    }

    /**
     * Continúa con la dirección de envío
     */
    public void continueShippingAddress() {
        try {
            System.out.println("   → Continuando con dirección de envío...");
            WebElement continueBtn = driver.findElement(By.id("button-shipping-address"));
            clickElement(continueBtn);
            waitHelper.customWait(2000);
            System.out.println("   ✓ Dirección de envío confirmada");
        } catch (Exception e) {
            System.out.println("   ⚠ Error al continuar con dirección de envío: " + e.getMessage());
        }
    }

    /**
     * Continúa con el método de envío
     */
    public void continueShippingMethod() {
        try {
            System.out.println("   → Seleccionando método de envío...");
            WebElement continueBtn = driver.findElement(By.id("button-shipping-method"));
            clickElement(continueBtn);
            waitHelper.customWait(2000);
            System.out.println("   ✓ Método de envío seleccionado");
        } catch (Exception e) {
            System.out.println("   ⚠ Error al continuar con método de envío: " + e.getMessage());
        }
    }

    /**
     * Acepta términos y condiciones y continúa con método de pago
     */
    public void acceptTermsAndContinuePayment() {
        try {
            System.out.println("   → Aceptando términos y condiciones...");
            WebElement termsCheck = driver.findElement(By.name("agree"));
            clickElement(termsCheck);
            waitHelper.customWait(500);
            
            WebElement continueBtn = driver.findElement(By.id("button-payment-method"));
            clickElement(continueBtn);
            waitHelper.customWait(2000);
            System.out.println("   ✓ Términos aceptados y método de pago confirmado");
        } catch (Exception e) {
            System.out.println("   ⚠ Error al aceptar términos: " + e.getMessage());
        }
    }

    /**
     * Confirma la orden
     */
    public void confirmOrder() {
        try {
            System.out.println("   → Confirmando orden...");
            WebElement confirmBtn = driver.findElement(By.id("button-confirm"));
            clickElement(confirmBtn);
            waitHelper.customWait(3000);
            System.out.println("   ✓ Orden confirmada");
        } catch (Exception e) {
            System.out.println("   ⚠ Error al confirmar orden: " + e.getMessage());
        }
    }

    /**
     * Verifica si la orden fue exitosa
     */
    public boolean isOrderSuccessful() {
        try {
            WebElement successMessage = driver.findElement(
                By.xpath("//h1[contains(text(), 'Your order has been placed!') or contains(text(), 'Your order has been processed!')]"));
            return successMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Obtiene el número de orden
     */
    public String getOrderNumber() {
        try {
            // Buscar en el contenido de la página
            String pageText = driver.findElement(By.id("content")).getText();
            if (pageText.contains("Order ID:")) {
                int startIndex = pageText.indexOf("Order ID:") + 9;
                int endIndex = pageText.indexOf("\n", startIndex);
                if (endIndex == -1) endIndex = startIndex + 10;
                return pageText.substring(startIndex, endIndex).trim();
            }
        } catch (Exception e) {
            System.out.println("   ℹ No se pudo obtener el número de orden");
        }
        return "N/A";
    }

    /**
     * Proceso completo de checkout
     */
    public boolean completeCheckout(String firstName, String lastName, String address, 
                                    String city, String postcode, String country, String zone) {
        try {
            System.out.println("\n=== PROCESO DE CHECKOUT ===");
            
            clickCheckout();
            
            // Paso 1: Billing Details
            fillBillingDetails(firstName, lastName, address, city, postcode, country, zone);
            continueBillingAddress();
            
            // Paso 2: Delivery Details
            continueShippingAddress();
            
            // Paso 3: Delivery Method
            continueShippingMethod();
            
            // Paso 4: Payment Method
            acceptTermsAndContinuePayment();
            
            // Paso 5: Confirm Order
            confirmOrder();
            
            // Verificar éxito
            boolean success = isOrderSuccessful();
            if (success) {
                String orderNumber = getOrderNumber();
                System.out.println("\n✓ ¡COMPRA EXITOSA!");
                System.out.println("   Número de orden: " + orderNumber);
            }
            
            return success;
            
        } catch (Exception e) {
            System.out.println("\n✗ Error en el proceso de checkout: " + e.getMessage());
            return false;
        }
    }
}

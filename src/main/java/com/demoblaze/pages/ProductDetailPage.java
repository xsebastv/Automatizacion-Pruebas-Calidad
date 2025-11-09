package com.demoblaze.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Página de detalle de producto de OpenCart
 */
public class ProductDetailPage extends BasePage {

    // Localizadores
    @FindBy(css = "h1")
    private WebElement productTitle;

    @FindBy(css = "input[name='quantity']")
    private WebElement quantityInput;

    @FindBy(css = "button#button-cart")
    private WebElement addToCartButton;

    @FindBy(css = "div.alert.alert-success")
    private WebElement successMessage;

    @FindBy(linkText = "shopping cart")
    private WebElement shoppingCartLink;

    // Constructor
    public ProductDetailPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Obtiene el nombre del producto
     */
    public String getProductName() {
        return getText(productTitle);
    }

    /**
     * Establece la cantidad del producto
     */
    public void setQuantity(String quantity) {
        writeText(quantityInput, quantity);
    }

    /**
     * Hace clic en agregar al carrito
     */
    public void clickAddToCart() {
        clickElement(addToCartButton);
        waitHelper.customWait(2000); // Espera para el mensaje de confirmación
    }

    /**
     * Agrega producto al carrito con cantidad específica
     */
    public boolean addToCartWithQuantity(String quantity) {
        try {
            // Completar opciones requeridas si existen
            fillRequiredOptionsIfAny();

            setQuantity(quantity);
            clickAddToCart();
            return isSuccessMessageDisplayed();
        } catch (Exception e) {
            System.out.println("Error al agregar producto al carrito: " + e.getMessage());
            return false;
        }
    }

    /**
     * Busca y completa opciones requeridas del producto (selects, radios, checkboxes, fechas, textos)
     * para productos como Canon EOS 5D (color) o HP LP3065 (fecha de entrega), etc.
     */
    private void fillRequiredOptionsIfAny() {
        try {
            List<WebElement> requiredGroups = driver.findElements(By.cssSelector(".form-group.required"));
            for (WebElement group : requiredGroups) {
                // Intentar con <select>
                List<WebElement> selects = group.findElements(By.tagName("select"));
                if (!selects.isEmpty()) {
                    WebElement selectEl = selects.get(0);
                    try {
                        Select sel = new Select(selectEl);
                        if (sel.getOptions().size() > 1) {
                            sel.selectByIndex(1); // Seleccionar la primera opción válida
                            continue;
                        }
                    } catch (Exception ignore) { }
                }

                // Intentar con radio buttons
                List<WebElement> radios = group.findElements(By.cssSelector("input[type='radio']"));
                if (!radios.isEmpty()) {
                    WebElement radio = radios.get(0);
                    if (!radio.isSelected()) {
                        clickElement(radio);
                    }
                    continue;
                }

                // Intentar con checkboxes
                List<WebElement> checks = group.findElements(By.cssSelector("input[type='checkbox']"));
                if (!checks.isEmpty()) {
                    WebElement chk = checks.get(0);
                    if (!chk.isSelected()) {
                        clickElement(chk);
                    }
                    continue;
                }

                // Fechas u otros inputs de texto requeridos (p. ej., HP LP3065 "Delivery Date")
                List<WebElement> textInputs = group.findElements(By.cssSelector("input[type='text']"));
                if (!textInputs.isEmpty()) {
                    WebElement txt = textInputs.get(0);
                    // Si parece una fecha, escribir una fecha válida (hoy + 1)
                    String val = todayPlusDays(1);
                    try {
                        txt.clear();
                    } catch (Exception ignore) { }
                    txt.sendKeys(val);
                    // Cerrar posibles datepickers
                    try { txt.sendKeys(Keys.TAB); } catch (Exception ignore) { }
                    continue;
                }

                // Textarea
                List<WebElement> textareas = group.findElements(By.tagName("textarea"));
                if (!textareas.isEmpty()) {
                    WebElement ta = textareas.get(0);
                    ta.clear();
                    ta.sendKeys("Test");
                }
            }
        } catch (Exception e) {
            System.out.println("No se pudieron completar opciones requeridas automáticamente: " + e.getMessage());
        }
    }

    private String todayPlusDays(int days) {
        LocalDate date = LocalDate.now().plusDays(days);
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    /**
     * Verifica si se muestra el mensaje de éxito
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
     * Obtiene el mensaje de éxito
     */
    public String getSuccessMessage() {
        if (isSuccessMessageDisplayed()) {
            return getText(successMessage);
        }
        return "";
    }

    /**
     * Navega al carrito de compras desde el mensaje de éxito
     */
    public void goToShoppingCart() {
        try {
            if (isSuccessMessageDisplayed()) {
                clickElement(shoppingCartLink);
                waitHelper.waitForPageLoad();
            }
        } catch (Exception e) {
            System.out.println("No se pudo navegar al carrito: " + e.getMessage());
        }
    }
}

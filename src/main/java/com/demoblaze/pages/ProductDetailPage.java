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

    // Localizadores - Combinando name (único), ID (rápido) y XPath (flexible)
    @FindBy(xpath = "//h1") // XPath simple para título
    private WebElement productTitle;

    @FindBy(name = "quantity") // name es único y estable
    private WebElement quantityInput;

    @FindBy(id = "button-cart") // ID único - más eficiente
    private WebElement addToCartButton;

    @FindBy(xpath = "//div[contains(@class, 'alert-success')]") // XPath para clase parcial
    private WebElement successMessage;

    @FindBy(linkText = "shopping cart") // linkText es directo para enlaces
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
            // XPath para buscar grupos de formularios requeridos
            List<WebElement> requiredGroups = driver.findElements(By.xpath("//div[contains(@class, 'form-group') and contains(@class, 'required')]"));
            for (WebElement group : requiredGroups) {
                // Intentar con <select> - XPath para tag
                List<WebElement> selects = group.findElements(By.xpath(".//select"));
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

                // Intentar con radio buttons - XPath para type
                List<WebElement> radios = group.findElements(By.xpath(".//input[@type='radio']"));
                if (!radios.isEmpty()) {
                    WebElement radio = radios.get(0);
                    if (!radio.isSelected()) {
                        clickElement(radio);
                    }
                    continue;
                }

                // Intentar con checkboxes - XPath para type
                List<WebElement> checks = group.findElements(By.xpath(".//input[@type='checkbox']"));
                if (!checks.isEmpty()) {
                    WebElement chk = checks.get(0);
                    if (!chk.isSelected()) {
                        clickElement(chk);
                    }
                    continue;
                }

                // Fechas u otros inputs de texto requeridos (p. ej., HP LP3065 "Delivery Date")
                List<WebElement> textInputs = group.findElements(By.xpath(".//input[@type='text']"));
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

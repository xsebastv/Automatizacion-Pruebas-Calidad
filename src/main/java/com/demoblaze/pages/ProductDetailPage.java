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
                try {
                    // Hacer scroll al grupo para asegurar visibilidad
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", group);
                    waitHelper.customWait(300);
                    
                    // 1. RADIO BUTTONS primero - XPath para type
                    List<WebElement> radios = group.findElements(By.xpath(".//input[@type='radio']"));
                    if (!radios.isEmpty()) {
                        WebElement radio = radios.get(0);
                        if (!radio.isSelected()) {
                            try {
                                // Hacer scroll al elemento específico
                                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", radio);
                                waitHelper.customWait(200);
                                clickElement(radio);
                                waitHelper.customWait(300);
                            } catch (Exception e) {
                                // Si falla el click normal, intentar con JavaScript
                                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", radio);
                                waitHelper.customWait(300);
                            }
                        }
                        continue;
                    }

                    // 2. CHECKBOXES - XPath para type
                    List<WebElement> checks = group.findElements(By.xpath(".//input[@type='checkbox']"));
                    if (!checks.isEmpty()) {
                        WebElement chk = checks.get(0);
                        if (!chk.isSelected()) {
                            try {
                                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", chk);
                                waitHelper.customWait(200);
                                clickElement(chk);
                                waitHelper.customWait(300);
                            } catch (Exception e) {
                                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", chk);
                                waitHelper.customWait(300);
                            }
                        }
                        continue;
                    }

                    // 3. SELECT dropdowns - XPath para tag
                    List<WebElement> selects = group.findElements(By.xpath(".//select"));
                    if (!selects.isEmpty()) {
                        WebElement selectEl = selects.get(0);
                        try {
                            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", selectEl);
                            waitHelper.customWait(200);
                            Select sel = new Select(selectEl);
                            if (sel.getOptions().size() > 1) {
                                sel.selectByIndex(1); // Seleccionar la primera opción válida
                                waitHelper.customWait(300);
                            }
                        } catch (Exception ignore) { }
                        continue;
                    }

                    // 4. TEXT INPUTS (fechas u otros) - XPath para type
                    List<WebElement> textInputs = group.findElements(By.xpath(".//input[@type='text']"));
                    if (!textInputs.isEmpty()) {
                        WebElement txt = textInputs.get(0);
                        try {
                            // Verificar si el campo ya tiene valor
                            String currentValue = txt.getAttribute("value");
                            if (currentValue != null && !currentValue.trim().isEmpty()) {
                                // Si ya tiene valor, no hacer nada
                                continue;
                            }
                            
                            // Hacer scroll y esperar
                            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", txt);
                            waitHelper.customWait(300);
                            
                            // Verificar si es visible e interactuable
                            if (txt.isDisplayed() && txt.isEnabled()) {
                                String val = todayPlusDays(1);
                                try {
                                    txt.clear();
                                    waitHelper.customWait(200);
                                } catch (Exception ignore) { }
                                
                                // Intentar con sendKeys normal
                                try {
                                    txt.sendKeys(val);
                                    waitHelper.customWait(200);
                                    // Cerrar posibles datepickers
                                    try { txt.sendKeys(Keys.TAB); } catch (Exception ignore) { }
                                } catch (ElementNotInteractableException e) {
                                    // Si falla, usar JavaScript
                                    ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", txt, val);
                                }
                            }
                        } catch (Exception e) {
                            // Silenciar error si el campo no es interactuable
                        }
                        continue;
                    }

                    // 5. TEXTAREA
                    List<WebElement> textareas = group.findElements(By.tagName("textarea"));
                    if (!textareas.isEmpty()) {
                        WebElement ta = textareas.get(0);
                        try {
                            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", ta);
                            waitHelper.customWait(300);
                            
                            if (ta.isDisplayed() && ta.isEnabled()) {
                                ta.clear();
                                waitHelper.customWait(200);
                                ta.sendKeys("Test textarea content");
                                waitHelper.customWait(300);
                            }
                        } catch (Exception e) {
                            // Intentar con JavaScript si falla
                            try {
                                ((JavascriptExecutor) driver).executeScript("arguments[0].value = 'Test textarea content';", ta);
                            } catch (Exception ignore) { }
                        }
                    }
                } catch (Exception e) {
                    // Continuar con el siguiente grupo si hay error en uno
                    System.out.println("   → Error al procesar grupo de opciones: " + e.getMessage());
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

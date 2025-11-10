package com.demoblaze.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
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
     */
    private void fillRequiredOptionsIfAny() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            
            // JavaScript que llena TODOS los campos requeridos instantáneamente
            String fastFillScript = 
                "var filled = 0;" +
                "document.querySelectorAll('.form-group.required').forEach(function(g) {" +
                "  var r = g.querySelectorAll('input[type=\"radio\"]');" +
                "  if (r.length > 0 && !r[0].checked) { r[0].checked = true; r[0].click(); filled++; }" +
                "  var c = g.querySelectorAll('input[type=\"checkbox\"]');" +
                "  if (c.length > 0 && !c[0].checked) { c[0].checked = true; c[0].click(); filled++; }" +
                "  var s = g.querySelectorAll('select');" +
                "  if (s.length > 0 && s[0].options.length > 1 && s[0].selectedIndex === 0) { s[0].selectedIndex = 1; filled++; }" +
                "  var t = g.querySelectorAll('input[type=\"text\"]');" +
                "  if (t.length > 0 && !t[0].value) { var d = new Date(); d.setDate(d.getDate()+1); t[0].value = d.toISOString().split('T')[0]; filled++; }" +
                "  var a = g.querySelectorAll('textarea');" +
                "  if (a.length > 0 && !a[0].value) { a[0].value = 'Test content'; filled++; }" +
                "  var dt = g.querySelectorAll('input[type=\"date\"]');" +
                "  if (dt.length > 0 && !dt[0].value) { var d = new Date(); d.setDate(d.getDate()+1); dt[0].value = d.toISOString().split('T')[0]; filled++; }" +
                "  var tm = g.querySelectorAll('input[type=\"time\"]');" +
                "  if (tm.length > 0 && !tm[0].value) { tm[0].value = '10:30'; filled++; }" +
                "});" +
                "return filled;";
            
            Long filledCount = (Long) js.executeScript(fastFillScript);
            
            if (filledCount > 0) {
                System.out.println("   → " + filledCount + " campos requeridos completados instantáneamente");
                waitHelper.customWait(300); // Una sola espera
            }
            
        } catch (Exception e) {
            // Si JavaScript falla, intentar método tradicional simplificado
            System.out.println("   → Método rápido falló, usando alternativa...");
            fillOptionsTraditionalWay();
        }
    }
    
    /**
     * Método alternativo SIMPLIFICADO y RÁPIDO (solo si JavaScript falla)
     */
    private void fillOptionsTraditionalWay() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            List<WebElement> requiredGroups = driver.findElements(By.xpath("//div[contains(@class, 'form-group') and contains(@class, 'required')]"));
            int filled = 0;
            
            for (WebElement group : requiredGroups) {
                try {
                    // RADIO: Click directo con JS
                    List<WebElement> radios = group.findElements(By.xpath(".//input[@type='radio']"));
                    if (!radios.isEmpty() && !radios.get(0).isSelected()) {
                        js.executeScript("arguments[0].checked = true; arguments[0].click();", radios.get(0));
                        filled++;
                        continue;
                    }

                    // CHECKBOX: Click directo con JS
                    List<WebElement> checks = group.findElements(By.xpath(".//input[@type='checkbox']"));
                    if (!checks.isEmpty() && !checks.get(0).isSelected()) {
                        js.executeScript("arguments[0].checked = true; arguments[0].click();", checks.get(0));
                        filled++;
                        continue;
                    }

                    // SELECT: Selección directa con JS
                    List<WebElement> selects = group.findElements(By.xpath(".//select"));
                    if (!selects.isEmpty()) {
                        js.executeScript("arguments[0].selectedIndex = 1;", selects.get(0));
                        filled++;
                        continue;
                    }

                    // TEXT: Seteo directo si vacío
                    List<WebElement> texts = group.findElements(By.xpath(".//input[@type='text']"));
                    if (!texts.isEmpty()) {
                        String val = texts.get(0).getAttribute("value");
                        if (val == null || val.trim().isEmpty()) {
                            js.executeScript("arguments[0].value = arguments[1];", texts.get(0), todayPlusDays(1));
                            filled++;
                        }
                        continue;
                    }

                    // TEXTAREA: Seteo directo si vacía
                    List<WebElement> areas = group.findElements(By.tagName("textarea"));
                    if (!areas.isEmpty()) {
                        String val = areas.get(0).getAttribute("value");
                        if (val == null || val.trim().isEmpty()) {
                            js.executeScript("arguments[0].value = 'Test content';", areas.get(0));
                            filled++;
                        }
                        continue;
                    }

                    // DATE: Seteo directo si vacío
                    List<WebElement> dates = group.findElements(By.xpath(".//input[@type='date']"));
                    if (!dates.isEmpty()) {
                        String val = dates.get(0).getAttribute("value");
                        if (val == null || val.trim().isEmpty()) {
                            js.executeScript("arguments[0].value = arguments[1];", dates.get(0), todayPlusDays(1));
                            filled++;
                        }
                        continue;
                    }

                    // TIME: Seteo directo si vacío
                    List<WebElement> times = group.findElements(By.xpath(".//input[@type='time']"));
                    if (!times.isEmpty()) {
                        String val = times.get(0).getAttribute("value");
                        if (val == null || val.trim().isEmpty()) {
                            js.executeScript("arguments[0].value = '10:30';", times.get(0));
                            filled++;
                        }
                    }

                } catch (Exception e) {
                    // Continuar con siguiente grupo
                }
            }
            
            if (filled > 0) {
                System.out.println("   → " + filled + " campos completados (método alternativo)");
                waitHelper.customWait(300);
            }
            
        } catch (Exception e) {
            System.out.println("   → Error en método alternativo: " + e.getMessage());
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

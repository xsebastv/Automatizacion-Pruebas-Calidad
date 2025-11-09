package com.demoblaze.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

/**
 * Página del carrito de compras de OpenCart
 */
public class CartPage extends BasePage {

    // Localizadores
    @FindBy(css = "div.table-responsive")
    private WebElement cartTable;

    @FindBy(css = "tbody tr")
    private List<WebElement> cartItems;

    @FindBy(linkText = "Shopping Cart")
    private WebElement shoppingCartLink;

    @FindBy(css = "p")
    private WebElement emptyCartMessage;

    // Constructor
    public CartPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navega a la página del carrito
     */
    public void navigateToCart() {
        navigateTo("https://opencart.abstracta.us/index.php?route=checkout/cart");
        waitHelper.customWait(1000);
    }

    /**
     * Hace clic en el enlace del carrito
     */
    public void clickShoppingCart() {
        try {
            // Primero hacer clic en el botón del carrito en el header
            WebElement cartButton = driver.findElement(By.cssSelector("button[data-toggle='dropdown']"));
            clickElement(cartButton);
            waitHelper.customWait(500);
            
            // Luego hacer clic en "View Cart"
            WebElement viewCartLink = driver.findElement(By.xpath("//p[@class='text-right']//a[contains(text(), 'View Cart')]"));
            clickElement(viewCartLink);
            waitHelper.waitForPageLoad();
        } catch (Exception e) {
            // Si falla, navegar directamente
            navigateToCart();
        }
    }

    /**
     * Obtiene la cantidad de productos en el carrito
     */
    public int getCartItemCount() {
        try {
            navigateToCart();
            // Filtrar solo las filas que contienen productos (no totales)
            List<WebElement> items = driver.findElements(
                By.cssSelector("div.table-responsive tbody tr"));
            return items.size();
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Verifica si el carrito está vacío
     */
    public boolean isCartEmpty() {
        try {
            navigateToCart();
            String pageContent = driver.findElement(By.id("content")).getText();
            return pageContent.contains("Your shopping cart is empty") || 
                   pageContent.contains("vacío");
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * Verifica si un producto específico está en el carrito
     */
    public boolean isProductInCart(String productName) {
        try {
            navigateToCart();
            List<WebElement> products = driver.findElements(
                By.xpath("//div[@class='table-responsive']//td[@class='text-left']//a[contains(text(), '" + 
                productName + "')]"));
            return !products.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Obtiene la lista de nombres de productos en el carrito
     */
    public List<String> getProductNamesInCart() {
        List<String> productNames = new ArrayList<>();
        try {
            navigateToCart();
            List<WebElement> products = driver.findElements(
                By.cssSelector("div.table-responsive tbody tr td.text-left a"));
            
            for (WebElement product : products) {
                productNames.add(product.getText());
            }
        } catch (Exception e) {
            System.out.println("Error al obtener nombres de productos: " + e.getMessage());
        }
        return productNames;
    }

    /**
     * Obtiene la cantidad de un producto específico en el carrito
     */
    public String getProductQuantity(String productName) {
        try {
            WebElement quantityInput = driver.findElement(
                By.xpath("//td[@class='text-left']//a[contains(text(), '" + productName + 
                "')]/ancestor::tr//input[@name[contains(., 'quantity')]]"));
            return quantityInput.getAttribute("value");
        } catch (Exception e) {
            return "0";
        }
    }

    /**
     * Limpia el carrito
     */
    public void clearCart() {
        try {
            navigateToCart();
            List<WebElement> removeButtons = driver.findElements(
                By.cssSelector("button[data-original-title='Remove']"));
            
            while (!removeButtons.isEmpty()) {
                removeButtons.get(0).click();
                waitHelper.customWait(1000);
                removeButtons = driver.findElements(
                    By.cssSelector("button[data-original-title='Remove']"));
            }
        } catch (Exception e) {
            System.out.println("Error al limpiar el carrito: " + e.getMessage());
        }
    }

    /**
     * Verifica múltiples productos en el carrito
     */
    public boolean verifyProductsInCart(List<String> expectedProducts) {
        List<String> actualProducts = getProductNamesInCart();
        
        for (String expected : expectedProducts) {
            boolean found = false;
            for (String actual : actualProducts) {
                if (actual.contains(expected)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }
}

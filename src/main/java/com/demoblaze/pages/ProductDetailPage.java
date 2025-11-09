package com.demoblaze.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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
            setQuantity(quantity);
            clickAddToCart();
            return isSuccessMessageDisplayed();
        } catch (Exception e) {
            System.out.println("Error al agregar producto al carrito: " + e.getMessage());
            return false;
        }
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

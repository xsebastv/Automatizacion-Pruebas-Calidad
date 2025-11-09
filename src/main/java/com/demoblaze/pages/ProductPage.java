package com.demoblaze.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Página de productos y búsqueda de OpenCart
 */
public class ProductPage extends BasePage {

    // Localizadores
    @FindBy(css = "input[name='search']")
    private WebElement searchInput;

    @FindBy(css = "button.btn.btn-default.btn-lg")
    private WebElement searchButton;

    @FindBy(css = "div.product-thumb")
    private List<WebElement> productItems;

    @FindBy(css = "h1")
    private WebElement pageTitle;

    @FindBy(css = "p")
    private WebElement noProductMessage;

    @FindBy(id = "content")
    private WebElement contentArea;

    // Constructor
    public ProductPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navega a la página principal
     */
    public void navigateToHomePage() {
        navigateTo("https://opencart.abstracta.us/");
    }

    /**
     * Busca un producto por nombre
     */
    public void searchProduct(String productName) {
        writeText(searchInput, productName);
        clickElement(searchButton);
        waitHelper.waitForPageLoad();
        waitHelper.customWait(1000);
    }

    /**
     * Busca producto por categoría y subcategoría
     */
    public void searchByCategory(String category, String subCategory) {
        try {
            // Hacer clic en el menú de categorías
            WebElement categoryMenu = driver.findElement(
                By.xpath("//a[contains(text(), '" + category + "')]"));
            clickElement(categoryMenu);
            waitHelper.customWait(1000);

            // Si hay subcategoría, hacer clic en ella
            if (subCategory != null && !subCategory.isEmpty()) {
                WebElement subCategoryLink = driver.findElement(
                    By.xpath("//a[contains(text(), '" + subCategory + "')]"));
                clickElement(subCategoryLink);
                waitHelper.customWait(1000);
            }
        } catch (Exception e) {
            System.out.println("Error al navegar por categorías: " + e.getMessage());
        }
    }

    /**
     * Verifica si un producto aparece en los resultados
     */
    public boolean isProductDisplayed(String productName) {
        try {
            waitHelper.customWait(1000);
            
            // Buscar por el título exacto del producto
            List<WebElement> products = driver.findElements(
                By.xpath("//div[@class='product-thumb']//h4/a[contains(text(), '" + productName + "')]"));
            
            return !products.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Hace clic en un producto específico
     */
    public void clickOnProduct(String productName) {
        try {
            WebElement product = driver.findElement(
                By.xpath("//div[@class='product-thumb']//h4/a[contains(text(), '" + productName + "')]"));
            scrollToElement(product);
            clickElement(product);
            waitHelper.waitForPageLoad();
        } catch (Exception e) {
            System.out.println("No se pudo hacer clic en el producto: " + e.getMessage());
        }
    }

    /**
     * Agrega un producto al carrito desde la lista de resultados
     */
    public void addProductToCartFromList(String productName) {
        try {
            WebElement addToCartButton = driver.findElement(
                By.xpath("//div[@class='product-thumb'][.//a[contains(text(), '" + 
                productName + "')]]//button[@onclick[contains(., 'cart.add')]]"));
            scrollToElement(addToCartButton);
            clickElement(addToCartButton);
            waitHelper.customWait(2000); // Espera para que se agregue al carrito
        } catch (Exception e) {
            System.out.println("No se pudo agregar el producto al carrito: " + e.getMessage());
        }
    }

    /**
     * Obtiene el número de productos encontrados
     */
    public int getProductCount() {
        try {
            return productItems.size();
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Verifica si hay mensaje de "sin productos"
     */
    public boolean isNoProductMessageDisplayed() {
        try {
            String content = getText(contentArea);
            return content.contains("There is no product") || 
                   content.contains("No hay productos");
        } catch (Exception e) {
            return false;
        }
    }
}

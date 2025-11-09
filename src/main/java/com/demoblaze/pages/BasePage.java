package com.demoblaze.pages;

import com.demoblaze.utils.WaitHelper;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

/**
 * Clase base para todas las páginas del proyecto
 * Implementa el patrón Page Object Model (POM)
 */
public class BasePage {

    protected WebDriver driver;
    protected WaitHelper waitHelper;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.waitHelper = new WaitHelper(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Navega a una URL específica
     */
    public void navigateTo(String url) {
        driver.get(url);
        waitHelper.waitForPageLoad();
    }

    /**
     * Hace clic en un elemento después de esperar que sea clickeable
     */
    protected void clickElement(WebElement element) {
        waitHelper.waitForElementToBeClickable(element);
        element.click();
    }

    /**
     * Escribe texto en un elemento después de esperar que sea visible
     */
    protected void writeText(WebElement element, String text) {
        waitHelper.waitForElementToBeVisible(element);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Obtiene el texto de un elemento después de esperar que sea visible
     */
    protected String getText(WebElement element) {
        waitHelper.waitForElementToBeVisible(element);
        return element.getText();
    }

    /**
     * Verifica si un elemento es visible
     */
    protected boolean isElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Scroll hasta un elemento usando JavaScript
     */
    protected void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        waitHelper.customWait(500);
    }

    /**
     * Obtiene el título de la página actual
     */
    public String getPageTitle() {
        return driver.getTitle();
    }

    /**
     * Obtiene la URL actual
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}

package com.demoblaze.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductsPage extends BasePage{

    public ProductsPage(WebDriver driver){
        super(driver);
    }

    //crear los elementos con los que vamos a interactuar en la pag
    // Usando XPath para búsqueda dinámica por texto del producto
    private By product(String product){
        return By.xpath("//a[contains(text(),'"+product+"')]");
    }

    //Metodo
    public void selectProduct(String product){
        driver.findElement(product(product)).click();
    }
}

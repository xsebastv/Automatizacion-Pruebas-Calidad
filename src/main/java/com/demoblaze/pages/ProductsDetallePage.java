package com.demoblaze.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductsDetallePage extends BasePage{

    public ProductsDetallePage(WebDriver driver){
        super(driver);
    }

    //Elementos - Combinando ID (más rápido) y XPath (más flexible)
    private By quantity(){
        return By.name("quantity"); // Name es único y estable
    }

    private By btnAddCart(){
        return By.id("button-cart"); // ID es único - más eficiente que XPath
    }

    //metodo
    public void agregarCarrito(String quantity){
        driver.findElement(quantity()).sendKeys(quantity);
        driver.findElement(btnAddCart()).click();
    }
}

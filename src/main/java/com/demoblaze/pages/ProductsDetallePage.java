package com.demoblaze.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductsDetallePage extends BasePage{

    public ProductsDetallePage(WebDriver driver){
        super(driver);
    }

    //Elementos
    private By quantity(){
        return By.name("quantity");
    }

    private By btnAddCart(){
        return By.id("button-cart");
    }

    //metodo
    public void agregarCarrito(String quantity){
        driver.findElement(quantity()).sendKeys(quantity);
        driver.findElement(btnAddCart()).click();
    }
}

package com.demoblaze.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage{


    public HomePage(WebDriver driver){ super(driver);}

    //Crear los elementos de la pag
    private By category(String category){
        return By.xpath("//ul[@class='nav navbar-nav']/li/a[normalize-space(text())='"+category+"']");
    }
    private By subcategory(String subcategory){
        return By.xpath("//ul[@class='nav navbar-nav']/li//a[contains(normalize-space(),'"+subcategory+"')]");
    }




    //Metodos o acciones de la pagina
    public void selectCategory(String category){
        driver.findElement(category(category)).click();
    }

    public void selectSubCategory(String subcategory){
        driver.findElement(subcategory(subcategory)).click();
    }
}

package com.demoblaze.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage{


    public HomePage(WebDriver driver){ super(driver);}

    //Crear los elementos de la pag - Usando XPath para búsqueda por texto (flexible)
    private By category(String category){
        // XPath es ideal para búsqueda por texto con normalize-space
        return By.xpath("//ul[@class='nav navbar-nav']/li/a[normalize-space(text())='"+category+"']");
    }
    
    private By subcategory(String subcategory){
        // XPath con contains() para coincidencias parciales
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

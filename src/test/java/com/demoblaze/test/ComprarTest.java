package com.demoblaze.test;

import com.demoblaze.pages.HomePage;
import com.demoblaze.pages.ProductsDetallePage;
import com.demoblaze.pages.ProductsPage;
import com.demoblaze.utils.Constants;
import org.testng.annotations.Test;

public class ComprarTest extends BaseTest{

    @Test
    public void ComprarProductos(){
        //Pages
        HomePage homePage = new HomePage(driver);
        ProductsPage productsPage = new ProductsPage(driver);
        ProductsDetallePage productsDetallePage = new ProductsDetallePage(driver);

        homePage.navigateTo(Constants.BASE_URL);

        homePage.selectCategory("Desktops");
        homePage.selectSubCategory("Mac");

        productsPage.selectProduct("iMac");

        productsDetallePage.agregarCarrito("3");


    }
}

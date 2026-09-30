package productTest;

import baseTest.BaseTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.login.LoginPage;
import pages.product.ProductPage;

import java.util.List;

public class ProductTest extends BaseTest {

    @Test
    public void addToCart() {

        LoginPage login = new LoginPage(driver);
        ProductPage productPage = new ProductPage(driver);

        login.enterUsername(configHandler.getValue("username"));
        login.enterPassword(configHandler.getValue("password"));
        login.clickLogin();

        List<String> addedIds = productPage.addSelectedProducts();

        softAssert.assertTrue(addedIds.size() == 5);
        softAssert.assertAll();

        productPage.getShoppingCartBadge().click();
        Assert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/cart.html");
    }

    @Test
    public void checkCartageIconNumber(){

        LoginPage login = new LoginPage(driver);
        ProductPage productPage = new ProductPage(driver);

        login.enterUsername(configHandler.getValue("username"));
        login.enterPassword(configHandler.getValue("password"));
        login.clickLogin();

        softAssert.assertTrue(productPage.cartIconLogic());
        softAssert.assertAll();
    }

    @Test
    public void checkRemoveIsDisplayed() throws InterruptedException{

        LoginPage login = new LoginPage(driver);
        ProductPage productPage = new ProductPage(driver);

        login.enterUsername(configHandler.getValue("username"));
        login.enterPassword(configHandler.getValue("password"));
        login.clickLogin();

        Assert.assertTrue(productPage.checkRemoveDisplayedLogic());

        Thread.sleep(3000);

    }

    @Test
    public void filterProducts() throws InterruptedException{

        LoginPage login = new LoginPage(driver);
        ProductPage productPage = new ProductPage(driver);

        login.enterUsername(configHandler.getValue("username"));
        login.enterPassword(configHandler.getValue("password"));
        login.clickLogin();

        WebElement dropdown;
        Select select;

        for (int i = 0 ; i < 4 ; i++){
            dropdown = productPage.getFilterButton();
            select = new Select(dropdown);
            select.selectByIndex(i);
            softAssert.assertTrue(productPage.checkFilter(i));
            Thread.sleep(2000);
        }

        Thread.sleep(3000);
        softAssert.assertAll();

    }
}





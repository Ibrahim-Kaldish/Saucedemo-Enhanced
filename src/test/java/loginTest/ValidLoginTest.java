package loginTest;

import baseTest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.login.LoginPage;
import pages.product.ProductPage;

public class ValidLoginTest extends BaseTest {

    @Test
    public void validLogin(){

        LoginPage loginPage = new LoginPage(driver);

        loginPage.enterUsername(configHandler.getValue("username"));
        loginPage.enterPassword(configHandler.getValue("password"));
        loginPage.clickLogin();

        ProductPage productPage = new ProductPage(driver);

        Assert.assertEquals(productPage.getTitle().getText(),"Products");
    }

}

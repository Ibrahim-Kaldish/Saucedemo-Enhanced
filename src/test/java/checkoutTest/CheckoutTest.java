package checkoutTest;

import baseTest.BaseTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.cart.CartPage;
import pages.information.InformationPage;
import pages.login.LoginPage;
import pages.overview.OverviewPage;
import pages.product.ProductPage;


public class CheckoutTest extends BaseTest {
    @Test
    public void checkout() {

        LoginPage login = new LoginPage(driver);
        ProductPage productPage = new ProductPage(driver);
        CartPage cartPage = new CartPage(driver);

        login.enterUsername(configHandler.getValue("username"));
        login.enterPassword(configHandler.getValue("password"));
        login.clickLogin();

        productPage.addSelectedProducts();

        productPage.clickCartButton();

        int count = 0;
        for (String id: ProductPage.productIds){
            for (WebElement checkItem: driver.findElements(By.xpath("//*[@class='btn btn_secondary btn_small cart_button']"))){
                if(id.equals(checkItem.getAttribute("id").replace("remove-",""))){
                    count++;
                }
            }
        }

        cartPage.clickCheckout();

        softAssert.assertTrue(count==ProductPage.productIds.size());

        softAssert.assertTrue(productPage.navigateToPage(configHandler.getValue("checkoutUrlStepOne")));
        softAssert.assertAll();
    }

    @Test
    public void checkTotalSalary() throws InterruptedException {

        driver.get(configHandler.getValue("url"));

        LoginPage login = new LoginPage(driver);
        ProductPage productPage = new ProductPage(driver);
        InformationPage informationPage = new InformationPage(driver);
        CartPage cartPage = new CartPage(driver);
        OverviewPage overviewPage = new OverviewPage(driver);

        login.enterUsername(configHandler.getValue("username"));
        login.enterPassword(configHandler.getValue("password"));
        login.clickLogin();

        double actualTotalPrice = productPage.price();
        productPage.clickCartButton();
        cartPage.clickCheckout();

        informationPage.enterFirstname(configHandler.getValue("firstname"));
        informationPage.enterLastname(configHandler.getValue("lastname"));
        informationPage.enterPostalCode(configHandler.getValue("postalCode"));
        informationPage.clickContinue();

        double expectedTotalPrice = overviewPage.parseDouble(overviewPage.getSubTotal());

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(overviewPage.checkSubtotal(actualTotalPrice,expectedTotalPrice));
        softAssert.assertTrue(overviewPage.checkTotal(actualTotalPrice));
        softAssert.assertAll();
        Thread.sleep(1000);

    }

}

package generateReportTest;

import baseTest.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.cart.CartPage;
import pages.generateReport.GenerateReport;
import pages.information.InformationPage;
import pages.login.LoginPage;
import pages.overview.OverviewPage;
import pages.product.ProductPage;

public class GenerateReportTest extends BaseTest {
    @Test
    public void generatePDF() throws InterruptedException {
        LoginPage login = new LoginPage(driver);
        ProductPage productPage = new ProductPage(driver);
        CartPage cartPage = new CartPage(driver);
        InformationPage infoPage = new InformationPage(driver);
        OverviewPage overviewPage = new OverviewPage(driver);

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

        infoPage.enterFirstname(configHandler.getValue("firstname"));
        infoPage.enterLastname(configHandler.getValue("lastname"));
        infoPage.enterPostalCode(configHandler.getValue("postalCode"));
        infoPage.clickContinue();

        Assert.assertTrue(infoPage.navigateToPage(configHandler.getValue("checkoutUrlStepTwo")));


        overviewPage.clickFinish();
        Assert.assertTrue(overviewPage.navigateToPage(configHandler.getValue("completeUrl")));

        GenerateReport reportPage = new GenerateReport(driver);

        reportPage.clickGenerateButton();

    }
}

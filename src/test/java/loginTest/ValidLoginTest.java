package loginTest;

import baseTest.BaseTest;
import dataTestProvider.DataProviderTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.login.LoginPage;
import pages.product.ProductPage;

import java.util.ArrayList;
import java.util.List;

public class ValidLoginTest extends BaseTest {

    @Test(dataProvider = "validCredentials", dataProviderClass = DataProviderTest.class)
    public void validLogin(String username, String password) {
        LoginPage loginPage = new LoginPage(driver);

        log.info("🔐 Step 1: Logging in as {}", username);
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        log.info("🧪 Step 2: Verifying the products page is displayed");
        ProductPage productPage = new ProductPage(driver);
        String title = productPage.getTitle().getText();
        log.info("📋 Page title after login: '{}'", title);

        Assert.assertEquals(title, "Products", "Login failed for user: " + username);
        log.info("✅ validLogin passed for {}", username);
    }

    @Test
    public void maxFrequentWord(){
        openCSVFileManager.maxFrequentWordLogic();
    }
}
package informationTest;

import baseTest.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.cart.CartPage;
import pages.information.InformationPage;
import pages.login.LoginPage;
import pages.product.ProductPage;

public class InformationTest extends BaseTest {

    @Test
    public void checkoutInfo() {
        LoginPage login = new LoginPage(driver);
        ProductPage productPage = new ProductPage(driver);
        CartPage cartPage = new CartPage(driver);

        log.info("🔐 Step 1: Logging in");
        login.enterUsername(configHandler.getValue("username"));
        login.enterPassword(configHandler.getValue("password"));
        login.clickLogin();

        log.info("🛒 Step 2: Adding selected products");
        ProductPage.productIds.clear();
        productPage.addSelectedProducts();
        log.info("📋 Added product ids: {}", ProductPage.productIds);

        log.info("🧺 Step 3: Opening the cart");
        productPage.clickCartButton();

        log.info("🔎 Step 4: Verifying the added products appear in the cart");
        int count = 0;
        for (String id : ProductPage.productIds) {
            for (WebElement checkItem : driver.findElements(By.xpath("//*[@class='btn btn_secondary btn_small cart_button']"))) {
                if (id.equals(checkItem.getAttribute("id").replace("remove-", ""))) {
                    count++;
                    log.debug("✅ Found product in cart: {}", id);
                }
            }
        }
        log.info("📊 Products found in cart: {} of {}", count, ProductPage.productIds.size());

        log.info("➡️ Step 5: Clicking Checkout");
        cartPage.clickCheckout();

        log.info("🧪 Step 6: Asserting cart contents and checkout step one URL");
        softAssert.assertTrue(count == ProductPage.productIds.size(),
                "Cart does not contain all added products: " + count + " of " + ProductPage.productIds.size());
        softAssert.assertTrue(productPage.navigateToPage(configHandler.getValue("checkoutUrlStepOne")),
                "Not redirected to checkout step one");
        softAssert.assertAll();

        log.info("📝 Step 7: Filling customer information");
        InformationPage infoPage = new InformationPage(driver);
        infoPage.enterFirstname(configHandler.getValue("firstname"));
        infoPage.enterLastname(configHandler.getValue("lastname"));
        infoPage.enterPostalCode(configHandler.getValue("postalCode"));
        infoPage.clickContinue();

        log.info("🧪 Step 8: Asserting checkout step two URL");
        Assert.assertTrue(infoPage.navigateToPage(configHandler.getValue("checkoutUrlStepTwo")),
                "Not redirected to checkout step two");
        log.info("✅ checkoutInfo test finished");
    }
}
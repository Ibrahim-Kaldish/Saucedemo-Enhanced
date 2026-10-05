package checkoutTest;

import baseTest.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
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

        log.info("🧪 Step 6: Asserting cart contents and checkout URL");
        softAssert.assertTrue(count == ProductPage.productIds.size(),
                "Cart does not contain all added products: " + count + " of " + ProductPage.productIds.size());
        softAssert.assertTrue(productPage.navigateToPage(configHandler.getValue("checkoutUrlStepOne")),
                "Not redirected to checkout step one");
        softAssert.assertAll();
        log.info("✅ checkout test assertions passed");
    }

    @Test
    public void checkTotalSalary() {
        LoginPage login = new LoginPage(driver);
        ProductPage productPage = new ProductPage(driver);
        InformationPage informationPage = new InformationPage(driver);
        CartPage cartPage = new CartPage(driver);
        OverviewPage overviewPage = new OverviewPage(driver);

        log.info("🔐 Step 1: Logging in");
        login.enterUsername(configHandler.getValue("username"));
        login.enterPassword(configHandler.getValue("password"));
        login.clickLogin();

        log.info("💰 Step 2: Adding products and calculating expected price");
        double actualTotalPrice = productPage.price();
        log.info("💲 Calculated products price: {}", actualTotalPrice);

        log.info("🧺 Step 3: Opening cart and starting checkout");
        productPage.clickCartButton();
        cartPage.clickCheckout();

        log.info("📝 Step 4: Filling customer information");
        informationPage.enterFirstname(configHandler.getValue("firstname"));
        informationPage.enterLastname(configHandler.getValue("lastname"));
        informationPage.enterPostalCode(configHandler.getValue("postalCode"));
        informationPage.clickContinue();

        log.info("🧾 Step 5: Reading subtotal from the overview page");
        double expectedTotalPrice = overviewPage.parseDouble(overviewPage.getSubTotal());
        log.info("💲 Subtotal shown on page: {}", expectedTotalPrice);

        log.info("🧪 Step 6: Asserting subtotal and total");
        softAssert.assertTrue(overviewPage.checkSubtotal(actualTotalPrice, expectedTotalPrice),
                "Subtotal mismatch: calculated " + actualTotalPrice + " vs page " + expectedTotalPrice);
        softAssert.assertTrue(overviewPage.checkTotal(actualTotalPrice),
                "Total (subtotal + tax) mismatch");
        softAssert.assertAll();
        log.info("✅ checkTotalSalary test assertions passed");
    }
}
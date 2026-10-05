package productTest;

import baseTest.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.login.LoginPage;
import pages.product.ProductPage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductTest extends BaseTest {

    @Test
    public void addToCart() {
        LoginPage login = new LoginPage(driver);
        ProductPage productPage = new ProductPage(driver);

        log.info("🔐 Step 1: Logging in");
        login.enterUsername(configHandler.getValue("username"));
        login.enterPassword(configHandler.getValue("password"));
        login.clickLogin();

        log.info("🛒 Step 2: Adding selected products");
        ProductPage.productIds.clear();
        List<String> addedIds = productPage.addSelectedProducts();
        log.info("📋 Added {} products: {}", addedIds.size(), addedIds);

        log.info("🧪 Step 3: Asserting the number of added products");
        softAssert.assertTrue(addedIds.size() == 5,
                "Expected 5 products added but got " + addedIds.size());
        softAssert.assertAll();

        log.info("🧺 Step 4: Opening the cart from the badge");
        productPage.getShoppingCartBadge().click();

        log.info("🧪 Step 5: Asserting the cart URL");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/cart.html",
                "Not redirected to the cart page");
        log.info("✅ addToCart test finished");
    }

    @Test
    public void checkCartageIconNumber() {
        LoginPage login = new LoginPage(driver);
        ProductPage productPage = new ProductPage(driver);

        log.info("🔐 Step 1: Logging in");
        login.enterUsername(configHandler.getValue("username"));
        login.enterPassword(configHandler.getValue("password"));
        login.clickLogin();

        log.info("🛒 Step 2: Checking the cart icon logic");
        boolean result = productPage.cartIconLogic();

        log.info("🧪 Step 3: Asserting the cart is empty after removing the products");
        softAssert.assertTrue(result, "Cart icon still shows items after removing all products");
        softAssert.assertAll();
        log.info("✅ checkCartageIconNumber test finished");
    }

    @Test
    public void checkRemoveIsDisplayed() {
        LoginPage login = new LoginPage(driver);
        ProductPage productPage = new ProductPage(driver);

        log.info("🔐 Step 1: Logging in");
        login.enterUsername(configHandler.getValue("username"));
        login.enterPassword(configHandler.getValue("password"));
        login.clickLogin();

        log.info("📂 Step 2: Reading product names from product.json");
        List<String> substrings = new ArrayList<>(Arrays.asList(
                jsonFileManagerProducts.getValue("product1").toString().toLowerCase(),
                jsonFileManagerProducts.getValue("product2").toString().toLowerCase(),
                jsonFileManagerProducts.getValue("product3").toString().toLowerCase(),
                jsonFileManagerProducts.getValue("product4").toString().toLowerCase()
        ));
        log.info("📋 Products to add: {}", substrings);

        log.info("🛒 Step 3: Adding the products and collecting their link ids");
        List<String> linksIds = new ArrayList<>();

        for (String sub : substrings) {
            List<WebElement> elements = productPage.getAllProducts();
            for (WebElement el : elements) {
                String productName = el.findElement(By.className("inventory_item_name")).getText();
                if (productName.toLowerCase().contains(sub)) {
                    log.info("🖱️ Adding product: {}", productName);
                    el.findElement(By.tagName("button")).click();
                    linksIds.add(el.findElement(By.tagName("a")).getAttribute("id"));
                }
            }
        }
        log.info("📊 Added {} products, link ids: {}", linksIds.size(), linksIds);

        log.info("🔎 Step 4: Opening each product and checking the Remove button");
        for (String linkId : linksIds) {
            log.info("🖱️ Opening product page for link id: {}", linkId);
            driver.findElement(By.id(linkId)).click();

            WebElement removeBtn = productPage.findElement(By.id("remove"));
            boolean displayed = removeBtn.isDisplayed();
            log.info("🧪 Remove button displayed for {} -> {}", linkId, displayed ? "✅ yes" : "❌ no");
            softAssert.assertTrue(displayed, "Remove button not displayed for product link: " + linkId);

            log.debug("⬅️ Navigating back to the products page");
            driver.navigate().back();
        }

        softAssert.assertAll();
        log.info("✅ checkRemoveIsDisplayed test finished");
    }

    @Test
    public void filterProducts() {
        LoginPage login = new LoginPage(driver);
        ProductPage productPage = new ProductPage(driver);

        log.info("🔐 Step 1: Logging in");
        login.enterUsername(configHandler.getValue("username"));
        login.enterPassword(configHandler.getValue("password"));
        login.clickLogin();

        log.info("🔽 Step 2: Checking each sort option");
        String[] options = {"Name (A to Z)", "Name (Z to A)", "Price (low to high)", "Price (high to low)"};

        for (int i = 0; i < 4; i++) {
            log.info("🖱️ Selecting sort option {}: {}", i, options[i]);
            WebElement dropdown = productPage.getFilterButton();
            Select select = new Select(dropdown);
            select.selectByIndex(i);

            boolean sorted = productPage.checkFilter(i);
            softAssert.assertTrue(sorted, "Products not sorted correctly for option: " + options[i]);
        }

        softAssert.assertAll();
        log.info("✅ filterProducts test finished");
    }
}
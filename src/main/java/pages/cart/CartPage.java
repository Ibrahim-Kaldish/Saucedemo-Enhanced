package pages.cart;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;

public class CartPage extends BasePage {

    private static final Logger log = LogManager.getLogger(CartPage.class);

    private final By checkoutLocator = By.id("checkout");

    public CartPage(WebDriver driver) {
        super(driver);
        log.debug("🛒 CartPage initialized");
    }

    public WebElement getCheckoutButton() {
        log.debug("🔍 Locating checkout button: {}", checkoutLocator);
        return findElement(checkoutLocator);
    }

    public void clickCheckout() {
        log.info("🖱️ Clicking Checkout button");
        getCheckoutButton().click();
        log.info("✅ Checkout button clicked");
    }
}
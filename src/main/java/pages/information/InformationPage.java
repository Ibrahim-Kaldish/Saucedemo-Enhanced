package pages.information;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;

public class InformationPage extends BasePage {

    private static final Logger log = LogManager.getLogger(InformationPage.class);

    private final By firstnameField = By.id("first-name");
    private final By lastnameField = By.id("last-name");
    private final By postalCodeField = By.id("postal-code");
    private final By continueBtn = By.id("continue");

    public InformationPage(WebDriver driver) {
        super(driver);
        log.debug("📝 InformationPage initialized");
    }

    public WebElement getFirstname() {
        log.debug("🔍 Locating first name field: {}", firstnameField);
        return findElement(firstnameField);
    }

    public WebElement getLastname() {
        log.debug("🔍 Locating last name field: {}", lastnameField);
        return findElement(lastnameField);
    }

    public WebElement getPostalCode() {
        log.debug("🔍 Locating postal code field: {}", postalCodeField);
        return findElement(postalCodeField);
    }

    public WebElement getContinueButton() {
        log.debug("🔍 Locating continue button: {}", continueBtn);
        return findElement(continueBtn);
    }

    public void enterFirstname(String firstname) {
        log.info("⌨️ Entering first name: {}", firstname);
        getFirstname().sendKeys(firstname);
    }

    public void enterLastname(String lastname) {
        log.info("⌨️ Entering last name: {}", lastname);
        getLastname().sendKeys(lastname);
    }

    public void enterPostalCode(String postalCode) {
        log.info("⌨️ Entering postal code: {}", postalCode);
        getPostalCode().sendKeys(postalCode);
    }

    public void clickContinue() {
        log.info("🖱️ Clicking Continue button");
        getContinueButton().click();
        log.info("✅ Continue button clicked");
    }
}
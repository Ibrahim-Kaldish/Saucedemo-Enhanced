package pages.login;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;

import java.util.ArrayList;
import java.util.List;

public class LoginPage extends BasePage {

    private static final Logger log = LogManager.getLogger(LoginPage.class);

    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginBtn = By.id("login-button");

    public LoginPage(WebDriver driver) {
        super(driver);
        log.debug("🔐 LoginPage initialized");
    }

    public WebElement getUsernameField() {
        log.debug("🔍 Locating username field: {}", usernameField);
        return findElement(usernameField);
    }

    public WebElement getPasswordField() {
        log.debug("🔍 Locating password field: {}", passwordField);
        return findElement(passwordField);
    }

    public WebElement getLoginBtn() {
        log.debug("🔍 Locating login button: {}", loginBtn);
        return findElement(loginBtn);
    }

    public void enterUsername(String username) {
        log.info("⌨️ Entering username: {}", username);
        getUsernameField().sendKeys(username);
    }

    public void enterPassword(String password) {
        log.info("⌨️ Entering password: ********");
        getPasswordField().sendKeys(password);
    }

    public void clickLogin() {
        log.info("🖱️ Clicking Login button");
        getLoginBtn().click();
        log.info("✅ Login button clicked");
    }


}
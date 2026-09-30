package pages.login;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;

public class LoginPage extends BasePage{

    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginBtn = By.id("login-button");

    public LoginPage(WebDriver driver){
        super(driver);
    }

    public WebElement getUsernameField() {
        return findElement(usernameField);
    }
    public WebElement getPasswordField() {
        return findElement(passwordField);
    }
    public WebElement getLoginBtn() {
        return findElement(loginBtn);
    }

    public void enterUsername(String username){
        getUsernameField().sendKeys(username);
    }
    public void enterPassword(String password){
        getPasswordField().sendKeys(password);
    }
    public void clickLogin(){
        getLoginBtn().click();
    }
}

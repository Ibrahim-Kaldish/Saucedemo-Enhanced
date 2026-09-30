package pages.information;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;

public class InformationPage extends BasePage {

    private final By firstnameField = By.id("first-name");
    private final By lastnameField = By.id("last-name");
    private final By postalCodeField = By.id("postal-code");
    private final By continueBtn = By.id("continue");

    public InformationPage(WebDriver driver){
        super(driver);
    }

    public WebElement getFirstname(){
        return findElement(firstnameField);
    }
    public WebElement getLastname(){
        return findElement(lastnameField);
    }
    public WebElement getPostalCode(){
        return findElement(postalCodeField);
    }
    public WebElement getContinueButton(){
        return findElement(continueBtn);
    }
    public void enterFirstname(String username){
        getFirstname().sendKeys(username);
    }
    public void enterLastname(String lastname){
        getLastname().sendKeys(lastname);
    }
    public void enterPostalCode(String postalCode){
        getPostalCode().sendKeys(postalCode);
    }
    public void clickContinue(){
        getContinueButton().click();
    }

}

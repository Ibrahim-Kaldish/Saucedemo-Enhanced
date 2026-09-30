package pages.cart;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;

import java.util.ArrayList;
import java.util.List;

public class CartPage extends BasePage {

    private final By checkoutLocator = By.id("checkout");

    public CartPage( WebDriver driver){
        super(driver);
    }


    public WebElement getCheckoutButton(){
        return findElement(checkoutLocator);
    }

    public void clickCheckout(){
        getCheckoutButton().click();
    }


}

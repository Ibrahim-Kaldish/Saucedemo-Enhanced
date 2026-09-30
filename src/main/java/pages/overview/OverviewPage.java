package pages.overview;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;
import pages.product.ProductPage;

import java.util.List;

public class OverviewPage extends BasePage {
    private final By finishField = By.id("finish");
    private final By subTotal = By.className("summary_subtotal_label");
    private final By tax = By.className("summary_tax_label");
    private final By total = By.className("summary_total_label");


    public OverviewPage(WebDriver driver){
        super(driver);
    }

    public WebElement getFinishButton(){
        return findElement(finishField);
    }

    public WebElement getSubTotal(){
        return findElement(subTotal);
    }
    public WebElement getTax(){
        return findElement(tax);
    }
    public WebElement getTotal(){
        return findElement(total);
    }

    public void clickFinish(){
        getFinishButton().click();
    }



    public boolean checkSubtotal(double price1, double price2){
        System.out.println(price1 + " " + price2);
        return price1 == price2;
    }

    public boolean checkTotal(double price){
        System.out.println(price+parseDouble(getTax())+ " " + parseDouble(getTotal()));
        return Math.round(price + parseDouble(getTax())) == Math.round(parseDouble(getTotal()));
    }

}

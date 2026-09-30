package pages.generateReport;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;

public class GenerateReport extends BasePage {
    private final By generateButton = By.id("generate-pdf-order");

    public GenerateReport(WebDriver driver){
        super(driver);
    }

    public WebElement getGenerateButton() {
        return findElement(generateButton);
    }

    public void clickGenerateButton() {
        getGenerateButton().click();
    }

}

package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BasePage {
    public WebDriver driver ;
    public WebDriverWait wait;

    public BasePage(WebDriver driver){
        this.driver = driver ;
    }

    public WebElement findElement(By locator){
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return driver.findElement(locator);
    }

    public WebElement findElement(By locator, Duration duration){
        wait = new WebDriverWait(driver, duration);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return driver.findElement(locator);
    }

    public List<WebElement> findElements(By locator){
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return driver.findElements(locator);
    }

    public List<WebElement> findElements(By locator, Duration duration){
        wait = new WebDriverWait(driver, duration);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return driver.findElements(locator);
    }

    public boolean navigateToPage(String redirectedUrl){
        return driver.getCurrentUrl().equals(redirectedUrl);
    }

    public double parseDouble (WebElement el){
        String s = "";
        for (char c: el.getText().toCharArray()){
            if (Character.isDigit(c) || c == '.') s += c;
        }
        return Double.parseDouble(s);
    }
}

package driverFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class GetFirefoxDriver {
    private static WebDriver driver = null;

    public static WebDriver getWebDriver(){
        if (driver == null){
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--private");
            driver = new FirefoxDriver(options);
        }
        return driver;
    }
    public static void quitDriver(){
        if (driver != null){
            driver.quit();
            driver = null;
        }
    }
}

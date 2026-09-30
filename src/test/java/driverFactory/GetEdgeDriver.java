package driverFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class GetEdgeDriver implements DriverFactory{
    private static WebDriver driver = null;

    public static WebDriver getWebDriver(){
        if (driver == null){
            EdgeOptions options = new EdgeOptions();
            options.addArguments("--incognito");
            driver = new EdgeDriver(options);
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

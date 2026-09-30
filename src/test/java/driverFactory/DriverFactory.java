package driverFactory;

import org.openqa.selenium.WebDriver;

public interface DriverFactory {

    public static WebDriver getWebDriver(){
        return null;
    }
    public static void quitDriver(){};
}

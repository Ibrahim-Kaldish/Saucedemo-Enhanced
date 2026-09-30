package baseTest;

import driverFactory.GetChromeDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import org.testng.asserts.SoftAssert;
import utilies.ConfigHandler;

import java.time.Duration;

public class BaseTest {
    public WebDriver driver;
    public WebDriverWait wait;
    public SoftAssert softAssert;
    public ConfigHandler configHandler;

    @BeforeMethod
    public void setUp(){
        configHandler = new ConfigHandler("src/main/resources/config.properties");
        driver = GetChromeDriver.getWebDriver();
        driver.manage().window().maximize();
        driver.get(configHandler.getValue("url"));
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        softAssert = new SoftAssert();
    }

    @AfterMethod
    public void tearDown(){
        GetChromeDriver.quitDriver();
        driver = null;
    }
}
package baseTest;

import driverFactory.GetChromeDriver;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;
import utilities.ConfigHandler;
import utilities.JSONFileManager;
import utilities.OpenCSVFileManager;
import utilities.Screenshot;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.time.Duration;

public class BaseTest {
    public WebDriver driver;
    public WebDriverWait wait;
    public SoftAssert softAssert;
    public ConfigHandler configHandler;
    public JSONFileManager jsonFileManagerUsers;
    public JSONFileManager jsonFileManagerProducts;
    public OpenCSVFileManager openCSVFileManager;
    protected static final Logger log = LogManager.getLogger(BaseTest.class);

    @BeforeMethod
    public void setUp(Method method) {
        log.info("🚀 ========== Starting test: {} ==========", method.getName());

        try {
            log.debug("⚙️ Loading config from src/main/resources/config.properties");
            configHandler = new ConfigHandler("src/main/resources/config.properties");

            log.debug("📂 Loading test data: product.json and users.json");
            jsonFileManagerProducts = new JSONFileManager("src/main/resources/product.json");
            jsonFileManagerUsers = new JSONFileManager("src/main/resources/users.json");

            log.debug("📂 Loading test data: products.csv");
            openCSVFileManager = new OpenCSVFileManager("src/main/resources/products.csv");

            log.info("🌐 Launching Chrome browser");
            driver = GetChromeDriver.getWebDriver();
            driver.manage().window().maximize();
            log.debug("🖥️ Browser window maximized");

            String url = configHandler.getValue("url");
            log.info("🔗 Navigating to {}", url);
            driver.get(url);

            wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            log.debug("⏳ WebDriverWait created with 5s timeout");

            softAssert = new SoftAssert();
            log.debug("🧪 SoftAssert initialized");

            log.info("✅ Setup completed successfully");
        } catch (Exception e) {
            log.error("❌ Setup failed for test {}", method.getName(), e);
            throw e;
        }
    }

    @AfterMethod
    public void failedTestCases(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            File image = Screenshot.takeScreenshot(driver);
            FileInputStream fis = new  FileInputStream(image);
            Allure.addAttachment("Failure TC: "+ result.getTestName(), "image/png", fis, "png");
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        String name = result.getMethod().getMethodName();

        try {
            switch (result.getStatus()) {
                case ITestResult.SUCCESS:
                    log.info("✅ Test PASSED: {}", name);
                    break;
                case ITestResult.FAILURE:
                    log.error("❌ Test FAILED: {}", name, result.getThrowable());
                    File image = Screenshot.takeScreenshot(driver);
                    if (image != null) {
                        try (InputStream is = new FileInputStream(image)) {
                            Allure.addAttachment("Failure: " + name, "image/png", is, "png");
                            log.info("📎 Screenshot attached to Allure");
                        } catch (IOException e) {
                            log.error("❌ Could not attach screenshot to Allure", e);
                        }
                    }
                    break;
                case ITestResult.SKIP:
                    log.warn("⚠️ Test SKIPPED: {}", name);
                    break;
            }
        } finally {
            log.info("🔒 Closing browser");
            GetChromeDriver.quitDriver();
            driver = null;
            log.info("🏁 ========== Finished test: {} ==========", name);
        }
    }
}
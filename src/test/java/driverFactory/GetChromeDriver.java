package driverFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class GetChromeDriver implements DriverFactory {

    private static final Logger log = LogManager.getLogger(GetChromeDriver.class);

    private static WebDriver driver = null;

    public static WebDriver getWebDriver() {
        if (driver == null) {
            log.info("🌐 Creating new Chrome driver");
            try {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--incognito");
                log.debug("⚙️ Chrome options: {}", options.asMap());
                driver = new ChromeDriver(options);
                log.info("✅ Chrome driver started");
            } catch (Exception e) {
                log.error("❌ Failed to start Chrome driver", e);
                throw e;
            }
        } else {
            log.debug("♻️ Reusing existing Chrome driver");
        }
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            log.info("🔒 Quitting Chrome driver");
            driver.quit();
            driver = null;
            log.info("✅ Chrome driver closed");
        } else {
            log.debug("ℹ️ quitDriver called but no driver is running");
        }
    }
}
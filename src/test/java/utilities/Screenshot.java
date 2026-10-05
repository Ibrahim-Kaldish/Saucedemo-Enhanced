package utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;

public class Screenshot {

    private static final Logger log = LogManager.getLogger(Screenshot.class);

    public static File takeScreenshot(WebDriver driver) {
        if (driver == null) {
            log.warn("⚠️ Cannot take screenshot: driver is null");
            return null;
        }

        log.info("📸 Taking screenshot");
        try {
            File image = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            log.info("✅ Screenshot saved: {}", image.getAbsolutePath());
            return image;
        } catch (Exception e) {
            log.error("❌ Failed to take screenshot", e);
            return null;
        }
    }
}
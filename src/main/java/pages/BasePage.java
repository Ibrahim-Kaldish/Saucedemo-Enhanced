package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BasePage {

    private static final Logger log = LogManager.getLogger(BasePage.class);
    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(5);

    public WebDriver driver;
    public WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement findElement(By locator) {
        return findElement(locator, Duration.ofSeconds(10));
    }

    public WebElement findElement(By locator, Duration duration) {
        log.debug("🔍 Finding element: {} (timeout {}s)", locator, duration.getSeconds());
        wait = new WebDriverWait(driver, duration);
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Exception e) {
            log.error("❌ Element not visible after {}s: {}", duration.getSeconds(), locator);
            throw e;
        }
    }

    public List<WebElement> findElements(By locator) {
        return findElements(locator, Duration.ofSeconds(10));
    }

    public List<WebElement> findElements(By locator, Duration duration) {
        log.debug("🔍 Finding elements: {} (timeout {}s)", locator, duration.getSeconds());
        wait = new WebDriverWait(driver, duration);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Exception e) {
            log.error("❌ No visible element after {}s: {}", duration.getSeconds(), locator);
            throw e;
        }
        List<WebElement> elements = driver.findElements(locator);
        log.debug("📋 Found {} elements for {}", elements.size(), locator);
        return elements;
    }

    public boolean navigateToPage(String redirectedUrl) {
        String current = driver.getCurrentUrl();
        boolean result = current.equals(redirectedUrl);
        log.info("🧭 URL check: expected {} vs actual {} -> {}", redirectedUrl, current, result ? "✅ match" : "❌ mismatch");
        return result;
    }

    public double parseDouble(WebElement el) {
        String text = el.getText();
        StringBuilder sb = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (Character.isDigit(c) || c == '.') sb.append(c);
        }
        double value = Double.parseDouble(sb.toString());
        log.debug("🔢 Parsed '{}' -> {}", text, value);
        return value;
    }
}
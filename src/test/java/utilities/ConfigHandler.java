package utilies;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigHandler {

    private static final Logger log = LogManager.getLogger(ConfigHandler.class);

    Properties properties;

    public ConfigHandler(String filePath) {
        properties = new Properties();
        log.debug("⚙️ Loading config file: {}", filePath);
        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            properties.load(fileInputStream);
            log.info("✅ Config loaded: {} properties from {}", properties.size(), filePath);
        } catch (IOException e) {
            log.error("❌ Failed to load config file: {}", filePath, e);
            throw new RuntimeException("Cannot load config file: " + filePath, e);
        }
    }

    public String getValue(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            log.warn("⚠️ Config key not found: {}", key);
        } else {
            log.debug("🔑 Config {} loaded", key);
        }
        return value;
    }
}
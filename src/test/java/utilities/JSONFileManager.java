package utilities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class JSONFileManager {

    private static final Logger log = LogManager.getLogger(JSONFileManager.class);

    public LinkedHashMap<String, Object> data;

    public JSONFileManager(String filePath) {
        log.debug("📂 Loading JSON file: {}", filePath);
        try (FileReader reader = new FileReader(filePath)) {
            Type t = new TypeToken<LinkedHashMap<String, Object>>() {}.getType();
            data = new Gson().fromJson(reader, t);
        } catch (IOException e) {
            log.error("❌ Failed to read JSON file: {}", filePath, e);
            throw new RuntimeException("Cannot read JSON file: " + filePath, e);
        } catch (Exception e) {
            log.error("❌ Invalid JSON in file: {}", filePath, e);
            throw new RuntimeException("Invalid JSON in file: " + filePath, e);
        }

        if (data == null) {
            log.error("❌ JSON file is empty: {}", filePath);
            throw new RuntimeException("JSON file is empty: " + filePath);
        }
        log.info("✅ JSON loaded: {} entries from {}", data.size(), filePath);
    }

    public Object getValue(String key) {
        Object value = data.get(key);
        if (value == null) {
            log.warn("⚠️ JSON key not found: {}", key);
        } else {
            log.debug("🔑 JSON key {} loaded", key);
        }
        return value;
    }

    public List<Object> getValues() {
        log.debug("📋 Returning {} values", data.size());
        return new ArrayList<>(data.values());
    }

    public List<String> getKeys() {
        log.debug("🔑 Returning {} keys", data.size());
        return new ArrayList<>(data.keySet());
    }
}
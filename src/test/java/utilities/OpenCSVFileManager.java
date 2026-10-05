package utilities;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class OpenCSVFileManager {

    private static final Logger log = LogManager.getLogger(OpenCSVFileManager.class);

    private final String csvFilePath;
    private final List<String> columns = new ArrayList<>();
    private final List<String[]> rows = new ArrayList<>();

    public OpenCSVFileManager(String csvFilePath) {
        this.csvFilePath = csvFilePath;
        log.debug("📂 Reading CSV file: {}", csvFilePath);

        try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
            List<String[]> all = reader.readAll();
            if (all.isEmpty()) {
                throw new RuntimeException("CSV file is empty: " + csvFilePath);
            }
            columns.addAll(Arrays.asList(all.get(0)));
            rows.addAll(all.subList(1, all.size()));
            log.info("✅ CSV loaded: {} columns, {} rows from [{}]", columns.size(), rows.size(), csvFilePath);
        } catch (IOException | CsvException e) {
            log.error("❌ Failed to read CSV file: {}", csvFilePath, e);
            throw new RuntimeException("Cannot read CSV file: " + csvFilePath, e);
        }
    }

    /** All data rows (header excluded). */
    public List<String[]> getRows() {
        return rows;
    }

    /** Column names from the header row. */
    public List<String> getColumns() {
        return new ArrayList<>(columns);
    }

    /** Column name -> list of that column's values, in file order. */
    public Map<String, List<String>> getColumnsWithData() {
        Map<String, List<String>> map = new LinkedHashMap<>();
        for (int i = 0; i < columns.size(); i++) {
            List<String> data = new ArrayList<>();
            for (String[] row : rows) {
                if (i < row.length) data.add(row[i]);
            }
            map.put(columns.get(i), data);
        }
        return map;
    }

    public String getFirstColumn() {
        return columns.get(0);
    }

    public String getLastColumn() {
        return columns.get(columns.size() - 1);
    }

    /** columnIndex is 0-based. */
    public String getSpecificColumnName(int columnIndex) {
        return columns.get(columnIndex);
    }

    public List<String> getSpecificColumnData(String columnName) {
        int idx = columns.indexOf(columnName);
        if (idx == -1) {
            log.warn("⚠️ Column not found: {}", columnName);
            return Collections.emptyList();
        }
        return getSpecificColumnData(idx);
    }

    /** columnIndex is 0-based. */
    public List<String> getSpecificColumnData(int columnIndex) {
        List<String> data = new ArrayList<>();
        for (String[] row : rows) {
            if (columnIndex < row.length) data.add(row[columnIndex]);
        }
        log.debug("📋 Column '{}' has {} values", columns.get(columnIndex), data.size());
        return data;
    }

    /** rowNum and columnIndex are 0-based (row 0 = first data row). */
    public String getCellData(int rowNum, int columnIndex) {
        return rows.get(rowNum)[columnIndex];
    }

    public String getCellData(int rowNum, String columnName) {
        int idx = columns.indexOf(columnName);
        if (idx == -1) {
            log.warn("⚠️ Column not found: {}", columnName);
            return null;
        }
        return getCellData(rowNum, idx);
    }


    public String formatAll(List<String> columns, List<List<String>> ans){
        int colWidth = "Column".length();
        int wordsWidth = "Word(s)".length();
        List<String> wordsText = new ArrayList<>();

        for (int i = 0; i < columns.size(); i++){
            List<String> subAns = ans.get(i);
            List<String> words = new ArrayList<>(subAns.subList(0, subAns.size() - 1));
            Collections.sort(words);
            String text = String.join(", ", words);
            wordsText.add(text);
            colWidth = Math.max(colWidth, columns.get(i).length());
            wordsWidth = Math.max(wordsWidth, text.length());
        }

        String line = "+-" + "-".repeat(colWidth) + "-+-------+-" + "-".repeat(wordsWidth) + "-+";
        String row = "| %-" + colWidth + "s | %5s | %-" + wordsWidth + "s |%n";

        StringBuilder sb = new StringBuilder("\n📊 Most frequent word(s) per column\n");
        sb.append(line).append("\n");
        sb.append(String.format(row, "Column", "Count", "Word(s)"));
        sb.append(line).append("\n");
        for (int i = 0; i < columns.size(); i++){
            List<String> subAns = ans.get(i);
            sb.append(String.format(row, columns.get(i), subAns.get(subAns.size() - 1), wordsText.get(i)));
        }
        sb.append(line);
        return sb.toString();
    }

    public List<String> findMaxFrequentWordsInCloumn(List<String> values){
        List<String> subAns = new ArrayList<>();
        HashMap<String,Integer> map = new HashMap<>();
        int mx = 1 ;
        for (String line: values){
            for (String word : List.of(line.split(" "))){
                word = word.toLowerCase();
                if (map.containsKey(word)){
                    map.put(word, map.get(word) + 1) ;
                    mx = Math.max(mx, map.get(word));
                }
                else map.put(word, 1);
            }
        }
        for (Map.Entry<String, Integer> entry : map.entrySet()){
            if (entry.getValue() == mx){
                subAns.add(entry.getKey());
            }
        }
        subAns.add(String.valueOf(mx));
        return subAns;
    }

    public List<List<String>> maxFrequentWordLogic(){
        List<List<String>> ans = new ArrayList<>();
        List<String> cloNames = getColumns();
        for (String cloName : cloNames){
            List<String> subAns = findMaxFrequentWordsInCloumn(getSpecificColumnData(cloName));
            ans.add(subAns);
        }
        log.info(formatAll(cloNames, ans));
        return ans;
    }

    public int getCellCount(String columnName) {
        return getSpecificColumnData(columnName).size();
    }

    public int getCellCount(int columnIndex) {
        return getSpecificColumnData(columnIndex).size();
    }
}

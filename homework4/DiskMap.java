import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class DiskMap implements Map<String, String> {
    private final Map<String, String> map;
    private final File file;

    public DiskMap(String filename) {
        this.map = new HashMap<>();
        this.file = new File(filename);
        loadFromFile();
    }

    private void loadFromFile() {
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(":", 2);
                    if (parts.length == 2) {
                        map.put(parts[0].trim(), parts[1].trim());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String put(String key, String value) {
        String oldValue = map.put(key, value);
        saveToFile();
        return oldValue;
    }

    @Override
    public String get(Object key) {
        return map.get(key);
    }

    @Override
    public String remove(Object key) {
        String oldValue = map.remove(key);
        saveToFile();
        return oldValue;
    }

    @Override
    public void putAll(Map<? extends String, ? extends String> m) {
        map.putAll(m);
        saveToFile();
    }

    @Override
    public void clear() {
        map.clear();
        saveToFile();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public String getOrDefault(Object key, String defaultValue) {
        return map.getOrDefault(key, defaultValue);
    }

    @Override
    public String putIfAbsent(String key, String value) {
        String oldValue = map.putIfAbsent(key, value);
        saveToFile();
        return oldValue;
    }

    @Override
    public boolean remove(Object key, Object value) {
        boolean removed = map.remove(key, value);
        if (removed) {
            saveToFile();
        }
        return removed;
    }

    @Override
    public boolean replace(String key, String oldValue, String newValue) {
        boolean replaced = map.replace(key, oldValue, newValue);
        if (replaced) {
            saveToFile();
        }
        return replaced;
    }

    @Override
    public String replace(String key, String value) {
        String oldValue = map.replace(key, value);
        saveToFile();
        return oldValue;
    }

    @Override
    public java.util.Set<String> keySet() {
        return map.keySet();
    }

    @Override
    public java.util.Collection<String> values() {
        return map.values();
    }

    @Override
    public java.util.Set<Entry<String, String>> entrySet() {
        return map.entrySet();
    }
}
package com.renuka.minikv;

import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MiniKV implements KeyValueStore {

    private final Map<String, String> store;
    private final String fileName = "minikv.data";
    private final WriteAheadLog wal;

    public MiniKV() {
        store = new ConcurrentHashMap<>();
        wal = new WriteAheadLog();
        loadFromFile();
    }

    private void validateKey(String key) {
        if (key == null || key.isBlank()) {
            throw new InvalidKeyException("Key cannot be null or blank");
        }
    }

    @Override
    public synchronized void put(String key, String value) {
        validateKey(key);

        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null");
        }

        wal.log("PUT", key, value);
        store.put(key, value);
        saveToFile();
    }

    @Override
    public String get(String key) {
        validateKey(key);
        return store.get(key);
    }

    @Override
    public synchronized void delete(String key) {
        validateKey(key);

        wal.log("DELETE", key, "");
        store.remove(key);
        saveToFile();
    }

    @Override
    public boolean containsKey(String key) {
        validateKey(key);
        return store.containsKey(key);
    }

    @Override
    public int size() {
        return store.size();
    }

    private void saveToFile() {

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(fileName))) {

            for (Map.Entry<String, String> entry : store.entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue());
                writer.newLine();
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to save data", e);
        }
    }

    private void loadFromFile() {

        File file = new File(fileName);

        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split("=", 2);

                if (parts.length == 2) {
                    store.put(parts[0], parts[1]);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to load data", e);
        }
    }
}
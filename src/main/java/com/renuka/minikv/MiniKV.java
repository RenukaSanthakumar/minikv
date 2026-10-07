package com.renuka.minikv;

import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class MiniKV implements KeyValueStore {

    private final Map<String, String> store;
    private final Map<String, Long> expirationTimes;
    private final String fileName = "minikv.data";
    private final WriteAheadLog wal;

    public MiniKV() {
        store = new ConcurrentHashMap<>();
        expirationTimes = new ConcurrentHashMap<>();
        wal = new WriteAheadLog();
        loadFromFile();
    }

    private void validateKey(String key) {
        if (key == null || key.isBlank()) {
            throw new InvalidKeyException(
                    "Key cannot be null or blank"
            );
        }
    }

    @Override
    public synchronized void put(String key, String value) {
        validateKey(key);

        if (value == null) {
            throw new IllegalArgumentException(
                    "Value cannot be null"
            );
        }

        wal.log("PUT", key, value);
        store.put(key, value);
        expirationTimes.remove(key);
        saveToFile();
    }

    @Override
    public synchronized void put(
            String key, String value, long ttlSeconds) {

        validateKey(key);

        if (value == null) {
            throw new IllegalArgumentException(
                    "Value cannot be null"
            );
        }

        if (ttlSeconds <= 0) {
            throw new IllegalArgumentException(
                    "TTL must be greater than zero"
            );
        }

        long expirationTime = System.currentTimeMillis()
                + TimeUnit.SECONDS.toMillis(ttlSeconds);

        wal.log("PUT_TTL", key, value + "|" + expirationTime);
        store.put(key, value);
        expirationTimes.put(key, expirationTime);
        saveToFile();
    }

    @Override
    public String get(String key) {
        validateKey(key);

        if (isExpired(key)) {
            removeExpiredKey(key);
            return null;
        }

        return store.get(key);
    }

    @Override
    public synchronized void delete(String key) {
        validateKey(key);

        wal.log("DELETE", key, "");
        store.remove(key);
        expirationTimes.remove(key);
        saveToFile();
    }

    @Override
    public boolean containsKey(String key) {
        validateKey(key);

        if (isExpired(key)) {
            removeExpiredKey(key);
            return false;
        }

        return store.containsKey(key);
    }

    @Override
    public int size() {
        for (String key : store.keySet()) {
            if (isExpired(key)) {
                removeExpiredKey(key);
            }
        }

        return store.size();
    }

    private boolean isExpired(String key) {
        Long expirationTime = expirationTimes.get(key);

        return expirationTime != null
                && System.currentTimeMillis() >= expirationTime;
    }

    private synchronized void removeExpiredKey(String key) {
        if (isExpired(key)) {
            wal.log("DELETE", key, "");
            store.remove(key);
            expirationTimes.remove(key);
            saveToFile();
        }
    }

    private void saveToFile() {
        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(fileName))) {

            for (Map.Entry<String, String> entry
                    : store.entrySet()) {

                String key = entry.getKey();
                String value = entry.getValue();
                Long expiry = expirationTimes.get(key);

                writer.write(key + "=" + value);

                if (expiry != null) {
                    writer.write("|" + expiry);
                }

                writer.newLine();
            }

        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to save data", e
            );
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

                if (parts.length != 2) {
                    continue;
                }

                String key = parts[0];
                String[] valueParts = parts[1].split("\\|", 2);

                String value = valueParts[0];
                store.put(key, value);

                if (valueParts.length == 2) {
                    long expiry = Long.parseLong(valueParts[1]);

                    if (System.currentTimeMillis() >= expiry) {
                        store.remove(key);
                    } else {
                        expirationTimes.put(key, expiry);
                    }
                }
            }

            saveToFile();

        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to load data", e
            );
        }
    }
}

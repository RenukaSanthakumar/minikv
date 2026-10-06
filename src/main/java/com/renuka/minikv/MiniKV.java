package com.renuka.minikv;

import java.util.HashMap;
import java.util.Map;

public class MiniKV implements KeyValueStore {

    private final Map<String, String> store;

    public MiniKV() {
        store = new HashMap<>();
    }

    private void validateKey(String key) {
        if (key == null || key.isBlank()) {
            throw new InvalidKeyException("Key cannot be null or blank");
        }
    }

    @Override
    public void put(String key, String value) {
        validateKey(key);

        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null");
        }

        store.put(key, value);
    }

    @Override
    public String get(String key) {
        validateKey(key);
        return store.get(key);
    }

    @Override
    public void delete(String key) {
        validateKey(key);
        store.remove(key);
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
}
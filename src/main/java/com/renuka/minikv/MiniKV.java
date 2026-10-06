package com.renuka.minikv;

import java.util.HashMap;
import java.util.Map;

public class MiniKV {

    private final Map<String, String> store;

    public MiniKV() {
        store = new HashMap<>();
    }

    public void put(String key, String value) {
        store.put(key, value);
    }

    public String get(String key) {
        return store.get(key);
    }

    public void delete(String key) {
        store.remove(key);
    }

    public boolean containsKey(String key) {
        return store.containsKey(key);
    }

    public int size() {
        return store.size();
    }
}
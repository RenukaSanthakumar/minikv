package com.renuka.minikv;

import java.util.Map;

public interface KeyValueStore {

    void put(String key, String value);

    String get(String key);

    void delete(String key);

    boolean containsKey(String key);

    int size();

    void put(String key, String value, long ttlSeconds);

    Map<String, String> getAll();
}
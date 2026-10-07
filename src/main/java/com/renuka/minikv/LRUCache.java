package com.renuka.minikv;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache extends LinkedHashMap<String, String> {

    private final int capacity;

    public LRUCache(int capacity) {

        super(capacity, 0.75f, true);

        if (capacity <= 0) {
            throw new IllegalArgumentException(
                    "Capacity must be greater than zero"
            );
        }

        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(
            Map.Entry<String, String> eldest) {

        return size() > capacity;
    }
}
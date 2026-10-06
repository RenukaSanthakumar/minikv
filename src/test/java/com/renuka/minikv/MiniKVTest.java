package com.renuka.minikv;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MiniKVTest {

    @Test
    void testPutAndGet() {
        MiniKV kv = new MiniKV();

        kv.put("name", "Renu");

        assertEquals("Renu", kv.get("name"));
    }

    @Test
    void testUpdateExistingKey() {
        MiniKV kv = new MiniKV();

        kv.put("name", "Renu");
        kv.put("name", "Renuka");

        assertEquals("Renuka", kv.get("name"));
    }

    @Test
    void testDelete() {
        MiniKV kv = new MiniKV();

        kv.put("name", "Renu");
        kv.delete("name");

        assertFalse(kv.containsKey("name"));
    }

    @Test
    void testContainsKey() {
        MiniKV kv = new MiniKV();

        kv.put("name", "Renu");

        assertTrue(kv.containsKey("name"));
        assertFalse(kv.containsKey("age"));
    }

    @Test
    void testSize() {
        MiniKV kv = new MiniKV();

        kv.put("name", "Renu");
        kv.put("age", "20");

        assertEquals(2, kv.size());

        kv.delete("age");

        assertEquals(1, kv.size());
    }

    @Test
    void testInvalidKey() {
        MiniKV kv = new MiniKV();

        assertThrows(
                InvalidKeyException.class,
                () -> kv.put("", "Renu")
        );
    }

    @Test
    void testNullKey() {
        MiniKV kv = new MiniKV();

        assertThrows(
                InvalidKeyException.class,
                () -> kv.put(null, "Renu")
        );
    }

    @Test
    void testNullValue() {
        MiniKV kv = new MiniKV();

        assertThrows(
                IllegalArgumentException.class,
                () -> kv.put("name", null)
        );
    }
}
package com.renuka.minikv;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import java.io.File;

public class MiniKVTest {

    @BeforeEach
void cleanUpBeforeTest() {
    new File("minikv.data").delete();
    new File("minikv.wal").delete();
}

@AfterEach
void cleanUpAfterTest() {
    new File("minikv.data").delete();
    new File("minikv.wal").delete();
}

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
    
    @Test
void testConcurrentPut() throws InterruptedException {

    MiniKV kv = new MiniKV();

    int numberOfThreads = 10;
    int entriesPerThread = 100;

    Thread[] threads = new Thread[numberOfThreads];

    for (int i = 0; i < numberOfThreads; i++) {

        final int threadNumber = i;

        threads[i] = new Thread(() -> {

            for (int j = 0; j < entriesPerThread; j++) {

                String key = "thread" + threadNumber + "-key" + j;
                kv.put(key, "value");
            }
        });

        threads[i].start();
    }

    for (Thread thread : threads) {
        thread.join();
    }

    assertEquals(
            numberOfThreads * entriesPerThread,
            kv.size()
    );
}
}
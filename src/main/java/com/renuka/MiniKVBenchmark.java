package com.renuka;

import com.renuka.minikv.MiniKV;

public class MiniKVBenchmark {

    public static void main(String[] args) {

        MiniKV kv = new MiniKV();

        int numberOfOperations = 1000;

        // Benchmark PUT
        long startPut = System.nanoTime();

        for (int i = 0; i < numberOfOperations; i++) {
            kv.put("key" + i, "value" + i);
        }

        long endPut = System.nanoTime();

        // Benchmark GET
        long startGet = System.nanoTime();

        for (int i = 0; i < numberOfOperations; i++) {
            kv.get("key" + i);
        }

        long endGet = System.nanoTime();

        long putTime = endPut - startPut;
        long getTime = endGet - startGet;

        System.out.println("=== MiniKV Benchmark ===");
        System.out.println("Operations: " + numberOfOperations);

        System.out.println(
                "PUT time: " + putTime / 1_000_000.0 + " ms"
        );

        System.out.println(
                "GET time: " + getTime / 1_000_000.0 + " ms"
        );

        System.out.println(
                "Final size: " + kv.size()
        );
    }
}
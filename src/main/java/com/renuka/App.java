package com.renuka;

import com.renuka.minikv.KeyValueStore;
import com.renuka.minikv.MiniKV;

public class App {

    public static void main(String[] args) {

        KeyValueStore kv = new MiniKV();

        kv.put("name", "Renu");
        kv.put("age", "20");

        System.out.println(kv.get("name"));
        System.out.println(kv.get("age"));

        System.out.println(kv.containsKey("name"));

        kv.delete("age");

        System.out.println(kv.containsKey("age"));

        System.out.println(kv.size());
    }
}
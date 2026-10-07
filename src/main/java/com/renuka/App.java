package com.renuka;

import com.renuka.minikv.KeyValueStore;
import com.renuka.minikv.MiniKV;

public class App {

    public static void main(String[] args) throws InterruptedException {

        KeyValueStore kv = new MiniKV();

        // 1. Normal PUT and GET
        kv.put("name", "Renu");

        System.out.println("Name: " + kv.get("name"));


        // 2. Update existing key
        kv.put("name", "Renuka");

        System.out.println("Updated name: " + kv.get("name"));


        // 3. Check key
        System.out.println("Contains name: "
                + kv.containsKey("name"));


        // 4. Add another key
        kv.put("age", "20");

        System.out.println("Size: " + kv.size());


        // 5. Delete
        kv.delete("age");

        System.out.println("Contains age after delete: "
                + kv.containsKey("age"));


        // 6. TTL
        kv.put("OTP", "583921", 3);

        System.out.println("OTP before expiry: "
                + kv.get("OTP"));

        Thread.sleep(4000);

        System.out.println("OTP after expiry: "
                + kv.get("OTP"));


        // 7. Final size
        System.out.println("Final size: " + kv.size());
    }
}
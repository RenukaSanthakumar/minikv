package com.renuka;

import com.renuka.minikv.KeyValueStore;
import com.renuka.minikv.MiniKV;

import java.util.Scanner;

public class MiniKVCLI {

    public static void main(String[] args) {

        KeyValueStore kv = new MiniKV();

        Scanner scanner = new Scanner(System.in);

        System.out.println("=== MiniKV CLI ===");
        System.out.println("Commands:");
        System.out.println("PUT key value");
        System.out.println("GET key");
        System.out.println("DELETE key");
        System.out.println("CONTAINS key");
        System.out.println("SIZE");
        System.out.println("EXIT");

        while (true) {

            System.out.print("minikv> ");

            String input = scanner.nextLine();

            String[] parts = input.split(" ", 3);

            String command = parts[0].toUpperCase();

            try {

                switch (command) {

                    case "PUT":

                        if (parts.length < 3) {
                            System.out.println(
                                    "Usage: PUT key value"
                            );
                            break;
                        }

                        kv.put(parts[1], parts[2]);

                        System.out.println("OK");
                        break;

                    case "GET":

                        if (parts.length < 2) {
                            System.out.println(
                                    "Usage: GET key"
                            );
                            break;
                        }

                        System.out.println(
                                kv.get(parts[1])
                        );
                        break;

                    case "DELETE":

                        if (parts.length < 2) {
                            System.out.println(
                                    "Usage: DELETE key"
                            );
                            break;
                        }

                        kv.delete(parts[1]);

                        System.out.println("OK");
                        break;

                    case "CONTAINS":

                        if (parts.length < 2) {
                            System.out.println(
                                    "Usage: CONTAINS key"
                            );
                            break;
                        }

                        System.out.println(
                                kv.containsKey(parts[1])
                        );
                        break;

                    case "SIZE":

                        System.out.println(
                                kv.size()
                        );
                        break;

                    case "EXIT":

                        System.out.println(
                                "Goodbye!"
                        );

                        scanner.close();
                        return;

                    default:

                        System.out.println(
                                "Unknown command"
                        );
                }

            } catch (Exception e) {

                System.out.println(
                        "Error: " + e.getMessage()
                );
            }
        }
    }
}
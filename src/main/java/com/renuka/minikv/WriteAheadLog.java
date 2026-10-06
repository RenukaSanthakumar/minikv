package com.renuka.minikv;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriteAheadLog {

    private final String fileName = "minikv.wal";

    public void log(String operation, String key, String value) {

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(fileName, true))) {

            writer.write(operation + "|" + key + "|" + value);
            writer.newLine();

        } catch (IOException e) {
            throw new RuntimeException("Failed to write WAL", e);
        }
    }
}
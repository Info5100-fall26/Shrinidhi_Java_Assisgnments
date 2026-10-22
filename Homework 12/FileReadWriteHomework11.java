package edu.neu.mgen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileReadWriteHomework11 {
    public static void main(String[] args) throws IOException {
        Path file = Path.of("my_test_file.txt");

        String text = Files.readString(file, StandardCharsets.UTF_8);
        System.out.println("File contents: " + text);

        System.out.print("Enter text to save: ");
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String replacement = input.readLine();
        if (replacement == null) {
            throw new IOException("No text was entered.");
        }

        Files.writeString(file, replacement, StandardCharsets.UTF_8);
        System.out.println("Saved text to " + file.toAbsolutePath());
    }
}

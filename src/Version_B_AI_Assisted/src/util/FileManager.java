package util;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * File utility using Java NIO for improved performance and reliability.
 * VERSION B - AI-Assisted Approach
 * AI Prompt: "Rewrite a Java file manager utility using java.nio for reading/writing lines"
 */
public class FileManager {

    public static List<String> readLines(String filename) {
        List<String> lines = new ArrayList<>();
        Path path = Paths.get(filename);
        if (!Files.exists(path)) return lines;
        try {
            lines = Files.readAllLines(path);
            lines.removeIf(String::isBlank);
        } catch (IOException e) {
            System.err.println("[FileManager] Read error: " + e.getMessage());
        }
        return lines;
    }

    public static void writeLines(String filename, List<String> lines) {
        try {
            Files.write(Paths.get(filename), lines,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.err.println("[FileManager] Write error: " + e.getMessage());
        }
    }

    public static void appendLine(String filename, String line) {
        try {
            Files.writeString(Paths.get(filename), line + System.lineSeparator(),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("[FileManager] Append error: " + e.getMessage());
        }
    }
}

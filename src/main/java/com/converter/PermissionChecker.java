// Checks if the directory has write permission
package com.converter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PermissionChecker {
    public static void main(String[] args) {
        String directoryPath = "C:\\Users\\Kaustubh\\JIO Internship\\Audio converter";

        if (isDirectoryWritable(directoryPath)) {
            System.out.println("Directory is writable.");
        } else {
            System.out.println("Directory is not writable or does not exist.");
        }
    }

    public static boolean isDirectoryWritable(String directoryPath) {
        Path path = Paths.get(directoryPath);
        if (!Files.exists(path) || !Files.isDirectory(path)) {
            return false; // Directory does not exist or is not a directory
        }

        // Check if the directory is writable
        return Files.isWritable(path);
    }
}

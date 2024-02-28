package org.example;

import java.io.File;
import java.io.IOException;

public class AudioConverter {

    public static void main(String[] args) {
        String sourceDirectory = "C:\\Users\\Kaustubh\\JIO Internship\\Audio converter\\inputData";
        String destinationDirectory = "C:\\Users\\Kaustubh\\JIO Internship\\Audio converter\\processed_data";

        convertAudioFiles(sourceDirectory, destinationDirectory);
    }

    public static void convertAudioFiles(String sourceDirectory, String destinationDirectory) {
        File sourceDir = new File(sourceDirectory);
        File destDir = new File(destinationDirectory);

        if (!sourceDir.exists() || !sourceDir.isDirectory()) {
            System.err.println("Source directory does not exist or is not a directory.");
            return;
        }

        if (!destDir.exists() && !destDir.mkdirs()) {
            System.err.println("Failed to create destination directory: " + destinationDirectory);
            return;
        }

        File[] files = sourceDir.listFiles();
        if (files == null || files.length == 0) {
            System.out.println("No files found in the source directory.");
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                String subSourceDir = file.getAbsolutePath();
                String subDestinationDir = destinationDirectory + File.separator + file.getName();
                convertAudioFiles(subSourceDir, subDestinationDir); // Recursive call for subdirectories
            } else {
                convertFile(file.getAbsolutePath(), destinationDirectory);
            }
        }
    }

    public static void convertFile(String sourceFilePath, String destinationDirectory) {
        File sourceFile = new File(sourceFilePath);
        String fileName = sourceFile.getName();
        String fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();

        if (!isValidAudioFormat(fileExtension)) {
            System.out.println("Skipping file: " + fileName + " (Not a supported audio format)");
            return;
        }

        String destinationFilePath = destinationDirectory + File.separator + fileName.substring(0, fileName.lastIndexOf('.')) + ".wav";

        try {
            ProcessBuilder processBuilder = new ProcessBuilder("C:\\ffmpeg\\ffmpeg.exe", "-i", sourceFilePath, "-acodec", "pcm_s16le", "-ar", "44100", destinationFilePath);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Converted: " + fileName);
            } else {
                System.err.println("Failed to convert file: " + fileName);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static boolean isValidAudioFormat(String extension) {
        String[] supportedFormats = {"ogg", "mp3", "wav", "flac", "aac", "m4a"};
        for (String format : supportedFormats) {
            if (extension.equals(format)) {
                return true;
            }
        }
        return false;
    }
}

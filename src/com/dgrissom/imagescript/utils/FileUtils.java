package com.dgrissom.imagescript.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class FileUtils {
    private FileUtils() {}

    public static List<String> read(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }
    public static void write(File file, List<String> contents) throws IOException {
        Files.write(file.toPath(), contents);
    }
}

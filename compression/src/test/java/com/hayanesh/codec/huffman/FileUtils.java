package com.hayanesh.codec.huffman;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class FileUtils {
    public static String readTextFrom(String filePath) throws URISyntaxException, IOException {
        var path = getPath(filePath);

        var bufferedReader = Files.newBufferedReader(path);

        String line;
        StringBuilder stringBuilder = new StringBuilder();

        while ((line = bufferedReader.readLine()) != null)
            stringBuilder.append(line).append("\n");

        return stringBuilder.toString();
    }

    public static Path getPath(String filePath) throws URISyntaxException {
        return Path.of(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource(filePath))
                .toURI().getPath());
    }
}

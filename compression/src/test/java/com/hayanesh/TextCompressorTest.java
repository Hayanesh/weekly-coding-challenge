package com.hayanesh;

import com.hayanesh.codec.huffman.HuffmanCodec;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static com.hayanesh.codec.huffman.FileUtils.getPath;
import static com.hayanesh.codec.huffman.FileUtils.readTextFrom;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TextCompressorTest {

    private static final String FILE_NAME = "gutenberg";

    @Test
    @Order(1)
    void testCompression() throws URISyntaxException, IOException {
        TextCompressor textCompressor = new TextCompressor();
        textCompressor.compress(getPath(FILE_NAME + ".txt"));
        assertTrue(getPath(FILE_NAME + ".hf").toFile().exists());
    }

    @Test
    @Order(2)
    void testDeCompression() throws URISyntaxException, IOException {
        TextCompressor textCompressor = new TextCompressor();
        String actualText  = textCompressor.decompress(getPath(FILE_NAME + ".hf"));
        String expectedText = readTextFrom(FILE_NAME + ".txt");
        assertEquals(expectedText, actualText);
    }

}
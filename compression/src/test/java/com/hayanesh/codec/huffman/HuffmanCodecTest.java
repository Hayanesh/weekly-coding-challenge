package com.hayanesh.codec.huffman;

import com.hayanesh.TextCompressor;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static com.hayanesh.codec.huffman.FileUtils.readTextFrom;
import static org.junit.jupiter.api.Assertions.*;

class HuffmanCodecTest {

    @ParameterizedTest()
    @CsvSource(value = {"X:333", "t:223000"}, delimiter = ':')
    void givenACharacterThenReturnItsFrequency(Character letter, Integer expectedFrequency) throws URISyntaxException, IOException {
        String text = readTextFrom("gutenberg.txt");
        HuffmanCodec codec = new HuffmanCodec();
        var map = codec.buildFrequencyMap(text);
        assertEquals(expectedFrequency, map.get(letter));
    }

}
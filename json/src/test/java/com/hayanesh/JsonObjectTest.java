package com.hayanesh;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class JsonObjectTest {

    @Test
    void givenStepOneValidJson_thenReturnTrue() throws URISyntaxException, IOException {
        var parser = JsonObject.fromFile(getPath("step1/valid.json"));
        assertTrue(parser.isValid());
    }

    @Test
    void givenStepOneInValidJson_thenReturnFalse() throws URISyntaxException, IOException {
        var parser = JsonObject.fromFile(getPath("step1/invalid.json"));
        assertFalse(parser.isValid());
    }

    @ParameterizedTest(name = "Check if {arguments} has a valid json string")
    @ValueSource(strings = {"step2/valid.json", "step2/valid2.json"})
    void givenStepTwoValidJsons_thenReturnTrue(String filePath) throws URISyntaxException, IOException {
        var parser = JsonObject.fromFile(getPath(filePath));
        assertTrue(parser.isValid());
    }

    @ParameterizedTest(name = "Check if {arguments} has an invalid json string")
    @ValueSource(strings = {"step2/invalid.json", "step2/invalid2.json"})
    void givenStepTwoInValidJsons_thenReturnTrue(String filePath) throws URISyntaxException, IOException {
        var parser = JsonObject.fromFile(getPath(filePath));
        assertFalse(parser.isValid());
    }


    public static Path getPath(String filePath) throws URISyntaxException {
        return Path.of(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource(filePath))
                .toURI().getPath());
    }

}
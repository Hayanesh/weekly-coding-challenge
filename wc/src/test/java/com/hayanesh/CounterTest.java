package com.hayanesh;

import com.hayanesh.Counter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CounterTest {
    private static final Path sampleFile;

    static {
        try {
            sampleFile = Path.of(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("sample-file.txt"))
                    .toURI().getPath()
            );
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void givenCounterIsCalledWithLineCountArguments_thenReturnLineWordAndCharacterCount() {
        Counter counter = Counter.newInstance("-l");
        assertEquals(List.of(7145L), counter.getCounts(sampleFile));
    }

    @Test
    void givenCounterIsCalledWithWordCountArguments_thenReturnLineWordAndCharacterCount() {
        Counter counter = Counter.newInstance("-w");
        assertEquals(List.of(58164L), counter.getCounts(sampleFile));
    }

    @Test
    void givenCounterIsCalledWitCharacterCountArguments_thenReturnLineWordAndCharacterCount() {
        Counter counter = Counter.newInstance("-c");
        assertEquals(List.of(327911L), counter.getCounts(sampleFile));
    }

    @Test
    void givenCounterIsCalledWithNoArguments_thenReturnLineWordAndCharacterCount() {
        Counter counter = Counter.newInstance();
        assertEquals(List.of(7145L, 58164L, 327911L), counter.getCounts(sampleFile));
    }

}
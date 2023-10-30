package com.hayanesh;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URISyntaxException;
import java.nio.file.Path;
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
        Counter counter = Counter.forOption("-l");
        assertEquals(List.of(7145L), counter.getCounts(sampleFile));
    }

    @Test
    void givenCounterIsCalledWithWordCountArguments_thenReturnLineWordAndCharacterCount() {
        Counter counter = Counter.forOption("-w");
        assertEquals(List.of(58164L), counter.getCounts(sampleFile));
    }

    @Test
    void givenCounterIsCalledWitCharacterCountArguments_thenReturnLineWordAndCharacterCount() {
        Counter counter = Counter.forOption("-c");
        assertEquals(List.of(327911L), counter.getCounts(sampleFile));
    }

    @Test
    void givenCounterIsCalledWithNoArguments_thenReturnLineWordAndCharacterCount() {
        Counter counter = Counter.forAllOptions();
        assertEquals(List.of(7145L, 58164L, 327911L), counter.getCounts(sampleFile));
    }

}
package com.hayanesh.count;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class WordCount implements Count {
    @Override
    public long find(List<String> lines) {
        return lines.stream()
                .mapToLong(line ->
                        Stream.of(line.split("\\s+")).filter(Predicate.not(String::isBlank)).count()
                ).sum();
    }
}

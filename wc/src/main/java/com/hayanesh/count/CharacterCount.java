package com.hayanesh.count;

import java.util.List;

public class CharacterCount implements Count {
    @Override
    public long find(List<String> lines) {
        return lines.stream()
                .mapToLong(line -> line.getBytes().length)
                .sum();
    }
}

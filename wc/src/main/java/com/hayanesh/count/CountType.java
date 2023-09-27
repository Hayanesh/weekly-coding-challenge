package com.hayanesh.count;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

@AllArgsConstructor
public enum CountType {
    LINE_COUNT("-l", LineCount::new),
    WORD_COUNT("-w", WordCount::new),
    CHARACTER_COUNT("-c", CharacterCount::new);

    private final String command;
    private final Supplier<Count> supplier;

    public static List<CountType> all(){
        return List.of(CountType.values());
    }

    public static List<CountType> parseString(String option){
        return Stream.of(CountType.values())
                .filter(countType -> countType.hasArgument(option))
                .toList();
    }

    public long apply(List<String> lines){
        return this.supplier.get().find(lines);
    }

    private boolean hasArgument(String argument){
        return this.command.equals(argument);
    }
}

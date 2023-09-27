package com.hayanesh;

import com.hayanesh.count.CountType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class Counter {
    private final SystemHelper system;
    private final List<CountType> countTypes;
    private final CountPrinter countPrinter;

    private Counter(List<CountType> countTypes) {
        this.countTypes = countTypes;
        this.countPrinter = new CountPrinter();
        this.system = new SystemHelper();
    }

    public static Counter newInstance(){
        return new Counter(CountType.all());
    }

    public static Counter newInstance(String optionString){
        var countTypes = CountType.parseString(optionString);
        return new Counter(countTypes);
    }

    public CountPrinter getCounts(List<Path> filePaths){
        countPrinter.clear();
        filePaths.forEach(path -> countPrinter.add(path.toString(), this.getCounts(path)));
        return countPrinter;
    }

    public List<Long> getCounts(Path filePath){
        return countTypes.stream()
                .map(countType -> countType.apply(system.readFile(filePath)))
                .toList();
    }

    public CountPrinter getCountsFromStandardInput(){
        return countPrinter.single(
                countTypes.stream()
                        .map(countType -> countType.apply(system.readInputStream()))
                        .toList()
        );
    }

    public static void main(String[] args) {
        boolean optionSpecified = hasOptionSpecified(args);
        var counter = optionSpecified ? Counter.newInstance(args[0]) : Counter.newInstance();

        var counts = isFilePathProvided(args, optionSpecified) ?
                counter.getCounts(getFilePaths(args, optionSpecified)) :
                counter.getCountsFromStandardInput();

        counts.print();
    }

    private static boolean hasOptionSpecified(String[] input){
        return input.length >= 1 && input[0].startsWith("-");
    }

    private static boolean isFilePathProvided(String[] input, boolean optionSpecified){
        int fileIdx = optionSpecified ? 1 : 0;
        return input.length > fileIdx;
    }

    private static List<Path> getFilePaths(String[] input, boolean optionSpecified){
        int fileIdx = optionSpecified ? 1 : 0;
        return IntStream.range(fileIdx, input.length).mapToObj(idx -> Path.of(input[idx])).toList();
    }

    static class SystemHelper {

        public List<String> readInputStream()  {
            try(var bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
                String line;
                List<String> lines = new LinkedList<>();

                while ((line = bufferedReader.readLine()) != null)
                    lines.add(line);

                return lines;
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        public List<String> readFile(Path path)  {
            try(var bufferedReader = Files.newBufferedReader(path)) {
                String line;
                List<String> lines = new LinkedList<>();

                while ((line = bufferedReader.readLine()) != null)
                    lines.add(line);

                return lines;
            } catch (IOException e) {
                throw new IllegalStateException(path + " no such file or directory", e);
            }
        }
    }
}

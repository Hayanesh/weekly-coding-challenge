package com.hayanesh;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;


public class CountPrinter {
    private final List<String> targets = new ArrayList<>();
    private final List<List<Long>> listOfCounts = new ArrayList<>();
    private int numOfCountPerTarget = 0;
    private int size = 0;

    public void add(String target, List<Long> counts){
        this.targets.add(target);
        this.listOfCounts.add(counts);

        size++;
        this.numOfCountPerTarget = counts.size();
    }

    public CountPrinter single(List<Long> counts){
        this.clear();
        this.add("", counts);
        return this;
    }

    public void print(){
        IntStream.range(0, size).forEach(idx -> print(listOfCounts.get(idx), targets.get(idx)));

        if(size > 1){
            var sumOfCounts = IntStream.range(0, numOfCountPerTarget)
                    .mapToObj(idx -> listOfCounts.stream().mapToLong(list -> list.get(idx)).sum())
                    .toList();

            print(sumOfCounts, "total");
        }
    }

    private static void print(List<Long> counts, String marker){
        for (Long count : counts) {
            System.out.printf("%8d", count);
        }
        System.out.println("   " + marker);
    }

    public void clear(){
        targets.clear();
        listOfCounts.clear();
    }

}

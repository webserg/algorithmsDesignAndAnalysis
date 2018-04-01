package com.gmail.webserg.algorithmsAndAnalysis;

import org.junit.Assert;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CombinationsIterator implements Iterator<Collection<String>> {
    private int allCombitations;
    private int current;
    private String[][] arrays;
    private int[] indexIncideArray;

    public CombinationsIterator(List<String[]> input) {
        allCombitations = input.stream().mapToInt(i -> i.length).reduce((x, y) -> x * y).orElse(0);
        current = 0;
        arrays = input.toArray(new String[input.size()][]);
        indexIncideArray = new int[arrays.length];
    }

    @Override
    public boolean hasNext() {
        return current < allCombitations;
    }

    /**
     * @return combinations
     */
    @Override
    public Collection<String> next() {
        List<String> nextValue = IntStream.range(0, arrays.length)
                .mapToObj(i -> arrays[i][indexIncideArray[i]]).collect(Collectors.toList());
        //j - number of arrays
        for (int j = 0; j < arrays.length && ++indexIncideArray[j] == arrays[j].length; j++) {
            indexIncideArray[j] = 0;//next array, start over
        }
        current++;
        return nextValue;
    }


    public static void main(String[] args) {
        List<String[]> input = Arrays.asList(
                new String[]{"such", "nice", "question"},
                new String[]{"much", "iterator"},
                new String[]{"very", "wow"}
        );
        Iterator<Collection<String>> it = new CombinationsIterator(input);
        StringBuilder sb = new StringBuilder();
        it.forEachRemaining(sb::append);
        String res = "[such, much, very]" +
                "[nice, much, very]" +
                "[question, much, very]" +
                "[such, iterator, very]" +
                "[nice, iterator, very]" +
                "[question, iterator, very]" +
                "[such, much, wow]" +
                "[nice, much, wow]" +
                "[question, much, wow]" +
                "[such, iterator, wow]" +
                "[nice, iterator, wow]" +
                "[question, iterator, wow]";
        System.out.println(sb.toString());
        Assert.assertEquals(res, sb.toString());
    }
}

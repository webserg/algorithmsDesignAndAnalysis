package com.gmail.webserg.algorithmsAndAnalysis.v2;

import org.junit.Assert;

import java.util.Arrays;

import static com.gmail.webserg.algorithmsAndAnalysis.v2.Utils.swap;

public class ShellSort {

    public static void main(String[] args) {
        int n = 4;
//        int[] input = Utils.shuffle(Utils.getSequence(n));
        int[] input = new int[]{3, 8, 2, 5, 1, 4, 7, 6};
        long start = System.currentTimeMillis();
        shellSort(input);
        long end = System.currentTimeMillis();
        System.out.println(end - start);

        System.out.println(Arrays.toString(input));
        Assert.assertTrue(Utils.isSorted(input));

    }

    private static void shellSort(int[] input) {
        final int N = input.length;
        int h = 1;
        while (h < N / 3) h = 3 * h + 1;
        while (h >= 1) {
            for (int i = h; i < N; i++) {
                for (int j = i; j >= h && input[j] < input[j - h]; j -= h) {
                    swap(input, j, j - h);
                }
            }
            h = h / 3;
        }
    }
}

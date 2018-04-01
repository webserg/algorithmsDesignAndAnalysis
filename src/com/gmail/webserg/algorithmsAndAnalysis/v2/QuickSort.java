package com.gmail.webserg.algorithmsAndAnalysis.v2;

import org.junit.Assert;

import java.util.Arrays;

import static com.gmail.webserg.algorithmsAndAnalysis.v2.Utils.swap;

public class QuickSort {
    private static void sort(int[] input, int start, int end) {
        if (end - start <= 0) return;
        int pdx = choosePivot(input, start, end);
        pdx = partition(input, pdx, end);
        sort(input, start, pdx - 1);
        sort(input, pdx + 1, end);
    }

    private static int partition(int[] input, int pdx, int r) {
        int p = input[pdx];
        int i = pdx + 1;
        for (int j = i; j <= r; j++) {
            if (input[j] < p) {
                swap(input, i, j);
                i++;
            }
        }
        swap(input, pdx, i - 1);
        return i - 1;
    }


    private static int choosePivot(int[] input, int start, int end) {
        return start;
    }

    public static void main(String[] args) {
        int n = 4;
//        int[] input = Utils.shuffle(Utils.getSequence(n));
        int[] input = new int[]{3, 8, 2, 5, 1, 4, 7, 6};
        long start = System.currentTimeMillis();
        sort(input, 0, input.length - 1);
        long end = System.currentTimeMillis();
        System.out.println(end - start);

        System.out.println(Arrays.toString(input));
        Assert.assertTrue(Utils.isSorted(input));

    }
}

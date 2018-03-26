package com.gmail.webserg.algorithmsAndAnalysis.v2.cormen;

import com.gmail.webserg.algorithmsAndAnalysis.v2.Utils;
import org.junit.Assert;

import java.util.Arrays;

public class InsertionSort {
    public static void main(String[] args) {
        int n = 20000;
        int[] input = Utils.shuffle(Utils.getSequence(n));
//        System.out.println(Arrays.toString(input));
        int[] res = insertionSort(input, 0, input.length - 1);
//        System.out.println(Arrays.toString(res));
        Assert.assertTrue(Utils.isSorted(res));
    }

    public static int[] insertionSort(int[] input, final int from, final int to) {
        for (int i = from + 1; i <= to; i++) {
            int j = i - 1;
            int key = input[i];
            while (j >= from && input[j] > key) {
                input[j + 1] = input[j];
                j--;
            }
            input[j + 1] = key;
        }
        return input;
    }
}

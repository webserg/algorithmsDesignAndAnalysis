package com.gmail.webserg.algorithmsAndAnalysis.v2.cormen;

import com.gmail.webserg.algorithmsAndAnalysis.v2.Utils;
import org.junit.Assert;

import java.util.Arrays;

/***
 * cormen 2-2-2
 */
public class SelectionSort {
    public static void main(String[] args) {
        int n = 10;
        int[] input = Utils.shuffle(Utils.getSequence(n));
        System.out.println(Arrays.toString(input));
        int[] res = selectionSort(n, input);
        System.out.println(Arrays.toString(res));
        Assert.assertTrue(Utils.isSorted(res));
    }

    private static int[] selectionSort(int n, int[] input) {
        boolean changed = false;
        for (int i = 0; i < n; i++) {
            int min = i;
            for (int k = i; k < n; k++) {
                if (input[k] < input[min]) {
                    min = k;
                    changed = true;
                }
            }
            if (changed) {
                int tmp = input[i];
                input[i] = input[min];
                input[min] = tmp;
            }
        }
        return input;
    }
}

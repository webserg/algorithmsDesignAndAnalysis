package com.gmail.webserg.algorithmsAndAnalysis.v2.cormen;

import com.gmail.webserg.algorithmsAndAnalysis.v2.Utils;
import org.junit.Assert;

import java.util.Arrays;

public class MergeSortSedgwick {
    private static Comparable[] temp;

    public static void main(String[] args) {
        int n = 11;
        int[] input = Utils.shuffle(Utils.getSequence(n));
        temp = new Comparable[n];
        Comparable[] source = new Comparable[n];
        for (int i = 0; i < n; i++) {
            source[i] = input[i];
        }

        System.out.println(Arrays.toString(source));
        Comparable[] res = mergeSort(source, 0, input.length - 1);

        System.out.println(Arrays.toString(res));
        Assert.assertTrue(Utils.isSorted(res));
    }

    private static Comparable[] mergeSort(Comparable[] source, int left, int right) {
        if (left < right) {
            int m = (left + right) >>> 1;
            mergeSort(source, left, m);
            mergeSort(source, m + 1, right);
            merge(source, left, m, m + 1, right);
        }
        return source;
    }

    private static void merge(Comparable[] source, final int firstLeft, final int lastLeft, final int firstRight, final int lastRight) {
        int k = firstLeft;
        int j = firstRight;
        for (int i = firstLeft; i <= lastRight; i++) {
            temp[i] = source[i];
        }
        for (int i = firstLeft; i <= lastRight; i++) {
            if (k > lastLeft) source[i] = temp[j++];
            else if (j > lastRight) source[i] = temp[k++];
            else if (temp[k].compareTo(temp[j]) < 1) {
                source[i] = temp[k++];
            } else source[i] = temp[j++];
        }

    }
}

package com.gmail.webserg.algorithmsAndAnalysis.v2.cormen;

import com.gmail.webserg.algorithmsAndAnalysis.v2.Utils;
import org.junit.Assert;

public class MergeSort {
    public static void main(String[] args) {
        int n = 200000;
        int[] input = Utils.shuffle(Utils.getSequence(n));
//        System.out.println(Arrays.toString(input));
        long start = System.currentTimeMillis();
        int[] res = mergeSort(input, 0, input.length - 1);
        long end = System.currentTimeMillis();
        System.out.println(end - start);

//        System.out.println(Arrays.toString(res));
        Assert.assertTrue(Utils.isSorted(res));
    }

    private static void merge(int[] source, int firstLeft, int lastLeft, int firstRight, int lastRight) {
        int[] temp = new int[lastRight - firstLeft + 1];
        int idx = 0, saveF = firstLeft;
        while (firstLeft <= lastLeft && firstRight <= lastRight) {
            if (source[firstLeft] < source[firstRight]) {
                temp[idx++] = source[firstLeft++];
            } else {
                temp[idx++] = source[firstRight++];
            }
        }
        while (firstLeft <= lastLeft) {
            temp[idx++] = source[firstLeft++];
        }
        while (firstRight <= lastRight) {
            temp[idx++] = source[firstRight++];
        }
        for (int j = saveF, i = 0; j <= lastRight; j++, i++) {
            source[j] = temp[i];
        }
    }

    private static int[] mergeSort(int[] source, int l, int r) {
        if (l < r) {
            int m = (l + r) >>> 1;
            mergeSort(source, l, m);
            mergeSort(source, m + 1, r);
            merge(source, l, m, m + 1, r);
        }
        return source;
    }

}

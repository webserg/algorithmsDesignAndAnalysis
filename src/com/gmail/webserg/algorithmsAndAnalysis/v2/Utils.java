package com.gmail.webserg.algorithmsAndAnalysis.v2;

public class Utils {
    /**
     * randomly shuffle array of int
     *
     * @param source input array
     * @return output shuffled array
     */
    public static int[] shuffle(int[] source) {
        int l = source.length;
        int[] target = new int[l];
        int k;
        for (int i = 0; i < target.length; i++) {
            k = (int) (Math.random() * l--);
            target[i] = source[k];
            source[k] = source[l];
        }
        return target;
    }

    public static int[] getSequence(int n) {
        int[] input = new int[n];
        for (int i = 0; i < n; i++) {
            input[i] = i + 1;
        }
        return input;
    }

    public static boolean isSorted(int[] source) {
        boolean res = false;
        for (int i = 0; i < source.length - 1; i++) {
            res = source[i] <= source[i + 1];
        }
        return res;
    }

    public static boolean isSorted(Comparable[] source) {
        boolean res = false;
        for (int i = 0; i < source.length - 1; i++) {
            res = source[i].compareTo(source[i + 1]) < 1;
        }
        return res;
    }

    public static void swap(int[] input, int i, int j) {
        int temp = input[i];
        input[i] = input[j];
        input[j] = temp;
    }


}

package com.gmail.webserg.algorithmsAndAnalysis.v1;

import java.util.Arrays;

public class ShuffleArray {

    /**
     * @param args
     */
    public static void main(String[] args) {
        int[] k = {0,1,2,3,4,5,6,7};
        System.out.println(Arrays.toString(ShuffleArray.shuffle(k)));
    }

    public static int[] shuffle(int[] source){
        int l = source.length;
        int[] target = new int[l];
        int k;
        for (int i = 0; i < target.length ; i++) {
            k = (int)(Math.random() * l--);
            target[i] = source[k];
            source[k] = source[l];
        }
        return target;
    }

}

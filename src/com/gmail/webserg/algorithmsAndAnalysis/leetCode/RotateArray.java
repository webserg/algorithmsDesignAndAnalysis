package com.gmail.webserg.algorithmsAndAnalysis.leetCode;

import java.util.Arrays;

/*
Given an array, rotate the array to the right by k steps, where k is non-negative.

Input: nums = [1,2,3,4,5,6,7], k = 3
Output: [5,6,7,1,2,3,4]
Explanation:
rotate 1 steps to the right: [7,1,2,3,4,5,6]
rotate 2 steps to the right: [6,7,1,2,3,4,5]
rotate 3 steps to the right: [5,6,7,1,2,3,4]
 */
public class RotateArray {

    static public void rotate(int[] nums, int k) {
        for (int i = 0; i < k; i++)
            rotate2(nums);
    }


    static void rotate1(int[] arr) {
        int i = 0, j = arr.length - 1;
        while (i != j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
            i++;
        }
    }

    static void rotate2(int[] arr) {
        int i, temp;
        temp = arr[0];
        int n = arr.length;
        for (i = 0; i < n - 1; i++)
            arr[i] = arr[i + 1];
        arr[n - 1] = temp;
    }

    /* Driver program */
    public static void main(String[] args) {
        System.out.println("Given Array is");
        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7};
        System.out.println(Arrays.toString(arr));
        rotate1(arr);

        System.out.println("Rotated Array is");
        System.out.println(Arrays.toString(arr));

        System.out.println("Given Array is");
        arr = new int[]{1, 2, 3, 4, 5, 6, 7};
        System.out.println(Arrays.toString(arr));
        rotate2(arr);

        System.out.println("Rotated Array is");
        System.out.println(Arrays.toString(arr));

        System.out.println("Given Array is");
        arr = new int[]{1, 2, 3, 4, 5, 6, 7};
        System.out.println(Arrays.toString(arr));
        rotate(arr, 3);

        System.out.println("Rotated Array is");
        System.out.println(Arrays.toString(arr));

        System.out.println("Given Array is");
        arr = new int[]{-1, -100, 3, 99};
        System.out.println(Arrays.toString(arr));
        rotate(arr, 2);

        System.out.println("Rotated Array is");
        System.out.println(Arrays.toString(arr));

    }
}

package com.gmail.webserg.algorithmsAndAnalysis.leetCode;

/**
 * 26. Remove Duplicates from Sorted Array
 * https://leetcode.com/problems/remove-duplicates-from-sorted-array/
 * <p>
 * Given a sorted array, remove the duplicates in place such that each element appear only once and return the new length.
 * <p>
 * Do not allocate extra space for another array, you must do this in place with constant memory.
 * <p>
 * For example,
 * Given input array nums = [1,1,2],
 * <p>
 * Your function should return length = 2, with the first two elements of nums being 1 and 2 respectively.
 * It doesn't matter what you leave beyond the new length.
 * Input  : arr[] = {2, 2, 2, 2, 2}
 * Output : arr[] = {2}
 * new size = 1
 * <p>
 * Input  : arr[] = {1, 2, 2, 3, 4, 4, 4, 5, 5}
 * Output : arr[] = {1, 2, 3, 4, 5}
 * new size = 5
 */
public class RemoveDuplicateArray {
    public static int removeDuplicates(int[] nums) {
        if (nums.length < 2) return nums.length;
        int j = 0;
        for (int i = 1; i < nums.length; i++) {
            if(nums[i] != nums[j]){
                j++;
                nums[j] = nums[i];
            }
        }
        System.out.print("[");
        for (int i = 0; i < j+1; i++) {
            System.out.print(nums[i] + " ");
        }
        System.out.println("]");
        return j+1;
    }

    public static int removeDuplicates2(int[] nums) {
        if (nums.length < 2) return nums.length;
        int j = 0;
        for (int i = 1; i < nums.length; i++) {
            if(nums[i] != nums[j]){
                j++;
                nums[j] = nums[i];
            }
        }
        System.out.print("[");
        for (int i = 0; i < j+1; i++) {
            System.out.print(nums[i] + " ");
        }
        System.out.println("]");
        return j+1;
    }

    public static void main(String[] args) {
        int[] arr = {2, 2, 2, 2, 2};
        System.out.println(removeDuplicates(arr));
        int[] arr2 = {1, 2, 2, 3, 4, 4, 4, 5, 5};
        System.out.println(removeDuplicates(arr2));
    }
}

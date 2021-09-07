package com.gmail.webserg.algorithmsAndAnalysis.leetCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 350. Intersection of Two Arrays II
 * https://leetcode.com/problems/intersection-of-two-arrays-ii/
 * <p>
 * Given two arrays, write a function to compute their intersection.
 * <p>
 * Example:
 * Given nums1 = [1, 2, 2, 1], nums2 = [2, 2], return [2, 2].
 * <p>
 * Note:
 * Each element in the result should appear as many times as it shows in both arrays.
 * The result can be in any order.
 * Follow up:
 * What if the given array is already sorted? How would you optimize your algorithm?
 * What if nums1's size is small compared to nums2's size? Which algorithm is better?
 * What if elements of nums2 are stored on disk, and the memory is limited such that you
 * cannot load all elements into the memory at once?
 * <p>
 * https://discuss.leetcode.com/topic/45893/c-hash-table-solution-and-sort-two-pointers-solution-with-time-and-space-complexity
 * time complexity O(log n + log m + m + n) - I`m not really sure
 * <p>
 * https://discuss.leetcode.com/topic/45992/solution-to-3rd-follow-up-question
 * If only nums2 cannot fit in memory, put all elements of nums1 into a HashMap,
 * read chunks of array that fit into the memory, and record the intersections.
 * <p>
 * If both nums1 and nums2 are so huge that neither fit into the memory,
 * sort them individually (external sort), then read 2 elements from each
 * array at a time in memory, record intersections.
 * <p>
 * https://discuss.leetcode.com/topic/45920/ac-solution-using-java-hashmap
 */
public class IntersectionOfTwoArrays {

    public static int[] intersect(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        int i = 0, j = 0;
        List<Integer> list = new ArrayList<>();
        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] < nums2[j]) i++;
            else if (nums1[i] > nums2[j]) j++;
            else {
                list.add(nums1[i]);
                i++;
                j++;
            }
        }
        int[] res = new int[list.size()];
        int ind = 0;
        for (int k : list) res[ind++] = k;
        return res;
    }

    public static void main(String[] args) {
        int[] nums1 = {1, 2, 3, 4, 5, 5, 6, 6};
        int[] nums2 = {5, 6, 6, 0, 30, 76};
        int[] res = intersect(nums1, nums2);
        System.out.print(Arrays.toString(res));
        System.out.println();
        int[] nums11 = {1, 2, 2, 1};
        int[] nums22 = {2, 2};
        res = intersect(nums11, nums22);
        System.out.print(Arrays.toString(res));
    }
}

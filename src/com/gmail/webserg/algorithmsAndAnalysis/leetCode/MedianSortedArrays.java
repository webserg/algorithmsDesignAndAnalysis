package com.gmail.webserg.algorithmsAndAnalysis.leetCode;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class MedianSortedArrays {
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        PriorityQueue<Integer> heapMax = new PriorityQueue<>(Comparator.<Integer>naturalOrder().reversed());
        heapMax.addAll(Arrays.stream(nums1).boxed().collect(Collectors.toList()));
        PriorityQueue<Integer> heapMin = new PriorityQueue<>();
        heapMin.addAll(Arrays.stream(nums2).boxed().collect(Collectors.toList()));
        int min = heapMin.peek() == null ? 0 : heapMin.peek();
        int max = heapMax.peek() == null ? 0 : heapMax.peek();
        if(heapMax.isEmpty()){
            return min;
        }
        if(heapMin.isEmpty()){
            return max;
        }
        if (heapMax.size() == heapMin.size()) {
            return (min + max) / 2.0;
        } else if (heapMax.size() < heapMin.size()) {
            return max;
        } else {
            return min;
        }
    }

    public static void main(String[] args) {
        double res = findMedianSortedArrays(new int[]{1, 2}, new int[]{3, 4});
        System.out.println(res);
        res = findMedianSortedArrays(new int[]{1, 3}, new int[]{2});
        System.out.println(res);
    }
}

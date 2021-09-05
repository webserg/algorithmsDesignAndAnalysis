package com.gmail.webserg.algorithmsAndAnalysis.leetCode.trees;

/**
 * https://leetcode.com/explore/interview/card/top-interview-questions-easy/94/trees/631/
 * Given an integer array nums where the elements are sorted in ascending order, convert it to a height-balanced binary search tree.
 * <p>
 * A height-balanced binary tree is a binary tree in which the depth of the two subtrees of every node never differs by more than one.
 * <p>
 * <p>
 * <p>
 * Example 1:
 * <p>
 * <p>
 * Input: nums = [-10,-3,0,5,9]
 * Output: [0,-3,9,-10,null,5]
 * Explanation: [0,-10,5,null,-3,null,9] is also accepted:
 * <p>
 * Example 2:
 * <p>
 * <p>
 * Input: nums = [1,3]
 * Output: [3,1]
 * Explanation: [1,3] and [3,1] are both a height-balanced BSTs.
 */
public class ConvertSortedArrayToBST {

    static void preOrder(TreeNode node) {
        if (node == null) {
            return;
        }
        System.out.print(node.val + " ");
        preOrder(node.left);
        preOrder(node.right);
    }

    public static TreeNode sortedArrayToBST(int[] nums) {
        return sortedArrayToBST(nums, 0, nums.length - 1);
    }

    static TreeNode sortedArrayToBST(int[] nums, int start, int stop) {
        if (start > stop) {
            return null;
        }
        int len = (start + stop);
        int mid = len >>> 1;
        TreeNode root = new TreeNode(nums[mid]);
        root.left = sortedArrayToBST(nums, start, mid - 1);
        root.right = sortedArrayToBST(nums, mid + 1, stop);
        return root;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(2);
        root.left = new TreeNode(1);
        root.right = new TreeNode(3);
        System.out.println(sortedArrayToBST(new int[]{1, 2, 3}));
        preOrder(sortedArrayToBST(new int[]{1, 2, 3}));
        System.out.println();
        preOrder(sortedArrayToBST(new int[]{1, 2, 3, 4, 5, 6, 7}));


    }
}

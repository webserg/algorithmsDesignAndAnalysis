package com.gmail.webserg.algorithmsAndAnalysis.leetCode.trees;

/**
 * Given the root of a binary tree, return its maximum depth.
 * <p>
 * A binary tree's maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.
 * <p>
 * Input: root = [3,9,20,null,null,15,7]
 * Output: 3
 * Example 2:
 * <p>
 * Input: root = [1,null,2]
 * Output: 2
 * Example 3:
 * <p>
 * Input: root = []
 * Output: 0
 * Example 4:
 * <p>
 * Input: root = [0]
 * Output: 1
 */
public class MaximumDepthOfBinaryTree {
    public static int maxDepth(TreeNode root, int l) {
        if (root == null) return l;
        int left = maxDepth(root.left, l + 1);
        int right = maxDepth(root.right, l + 1);

        return Math.max(left, right);
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);

        System.out.println(maxDepth(root, 0));

        root = new TreeNode(1);
        root.right = new TreeNode(2);

        System.out.println(maxDepth(root, 0));

        root = new TreeNode(0);

        System.out.println(maxDepth(root, 0));

        root = new TreeNode(5);
        root.left = new TreeNode(4);
        root.right = new TreeNode(6);
        root.left.left = new TreeNode(2);
        root.left.right = new TreeNode(3);
        root.left.right.left = new TreeNode(10);

        System.out.println(maxDepth(root, 0));
    }
}

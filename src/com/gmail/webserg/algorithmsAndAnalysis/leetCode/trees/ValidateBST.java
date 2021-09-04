package com.gmail.webserg.algorithmsAndAnalysis.leetCode.trees;

/**
 * Validate Binary Search Tree
 *
 * Solution
 * Given the root of a binary tree, determine if it is a valid binary search tree (BST).
 *
 * A valid BST is defined as follows:
 *
 * The left subtree of a node contains only nodes with keys less than the node's key.
 * The right subtree of a node contains only nodes with keys greater than the node's key.
 * Both the left and right subtrees must also be binary search trees.
 */
public class ValidateBST {
    // https://discuss.leetcode.com/topic/7179/my-simple-java-solution-in-3-lines
    // https://en.wikipedia.org/wiki/Binary_search_tree - tree validation
    // The main idea is node value should be  min<val<max, how to get min and max?
    // for left node - min = parent.min, max = parent.value
    // for right node - min = parent.value, max = parent.max
    public static boolean validate(TreeNode root, long min, long max) {
        if(root == null) return true;
        System.out.println(min + " < " + root.val +" && "+ root.val +" < "+ max);
        if (min < root.val && root.val < max) {
            return validate(root.left, min, root.val) && validate(root.right, root.val, max);
        }
        return false;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(2);
        root.left = new TreeNode(1);
        root.right = new TreeNode(3);
        System.out.println(validate(root, Long.MIN_VALUE, Long.MAX_VALUE));

        root = new TreeNode(5);
        root.left = new TreeNode(1);
        root.right = new TreeNode(4);
        root.right.left = new TreeNode(3);
        root.right.right = new TreeNode(6);
        System.out.println(validate(root, Long.MIN_VALUE, Long.MAX_VALUE));

        root = new TreeNode(3);
        root.left = new TreeNode(2);
        root.right = new TreeNode(5);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(4);
        System.out.println(validate(root, Long.MIN_VALUE, Long.MAX_VALUE));

        root = new TreeNode(1);
        root.left = null;
        root.right = new TreeNode(1);
        System.out.println(validate(root, Long.MIN_VALUE, Long.MAX_VALUE));

        root = new TreeNode(Integer.MAX_VALUE);
        root.left = new TreeNode(Integer.MAX_VALUE);
        System.out.println(validate(root, Long.MIN_VALUE, Long.MAX_VALUE));

        root = new TreeNode(Integer.MAX_VALUE);
        System.out.println(validate(root, Long.MIN_VALUE, Long.MAX_VALUE));
    }
}

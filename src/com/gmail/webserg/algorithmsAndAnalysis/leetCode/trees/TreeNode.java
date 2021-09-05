package com.gmail.webserg.algorithmsAndAnalysis.leetCode.trees;

public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }

    @Override
    public String toString() {
        if (this == null) return " null ";
        String leftStr = this.left != null ? this.left.toString() : "null";
        String rightStr = this.right != null ? this.right.toString() : "null";
        return "ROOT: " + val + " LEFT: " + leftStr + " RIGHT: " + rightStr;
    }
}

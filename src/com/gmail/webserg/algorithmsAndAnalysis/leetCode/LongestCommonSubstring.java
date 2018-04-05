package com.gmail.webserg.algorithmsAndAnalysis.leetCode;

/**
 * In computer science, the longest common substring problem is to find the longest string that is a substring of two or more strings.
 * <p>
 * Analysis
 * <p>
 * Given two strings a and b, let dp[i][j] be the length of the common substring ending at a[i] and b[j].
 * https://www.programcreek.com/2015/04/longest-common-substring-java/
 */
public class LongestCommonSubstring {
    static int longestCommonSubstring(String s1, String s2) {
        int max = 0;
        int dp[][] = new int[s1.length()][s2.length()];
        for (int i = 0; i < s1.length(); i++) {
            for (int j = 0; j < s2.length(); j++) {
                if (s1.charAt(i) == s2.charAt(j)) {
                    if (i == 0 || j == 0) {
                        dp[i][j] = 1;
                    } else {
                        dp[i][j] = dp[i - 1][j - 1] + 1;
                        if (max < dp[i][j]) max = dp[i][j];
                    }
                }
            }
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println(longestCommonSubstring("abac", "asdasdaabacdtttrrr"));
    }
}

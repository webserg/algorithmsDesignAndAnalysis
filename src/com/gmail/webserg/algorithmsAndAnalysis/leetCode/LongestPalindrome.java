package com.gmail.webserg.algorithmsAndAnalysis.leetCode;

/**
 * Input: s = "babad"
 * Output: "bab"
 * Note: "aba" is also a valid answer.
 */
public class LongestPalindrome {
    static String longestPalindrome(String s1) {
        int max = 0;
        String s2 = reverse(s1);
        int start = 0;
        int start_i = 0;
        int[][] dp = new int[s1.length()][s2.length()];
        for (int i = 0; i < s1.length(); i++) {
            for (int j = 0; j < s2.length(); j++) {
                if (s1.charAt(i) == s2.charAt(j)) {
                    if (i == 0 || j == 0) {
                        dp[i][j] = 1;
                        if (j == 0) start_i = i;
                    } else {
                        dp[i][j] = dp[i - 1][j - 1] + 1;
                        if (max < dp[i][j]) {
                            max = dp[i][j];
                            start = start_i;
                        }
                    }
                }
            }
        }
        return s1.substring(start, start + max);
    }

    static String reverse(String s) {
        char[] c = new char[s.length()];
        for (int i = s.length() - 1, j = 0; i >= 0; i--, j++) {
            c[i] = s.charAt(j);
        }
        return new String(c);
    }

    public static void main(String[] args) {
        System.out.println(longestPalindrome("caba"));
    }
}

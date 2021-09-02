package com.gmail.webserg.algorithmsAndAnalysis.leetCode;

/**
 * Given two strings s1 and s2, find if s1 is a substring of s2. If yes, return the index of the first occurrence, else return -1.
 * <p>
 * Examples :
 * <p>
 * Input: s1 = "for", s2 = "geeksforgeeks"
 * Output: 5
 * Explanation:
 * String "for" is present as a substring
 * of s2.
 * <p>
 * Input: s1 = "practice", s2 = "geeksforgeeks"
 * Output: -1.
 * Explanation:
 * There is no occurrence of "practice" in
 * "geeksforgeeks"
 */
public class Strstr {
    public static int strStr(String haystack, String needle) {
        if (needle.length() > haystack.length()) return -1;
        if (needle.length() == 0) return 0;
        char first = needle.charAt(0);
        for (int i = 0; i < haystack.length(); i++) {
            if (first == haystack.charAt(i)) {
                boolean found = true;
                for (int j = 1; j < needle.length(); j++) {
                    if (i + j >= haystack.length() || needle.charAt(j) != haystack.charAt(i + j)) {
                        found = false;
                        break;
                    }
                }
                if (found) return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println(strStr("geeksforgeeks", "for"));
    }
}

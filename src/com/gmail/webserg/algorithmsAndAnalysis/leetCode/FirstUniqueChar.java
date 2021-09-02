package com.gmail.webserg.algorithmsAndAnalysis.leetCode;

/**
 * Given a string s, find the first non-repeating character in it and return its index. If it does not exist, return -1.
 * Example 1:
 * <p>
 * Input: s = "leetcode"
 * Output: 0
 */
public class FirstUniqueChar {
    public static int firstUniqChar(String s) {
        int[] fi = new int[256]; // array to store First Index

        // initializing all elements to -1
        for (int i = 0; i < 256; i++)
            fi[i] = -1;

        // sets all repeating characters to -2 and non-repeating characters
        // contain the index where they occur
        for (int i = 0; i < s.length(); i++) {
            if (fi[s.charAt(i)] == -1) {
                fi[s.charAt(i)] = i;
            } else {
                fi[s.charAt(i)] = -2;
            }
        }

        int res = Integer.MAX_VALUE;

        for (int i = 0; i < 256; i++) {

            // If this character is not -1 or -2 then it
            // means that this character occurred only once
            // so find the min index of all characters that
            // occur only once, that's our first index
            if (fi[i] >= 0)
                res = Math.min(res, fi[i]);
        }

        // if res remains  Integer.MAX_VALUE, it means there are no
        // characters that repeat only once or the string is empty
        if (res == Integer.MAX_VALUE) return -1;
        else return res;
    }


    public static void main(String[] args) {
        System.out.println(firstUniqChar("leetcode"));
        System.out.println(firstUniqChar("loveleetcode"));
        System.out.println(firstUniqChar("aabb"));
    }
}

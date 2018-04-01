package com.gmail.webserg.algorithmsAndAnalysis.leetCode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Given a string, find the length of the longest substring without repeating characters.
 * <p>
 * Examples:
 * <p>
 * Given "abcabcbb", the answer is "abc", which the length is 3.
 * <p>
 * Given "bbbbb", the answer is "b", with the length of 1.
 * <p>
 * Given "pwwkew", the answer is "wke", with the length of 3. Note that the answer must be a substring, "pwke" is a subsequence and not a substring.
 */
public class LengthOfLongestSubstring {
    public int lengthOfLongestSubstring(String s) {
        int n = s.length(), ans = 0;
        Map<Character, Integer> map = new HashMap<>(); // current index of character
        // try to extend the range [i, j]
        for (int j = 0, i = 0; j < n; j++) {
            if (map.containsKey(s.charAt(j))) {
                i = Math.max(map.get(s.charAt(j)), i);
            }
            ans = Math.max(ans, j - i + 1);
            map.put(s.charAt(j), j + 1);
        }
        return ans;
    }

    public static void main(String[] args) {
        LengthOfLongestSubstring solution = new LengthOfLongestSubstring();
        assert solution.lengthOfLongestSubstring("abcabcbb") == 3;
        assert solution.lengthOfLongestSubstring("bbbbb") == 1;
        assert solution.lengthOfLongestSubstring("pwwkew") == 3;
        assert solution.lengthOfLongestSubstring("qwertyqwertyrqwertypf") == 8;
        assert solution.lengthOfLongestSubstring("c") == 1;
        assert solution.lengthOfLongestSubstring("dvdf") == 3;
        assert solution.lengthOfLongestSubstring("dvdfdwerty") == 7;
        assert solution.lengthOfLongestSubstring("jbpnbwwd") == 4;
//        assert solution.lengthOfLongestSubstring("\"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\\\"#$%&'()*+,-./:;<=>?@[\\\\]^_`{|}~ abcdefghijklmno") == 4;
    }
}

package com.gmail.webserg.algorithmsAndAnalysis.leetCode.string;

import java.util.Arrays;

/*
67. Add Binary
https://leetcode.com/problems/add-binary/

Given two binary strings, return their sum (also a binary string).

For example,
a = "11"
b = "1"
Return "100".

Example 1:

Input: a = "11", b = "1"
Output: "100"
Example 2:

Input: a = "1010",
       b = "1011"
  Output: "10101"
*/
public class AddBinary {

    public static String plusOne(String a, String b) {
        if (a.length() == 0 && b.length() == 0) return "";
        StringBuilder result = new StringBuilder();
        int i = a.length() - 1, j = b.length() - 1, remainder = 0;
        while (i >= 0 || j >= 0) {
            int res = 0;
            if (i >= 0) {
                res += a.charAt(i) - '0';
                i--;
            }
            if (j >= 0) {
                res += b.charAt(j) - '0';
                j--;
            }
            res += remainder;
            result.append(res % 2);
            remainder = res / 2;
        }
        if (remainder == 1) result.append(1);
        return result.reverse().toString();
    }

    public static void main(String[] args) {
        System.out.println(plusOne("11", "1"));
        System.out.println(plusOne("11", "11"));
        System.out.println(plusOne("1010", "1011"));
    }
}

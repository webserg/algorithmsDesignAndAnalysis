package com.gmail.webserg.algorithmsAndAnalysis.leetCode;

public class MyAtoi {
    // A simple atoi() function
    static int myAtoi(String str) {
        int res = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            res = res * 10 + c - '0';
        }

        return res;
    }

    public static void main(String[] args) {
        System.out.println(myAtoi("123"));
        System.out.println(myAtoi("007"));
    }
}

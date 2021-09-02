package com.gmail.webserg.algorithmsAndAnalysis.leetCode;

/**
 * The atoi() function in C takes a string (which represents an integer) as an argument and returns its value of type int. So basically the function is used to convert a string argument to an integer.
 *
 * Syntax:
 *
 * int atoi(const char strn)
 * Parameters: The function accepts one parameter strn which refers to the string argument that is needed to be converted into its integer equivalent.
 *
 * Return Value: If strn is a valid input, then the function returns the equivalent integer number for the passed string number. If no valid conversion takes place, then the function returns zero.
 */
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

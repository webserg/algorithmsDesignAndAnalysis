package com.gmail.webserg.algorithmsAndAnalysis.leetCode;

/**
 * Given a signed 32-bit integer x, return x with its digits reversed. If reversing x causes the value to go outside
 * the signed 32-bit integer range [-231, 231 - 1], then return 0.
 */
public class ReverseInteger {

    /* Iterative function to reverse
   digits of num*/
    static int reversDigits(int num)
    {
        int rev_num = 0;
        while(num > 0)
        {
            rev_num = rev_num * 10 + num % 10;
            num = num / 10;
        }
        return rev_num;
    }

    // Driver code
    public static void main (String[] args)
    {
        int num = 4562;
        System.out.println("Reverse of no. is "
                + reversDigits(num));
    }

}

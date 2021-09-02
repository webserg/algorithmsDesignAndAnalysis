package com.gmail.webserg.algorithmsAndAnalysis.leetCode;

/**
 * Given a string s, determine if it is a palindrome, considering only alphanumeric characters and ignoring cases.
 * <p>
 * <p>
 * <p>
 * Example 1:
 * <p>
 * Input: s = "A man, a plan, a canal: Panama"
 * Output: true
 * Explanation: "amanaplanacanalpanama" is a palindrome.
 * Example 2:
 * <p>
 * Input: s = "race a car"
 * Output: false
 * Explanation: "raceacar" is not a palindrome.
 * <p>
 * <p>
 * Constraints:
 * <p>
 * 1 <= s.length <= 2 * 105
 * s consists only of printable ASCII characters.
 */
public class ValidPalindrome {

    static boolean sentencePalindrome(String str) {
        str = str.toLowerCase();
        int left = 0;
        int right = str.length() - 1;
        while (left <= right) {
            if (isNotLetter(str.charAt(left))) left++;
            else if (isNotLetter(str.charAt(right))) right--;
            else if (str.charAt(left) == str.charAt(right)) {
                left++;
                right--;
            } else return false;
        }

        return true;
    }

    static boolean isNotLetter(char c) {
        return !(c >= 'a' && c <= 'z');
    }

    public static void main(String[] args) {
        isPalindrome("A man, a plan, a canal: Panama");
        isPalindrome("Too hot to hoot.");
        isPalindrome("race a car");
    }

    private static void isPalindrome(String str) {
        if (sentencePalindrome(str)) System.out.println("Sentence is palindrome");
        else
            System.out.println("Sentence is not" + " " + "palindrome");
    }
}

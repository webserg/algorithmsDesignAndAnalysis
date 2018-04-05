package com.gmail.webserg.algorithmsAndAnalysis.leetCode;

import org.junit.Assert;

public class ReverseWords {
    public String reverseWords(String s) {
        String[] words = s.trim().replaceAll("\\s+", " ").split(" ");
        StringBuilder sb = new StringBuilder();
        for (int i = words.length - 1; i >= 0; i--) {
            sb.append(words[i]);
            if (i > 0) sb.append(" ");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        ReverseWords solution = new ReverseWords();
        Assert.assertEquals("blue is sky the", solution.reverseWords("the sky is blue"));
        Assert.assertEquals("", solution.reverseWords(""));
        Assert.assertEquals(" ", solution.reverseWords(" "));
    }
}

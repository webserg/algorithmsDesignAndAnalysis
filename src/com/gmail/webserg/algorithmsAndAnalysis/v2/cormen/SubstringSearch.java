package com.gmail.webserg.algorithmsAndAnalysis.v2.cormen;

import javafx.util.Pair;
import org.junit.Assert;

public class SubstringSearch {
    static int searchSubString(String pat, String txt) {
        int M = pat.length();
        int N = txt.length();
        for (int i = 0; i <= N - M; i++) {
            int j;
            for (j = 0; j < M; j++) {
                if (txt.charAt(i + j) != pat.charAt(j)) {
                    break;
                }
            }
            if (j == M) return i;
            else i += j;
        }

        return -1;
    }

    public static void main(String[] args) {
        Pair<String, String> pairs[] = new Pair[]{
                new Pair<>("ABRA", "ABACADABRAC"),
                new Pair<>("sky", "the sky is blue"),
                new Pair("aa", "abcaab"), new Pair("ab", "abc"),
                new Pair("wer", "abchfhgfhgfhgfhgfvcbcgjhkjk"),
                new Pair("", "abchfhgfhgfhgfhgfvcbcgjhkjk"),
                new Pair("f", "abchfhgfhgfhgfhgfvcbcgjhkjk"),
                new Pair("jk", "abchfhgfhgfhgfhgfvcbcgjhkjk")};
        for (int i = 0; i < pairs.length; i++) {
            Assert.assertEquals(pairs[i].getValue().indexOf(pairs[i].getKey()),
                    searchSubString(pairs[i].getKey(), (pairs[i].getValue())));
        }
    }
}

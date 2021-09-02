package com.gmail.webserg.algorithmsAndAnalysis.hackerRank;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CompareTriplets {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int a0 = in.nextInt();
        int a1 = in.nextInt();
        int a2 = in.nextInt();
        int b0 = in.nextInt();
        int b1 = in.nextInt();
        int b2 = in.nextInt();
        List<Integer> alise = new ArrayList<>();
        alise.add(a0);
        alise.add(a1);
        alise.add(a2);

        List<Integer> bob = new ArrayList<>();
        bob.add(b0);
        bob.add(b1);
        bob.add(b2);
        int bobScore = 0;
        int aliseScore = 0;
        for (int i = 0; i < 3; i++) {
            if (alise.get(i) != bob.get(i))
                if (alise.get(i) < bob.get(i)) bobScore += 1;
                else aliseScore += 1;
        }
        System.out.println(aliseScore + " " + bobScore);
    }
}

package com.gmail.webserg.algorithmsAndAnalysis.v1;

import org.junit.Assert;
import org.junit.Test;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * User: webserg
 * Date: 16.03.13
 */
public class MedianMaintenance {
    private static Logger log = Logger.getLogger(MedianMaintenance.class.getName());

    {
        log.setLevel(Level.SEVERE);
    }

    @Test
    public void testMedianMaintenance1() throws Exception {

        int[] intArray = FilesHelper.readIntArrayFromFile("resource/medianTest1.txt", 6);
        Assert.assertEquals(50, run(intArray));
    }

    public static void main(String[] args) throws Exception {
        int[] intArray = FilesHelper.readIntArrayFromFile("resource/median.txt", 10000);

        int res = new MedianMaintenance().run(intArray);
        log.severe(res + "");//1213
    }

    private int run(int[] intArray) {
        int[] resArray = new int[intArray.length];
        int resSum = 0;
        PriorityQueue<Integer> heapMax = new PriorityQueue<>(intArray.length, Comparator.<Integer>naturalOrder().reversed());
        PriorityQueue<Integer> heapMin = new PriorityQueue<>(intArray.length);

        for (int i = 0; i < intArray.length; i++) {
            int n = intArray[i];
            if (heapMax.isEmpty() && heapMin.isEmpty()) {
                heapMax.offer(n);
            } else {
                if (n < heapMax.peek()) {
                    heapMax.offer(n);
                } else {
                    heapMin.offer(n);
                }
                if (heapMax.size() < heapMin.size() - 1)
                    heapMax.offer(heapMin.poll());
                else if (heapMin.size() < heapMax.size() - 1)
                    heapMin.offer(heapMax.poll());
            }


            resArray[i] = heapMax.size() < heapMin.size() ? heapMin.peek() : heapMax.peek();
            System.out.println(resArray[i]);
            resSum += resArray[i];

        }

        return resSum % 10000;
    }
}

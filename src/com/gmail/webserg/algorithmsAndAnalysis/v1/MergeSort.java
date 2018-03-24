package com.gmail.webserg.algorithmsAndAnalysis.v1;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by webserg on 23.05.2014.
 * dfgdf
 * dfgdf
 */
public class MergeSort {
    int[] source;

    @Test
    public void testMergeSort() throws Exception {

        mergeSort(new int[]{12, 66, 8, 45});
        Assert.assertArrayEquals(new int[]{8, 12, 45, 66}, source);
//        mergeSort(new int[]{12, 66, 8, 45, 0});
//        Assert.assertArrayEquals(new int[]{0, 8, 12, 45, 66}, source);
    }

    public void mergeSort(int[] s) throws Exception {
        source = s;
        if (source.length < 2)
            return;
        split(0, s.length - 1);

    }

    private void split(int first, int last) {
        if (first < last) {
            int mid = (first + last) >>> 1;
            split(first, mid);
            split(mid + 1, last);
            merge(first, mid + 1, last);
        }
    }

    private void merge(final int first, final int mid2, final int last) {
        int[] temp = new int[last - first + 1];
        int l = first;
        int r = mid2;
        int i=0;
        for (;l < mid2 && r <= last; i++) {
            if (source[l] < source[r]) {
                temp[i] = source[l++];
            } else {
                temp[i] = source[r++];
            }
        }
        while (l < mid2) temp[i++] = source[l++];
        while (r <= last) temp[i++] = source[r++];
        System.arraycopy(temp, 0, source, first, last - first + 1);
    }
}

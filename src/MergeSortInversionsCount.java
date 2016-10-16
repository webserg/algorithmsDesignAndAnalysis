import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * User: webserg
 * Date: 12.02.13
 * This file contains all of the 100,000 integers between 1 and 100,000
 * (inclusive) in some order, with no integer repeated.
 * <p/>
 * Your task is to compute the number of inversions in the file given,
 * where the ith row of the file indicates the ith entry of an array.
 *
 * inversions = number of pair[i,j] of array indices with i < j and A[i] > A[j]
 */
public class MergeSortInversionsCount {
    private static int[] source2;
    private static long inversions2;

    @Test
    public void testMergeSort() throws Exception {
        source2 = new int[]{2,3,4,1,5,6};
        source2 = FilesHelper.readIntArrayFromFile("resource/IntegerArray2.txt", 100000);
        mergeSort(source2,0,source2.length-1);
        System.out.println("source2 = " + Arrays.toString(source2));
        System.out.println("inversions2 = " + inversions2);
    }

    private static void mergeSort(int[] array, final int i, final int j) {
        if (i < j) {
            int m = (i + j) >>> 1;
            mergeSort(array, i, m);
            mergeSort(array, m + 1, j);
            merge(array, i, m, j);
        }
    }

    private static void merge(int[] array, final int i, final int m, final int j) {
        int kk = i;
        int left[] = Arrays.copyOfRange(array, i, m + 1);
        int right[] = Arrays.copyOfRange(array, m + 1, j + 1);
        int leftK = 0, rightK = 0;
        for (; kk < j && leftK < left.length && rightK < right.length; kk++) {
            if (left[leftK] < right[rightK]) {
                array[kk] = left[leftK++];
            } else {
                array[kk] = right[rightK++];
                inversions2 += (left.length - leftK);
            }
        }
        while (leftK < left.length && kk <= j) {
            array[kk++] = left[leftK++];
        }
        while (rightK < right.length && kk <= j) {
            array[kk++] = right[rightK++];
        }
    }
}

import org.junit.Test;

import java.util.Arrays;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * User: webserg
 * Date: 16.02.13
 */
public class QuickSortComparCountLast {
    long comparison = 0;

    @Test
    public void testSort() throws Exception {
        System.out.println("start...");
        int result[] = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
//        int source[] = new int[]{9,8,7,6,5,4,3,2,1};
        int[] source = ShuffleArray.shuffle(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
//        int[] source = new int[]{3, 8, 2, 5};
//        int source[] = FilesHelper.readIntArrayFromFile("resource/QuickSort.txt", 10000);
//        assertFalse(Arrays.equals(result, source));
        System.out.println(Arrays.toString(source));
        quickSort(source, 0, source.length - 1);
        System.out.println("comparison = " + comparison);
        FilesHelper.writeIntArrayToFile("resource/out.txt", source);
        System.out.println(Arrays.toString(source));
        assertTrue(Arrays.equals(result, source));
    }

    private void quickSort(int a[], int str, int l) {
        if ((l - str) <= 0) return;
        comparison += l - str;
        int pdx = str;
        pdx = partitioning(a, pdx, l);
        quickSort(a, str, pdx - 1);
        quickSort(a, pdx + 1, l);
    }

    private int partitioning(int[] a, int pdx, int len) {
        int p = a[pdx];
        int i = pdx + 1;
        for (int j = pdx + 1; j <= len; j++) {
            if (a[j] < p) {
                swap(a, j, i);
                i++;
            }
        }
        swap(a, pdx, i - 1);
        return i - 1;
    }

    private static void swap(int a[], int i, int j) {
        int t;
        t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private int choosePivot(int a[], int str, int l) {
        return pivotLikeLastElement(a, str, l);
    }

    private int pivotLikeFirstElement(int a[], int str, int l) {
        return str;
    }

    private int pivotLikeLastElement(int a[], int str, int l) {
        return l;
    }
}

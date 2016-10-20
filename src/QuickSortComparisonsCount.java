import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * User: webserg
 * Date: 16.02.13
 */
public class QuickSortComparisonsCount {
    long comparison = 0;
    boolean debug = false;
    private static final int FIRST_PIVOT = 1;
    private static final int LAST_PIVOT = 2;
    private static final int MIDDLE_PIVOT = 3;
    private static int CURRENT_PIVOT = FIRST_PIVOT;

    @Test
    public void testSortSimle() throws Exception {
        System.out.println("start...");
        if (debug) {
            int result[] = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
            int[] source = new int[]{1, 9, 8, 7, 6, 5, 4, 3, 2};
            assertFalse(Arrays.equals(result, source));
            System.out.println(Arrays.toString(source));
            quickSort(source, 0, source.length - 1);
            System.out.println(Arrays.toString(source));
            assertTrue(Arrays.equals(result, source));
        }
    }

    @Test
    public void testSort() throws Exception {
        System.out.println("start...");
        if (debug) {
            int result[] = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
            int[] source = ShuffleArray.shuffle(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
            assertFalse(Arrays.equals(result, source));
            System.out.println(Arrays.toString(source));
            quickSort(source, 0, source.length - 1);
            System.out.println(Arrays.toString(source));
            assertTrue(Arrays.equals(result, source));
        } else {
            int source[] = FilesHelper.readIntArrayFromFile("resource/QuickSort.txt", 10000);
            quickSort(source, 0, source.length - 1);
            FilesHelper.writeIntArrayToFile("resource/out.txt", source);
        }
        System.out.println("comparison = " + comparison);
    }

    @Test
      public void testMedian() throws Exception {
        int source[] = new int[]{8, 2, 4, 5, 7, 1};
        int med = pivotMedianOfThree(source, 0, source.length-1);
        assertEquals(4,source[med]);
    }

    @Test
    public void testMedian2() throws Exception {
        int source[] = new int[]{1, 2, 4, 5, 7, 3};
        int med = pivotMedianOfThree(source, 0, source.length-1);
        assertEquals(3,source[med]);
    }

    @Test
    public void testMedian3() throws Exception {
        int source[] = new int[]{1, 2, 8, 5, 7, 4};
        int med = pivotMedianOfThree(source, 0, source.length-1);
        assertEquals(4,source[med]);
    }

    @Test
    public void testMedianOdd() throws Exception {
        int source[] = new int[]{8, 2, 4, 5, 7};
        int med = pivotMedianOfThree(source, 0, source.length-1);
        assertEquals(7,source[med]);
    }

    @Test
    public void testMedianOdd2() throws Exception {
        int source[] = new int[]{4,5,6,7};
        int med = pivotMedianOfThree(source, 0, source.length-1);
        assertEquals(5,source[med]);
    }

    /**
     * @param a array
     * @param str first index
     * @param l last index
     */
    private void quickSort(int a[], int str, int l) {
        if ((l - str) <= 0) return;
        comparison += l - str;
        int pdx = choosePivot(a, str, l);
        if (pdx != str) swap(a, str, pdx);
        pdx = partitioning(a, str, l);
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
        switch (CURRENT_PIVOT) {
            case FIRST_PIVOT:
                return pivotLikeFirstElement(a, str, l);
            case LAST_PIVOT:
                return pivotLikeLastElement(a, str, l);
            case MIDDLE_PIVOT:
                return pivotMedianOfThree(a, str, l);
            default:
                return pivotLikeFirstElement(a, str, l);
        }
    }

    private int pivotLikeFirstElement(int a[], int str, int l) {
        return str;
    }

    private int pivotLikeLastElement(int a[], int str, int l) {
        return l;
    }

    private int pivotMedianOfThree(int a[], int str, int l) {
        int middle = (str + l) >>> 1;

        if(a[str] > a[middle] && a[str] > a[l]){
            if(a[l] < a[middle]) return middle;
            else return l;
        } else if(a[middle] > a[str] && a[middle] > a[l]){
            if(a[l] < a[str]) return str;
            else return l;
        } else if(a[str] < a[middle]) return middle;
        else return str;

    }
}

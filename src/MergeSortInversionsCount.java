import org.junit.Assert;
import org.junit.Test;

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
    private static int[] source;
    private static long inversions;

    @Test
    public void testMergeSort() throws Exception {
        System.out.println("start...");
        source = FilesHelper.readIntArrayFromFile("resource/IntegerArray.txt", 100000);
        mergeSort(source);
        FilesHelper.writeIntArrayToFile("resource/out.txt", source);
        FilesHelper.writeLongToFile("resource/inversions.txt", inversions);
        System.out.println("inversions = " + inversions);
        Assert.assertEquals(2407905288L, inversions);
    }

    public void mergeSort(int[] s) throws Exception {
        source = s;
        if (source.length < 2)
            return;
        split(0, s.length - 1);

    }

    private static void split(final int first, final int last) {
        if (first < last) {
            int middle = (first + last) >>> 1;
            split(first, middle);
            if (first < last) split(middle + 1, last);
            merge(first, middle, middle + 1, last);
        }
    }

    private static void merge(int fl, int ll, int fr, int lr) {
        int[] temp = new int[source.length];
        int tmp_idx = fl;
        int saveF = fl;
        while (fl <= ll && fr <= lr) {
            if (source[fl] < source[fr]) {
                temp[tmp_idx] = source[fl];
                fl++;
            } else {
                temp[tmp_idx] = source[fr];
                fr++;
                inversions += (ll + 1 - fl);
            }
            tmp_idx++;
        }
        while (fl <= ll) temp[tmp_idx++] = source[fl++];
        while (fr <= lr) temp[tmp_idx++] = source[fr++];
        System.arraycopy(temp, saveF, source, saveF, lr - saveF + 1);
    }
}

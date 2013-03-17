import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * User: webserg
 * Date: 16.03.13
 */
public class SumOfTwoProblem {
    private static Logger log = Logger.getLogger(MedianMaintenance.class.getName());

    {
        log.setLevel(Level.SEVERE);
    }

    @Test
    public void testMedianMaintenance1() throws Exception {

        int[] intArray = FilesHelper.readIntRangeArrayFromFile("resource/HashInt.txt", 500000, 2500, 4000);
        Assert.assertEquals(50, run(intArray,2500, 4000));
    }

    @Test
    public void testMedianMaintenance2() throws Exception {

        int[] intArray = FilesHelper.readIntRangeArrayFromFile("resource/hashIntTest1.txt", 100, 30, 60);
        Assert.assertEquals(9, run(intArray, 30, 60));
    }

    @Test
    public void testMedianMaintenance3() throws Exception {

        int[] intArray = FilesHelper.readIntRangeArrayFromFile("resource/hashIntTest1.txt", 100, 60, 100);
        Assert.assertEquals(28, run(intArray, 60, 100));
    }

    private int run(int[] intArray, int startRange, int endRange) {
        Set<Integer> hashSet = new HashSet<>(intArray.length);
        Set<Integer> hashSetT = new HashSet<>(intArray.length);
        for (int n : intArray) {
            hashSet.add(n);
        }
        int resCount = 0;
        for (int t = startRange; t <= endRange; t++) {
            for (int x : intArray) {
                int y = t - x;
                if (hashSet.contains(y) && !hashSetT.contains(t)) {
                    if (x != y) {
//                        log.info("x=" + x + "; y=" + y +";t=" + t);
                        hashSetT.add(t);
                        resCount++;
                    }
                }
            }

        }
        return resCount;
    }
}

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
       // Assert.assertEquals(50, run(intArray,2500, 4000));
    }

    @Test
    public void testMedianMaintenance2() throws Exception {

        long[] intArray = FilesHelper.readLongArrayFromFile("resource/hashIntTest1.txt", 100, 30, 60);
        Assert.assertEquals(9, run(intArray, 30, 60));
    }

    @Test
    public void testMedianMaintenance3() throws Exception {

        long[] intArray = FilesHelper.readLongArrayFromFile("resource/hashIntTest1.txt", 100, 60, 100);
        Assert.assertEquals(28, run(intArray, 60, 100));
    }

    @Test
    public void testMedianMaintenance() throws Exception {

        long[] intArray = FilesHelper.readLongArrayFromFile("resource/2sum.txt", 1000000, -10000, 10000);
        Assert.assertEquals(28, run(intArray, -10000, 10000));
    }

    private int run(long[] intArray, long startRange, long endRange) {
        Set<Long> hashSet = new HashSet<>(intArray.length);
        Set<Long> hashSetT = new HashSet<>(intArray.length);
        for (long n : intArray) {
            hashSet.add(n);
        }
        int resCount = 0;
        for (long t = startRange; t <= endRange; t++) {
            for (long x : intArray) {
                long y = t - x;
                boolean xMinusYPlus = y > 0 && x < 0;
                boolean yMinusXPlus = y < 0 && x > 0;
                if(xMinusYPlus || yMinusXPlus)
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

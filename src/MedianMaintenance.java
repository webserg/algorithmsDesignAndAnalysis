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
        log.severe(res + "");
    }

    private int run(int[] intArray) {
        Comparator<Integer> comparatorLow = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                int res = o1.compareTo(o2);
                if (res == 0) return 0;
                return res < 0 ? 1 : -1;
            }
        };
        int[] resArray = new int[intArray.length];
        int resSum = 0;
        PriorityQueue<Integer> hlow = new PriorityQueue<>(intArray.length, comparatorLow);
        PriorityQueue<Integer> hhight = new PriorityQueue<>(intArray.length);

        for (int i = 0; i < intArray.length; i++) {
            int n = intArray[i];
            if (hlow.isEmpty() && hhight.isEmpty()) {
                hlow.offer(n);
            } else {
                if (n < hlow.peek()) {
                    hlow.offer(n);
                } else {
                    hhight.offer(n);
                }
                if (hlow.size() < hhight.size() - 1)
                    hlow.offer(hhight.poll());
                else if (hhight.size() < hlow.size() - 1)
                    hhight.offer(hlow.poll());
            }


            resArray[i] = hlow.size() < hhight.size() ? hhight.peek() : hlow.peek();
            resSum += resArray[i];

        }

        return resSum % 10000;
    }
}

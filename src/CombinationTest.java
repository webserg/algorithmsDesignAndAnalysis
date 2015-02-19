import org.junit.Test;

import java.util.Arrays;

/**
 * Created by webserg on 08.07.2014.
 */
public class CombinationTest {
    @Test
    public void test() {

        Combination c52 = new Combination(5, 3);
        long l = c52.size();
        println("size(c52): %d", l);
        println("c52", (CombinationIterator) c52.iterator());
        /*
         * size(c52): 10
         * c52:
         * -> [0, 1]
         * -> [0, 2]
         * -> [0, 3]
         * -> [0, 4]
         * -> [1, 2]
         * -> [1, 3]
         * -> [1, 4]
         * -> [2, 3]
         * -> [2, 4]
         * -> [3, 4]
         */

        long m = l / 2;
        CombinationIterator c52_lhs = new CombinationIterator(5, 2, 0, m);
        CombinationIterator c52_rhs = new CombinationIterator(5, 2, m + 1);

        println("c52_lhs", c52_lhs);
        /*
         * c52_lhs:
         * -> [0, 1]
         * -> [0, 2]
         * -> [0, 3]
         * -> [0, 4]
         * -> [1, 2]
         */

        println("c52_rhs", c52_rhs);
        /*
         * c52_rhs:
         * -> [2, 3]
         * -> [2, 4]
         * -> [3, 4]
         * -> [3, 4]
         */

        println("5C2: %d", Combination.choose(5, 2));
        /*
         * 5C2: 10
         */

        int[] c = new int[2];
        Combination.get(5, 2, 3, c);
        println("3rd combination of 5C2: %s", Arrays.toString(c));
        /*
         * 3rd combination of 5C2: [0, 4]
         */

        Combination.get(5, 2, 8, c);
        println("8th combination of 5C2: %s", Arrays.toString(c));
        /*
         * 8th combination of 5C2: [2, 4]
         */
    }

    private static void println(String fmt, Object... args) {
        System.out.println(String.format(fmt, args));
    }

    private static void println(String caption, CombinationIterator ci) {
        println("%s:", caption);
        while (ci.hasNext())
            println("-> %s", Arrays.toString(ci.next()));
    }
}

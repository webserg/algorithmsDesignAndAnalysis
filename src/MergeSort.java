import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * User: webserg
 * Date: 12.02.13
 */
public class MergeSort {
    private static int[] source;
    private static long inversions;

    private void writeIntArrayToFile(String filePath, int[] arr) {
        Charset charset = Charset.forName("UTF-8");
        Path path = Paths.get(filePath);
        try (BufferedWriter writer = Files.newBufferedWriter(path, charset)) {
            for (int i = 0; i < arr.length; i++) {
                writer.write("" + arr[i], 0, ("" + arr[i]).length());
                writer.newLine();
            }
            writer.flush();
            writer.close();
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    private void writeLongToFile(String filePath, long longNumber) {
        Charset charset = Charset.forName("UTF-8");
        Path path = Paths.get(filePath);
        try (BufferedWriter writer = Files.newBufferedWriter(path, charset)) {
            writer.write("" + longNumber, 0, ("" + longNumber).length());

            writer.flush();
            writer.close();
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    private int[] readIntArrayToFile(String filePath, int arraySize) {
        Charset charset = Charset.forName("UTF-8");
        Path path = Paths.get(filePath);
        int result[] = new int[arraySize];
        int idx = 0;
        try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
            String line;
            while ((line = reader.readLine()) != null) {
                result[idx++] = Integer.parseInt(line);
            }

            reader.close();
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
        return result;
    }

    @Test
    public void testMergeSort() throws Exception {
        System.out.println("start...");
        source = readIntArrayToFile("resource/IntegerArray.txt", 100000);
        mergeSort(source);
        writeIntArrayToFile("resource/out.txt", source);
        writeLongToFile("resource/inversions.txt", inversions);
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

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by webserg on 25.06.2014.
 */
public class KnapsackProblem {
    /**
     * // Input:
     * // Values (stored in array v)
     * // Weights (stored in array w)
     * // Number of distinct items (n)
     * // Knapsack capacity (W)
     * for j from 0 to W do
     * m[0, j] := 0
     * end for
     * for i from 1 to n do
     * for j from 0 to W do
     * if w[i] <= j then
     * m[i, j] := max(m[i-1, j], m[i-1, j-w[i]] + v[i])
     * else
     * m[i, j] := m[i-1, j]
     * end if
     * end for
     * end for
     */
    @Test
    public void test1() throws Exception {

        int[] v = new int[]{4, 2, 2, 1, 10};
        int[] w = new int[]{12, 2, 1, 1, 4};
        int res = calculate(v, w, 5, 15);
        System.out.println(res);
    }
    @Test
    public void test3() throws Exception {

        int[] v = new int[]{4, 2, 2, 1, 10};
        int[] w = new int[]{12, 2, 1, 1, 4};
        int res = calculate2(v, w, 5, 15);
        System.out.println(res);
    }

    @Test
    public void test() throws Exception {
        Data data = buildData("resource/knapsack1.txt");
        int res = calculate(data.v, data.w, data.n, data.W);
        System.out.println(res);
    }

    @Test
    public void test2() throws Exception {
        Data data = buildData("resource/knapsack1.txt");
        int res = calculate2(data.v, data.w, data.n, data.W);
        System.out.println(res);
        Assert.assertEquals(2493893,res);
    }

    @Test
    public void testSolution2() throws Exception {
        Data data = buildData("resource/knapsack2.txt");
        int res = calculate2(data.v, data.w, data.n, data.W);
        System.out.println(res);
    }

    static class Data {
        int[] v;
        int[] w;
        int n;
        int W;

        Data(int[] v, int[] w, int n, int w1) {
            this.v = v;
            this.w = w;
            this.n = n;
            this.W = w1;
        }
    }

    int calculate2(int[] v, int[] w, int n, int W) {
        int m[][] = new int[2][W];//subproblems
        for (int i = 1; i < n; i++) {//number of value
            int ii=1;
            for (int j = 0; j < W; j++) {//weights
                if (w[i] <= j) { // if weights less then capacity
                    m[ii][j] = Math.max(m[ii - 1][j], m[ii - 1][j - w[i]] + v[i]);
                } else {
                    m[ii][j] = m[ii - 1][j];
                }
            }
            m[0] = m[1];
            m[1] = new int[W];
        }
        return m[0][W - 1];
    }

    int calculate(int[] v, int[] w, int n, int W) {
        int m[][] = new int[n][W];//subproblems
        for (int i = 1; i < n; i++) {//number of value
            for (int j = 0; j < W; j++) {//weights
                if (w[i] <= j) { // if weights less then capacity
                    m[i][j] = Math.max(m[i - 1][j], m[i - 1][j - w[i]] + v[i]);
                } else {
                    m[i][j] = m[i - 1][j];
                }
            }
        }
        return m[n - 1][W - 1];
    }

    static Data buildData(String filePath) {
        Charset charset = Charset.forName("UTF-8");
        Path path = Paths.get(filePath);
        int n, W;
        int[] v;
        int[] w;
        try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
            String line = reader.readLine();
            String[] strLine = line.split("\\s+");
            W = Integer.parseInt(strLine[0]);
            n = Integer.parseInt(strLine[1]);
            v = new int[n];
            w = new int[n];
            for (int i = 0; i < n; i++) {
                line = reader.readLine();
                strLine = line.split("\\s+");
                v[i] = Integer.parseInt(strLine[0]);
                w[i] = Integer.parseInt(strLine[1]);
            }
            return new Data(v, w, n, W);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

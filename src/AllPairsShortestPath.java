import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by webserg on 30.06.2014.
 * In this assignment you will implement one or more algorithms for the all-pairs shortest-path problem. Here are data files describing three graphs: graph #1; graph #2; graph #3.
 * The first line indicates the number of vertices and edges, respectively. Each subsequent line describes an edge (the first two numbers are its tail and head, respectively)
 * and its length (the third number).
 * NOTE: some of the edge lengths are negative. NOTE: These graphs may or may not have negative-cost cycles.
 * <p>
 * Your task is to compute the "shortest shortest path". Precisely, you must first identify which, if any, of the three graphs have no negative cycles.
 * For each such graph, you should compute all-pairs shortest paths and remember the smallest one (i.e., compute minu,v∈Vd(u,v), where d(u,v) denotes the shortest-path distance from u to v).
 * <p>
 * If each of the three graphs has a negative-cost cycle, then enter "NULL" in the box below. If exactly one graph has no negative-cost cycles, then enter the
 * length of its shortest shortest path in the box below. If two or more of the graphs have no negative-cost cycles, then enter the smallest of the lengths of their
 * shortest shortest paths in the box below.
 */
public class AllPairsShortestPath {
    static final int INFINITY = Integer.MAX_VALUE;

    @Test
    public void testAPSP1() throws Exception {
        long res = run(readGraphFromFile("resource/g0.txt"));
        Assert.assertEquals(1, res);
    }

    @Test
    public void testAPSPgTest1() throws Exception {
        long res = run(readGraphFromFile("resource/gTest1.txt"));
        Assert.assertEquals(-6, res);
    }

    @Test
    public void testAPSPgTest2() throws Exception {
        long res = run(readGraphFromFile("resource/gTest2.txt"));
        Assert.assertEquals(-7, res);
    }

    @Test
    public void testAPSPgTest3() throws Exception {
        long res = run(readGraphFromFile("resource/gTest3.txt"));
        Assert.assertEquals(Integer.MIN_VALUE, res);
    }

    @Test
    public void testAPSPgTest4() throws Exception {
        long res = run(readGraphFromFile("resource/gTest4.txt"));
        Assert.assertEquals(-8, res);
    }

    @Test
    public void testAPSPgTest5() throws Exception {
        long res = run(readGraphFromFile("resource/gTest5.txt"));
        Assert.assertEquals(-3, res);
    }

    @Test
    public void testAPSPgTest6() throws Exception {
        long res = run(readGraphFromFile("resource/gTest6.txt"));
        Assert.assertEquals(3, res);
    }

    @Test
    public void testAPSP2() throws Exception {
        long res = run(readGraphFromFile("resource/g1.txt"));
        Assert.assertEquals(Integer.MIN_VALUE, res);
    }

    @Test
    public void testAPSP3() throws Exception {
        long res = run(readGraphFromFile("resource/g2.txt"));
        Assert.assertEquals(Integer.MIN_VALUE, res);
    }

    @Test
    public void testAPSP4() throws Exception {
        long res = run(readGraphFromFile("resource/g3.txt"));
        Assert.assertEquals(-1, res);
    }

    @Test
    public void testAPSPLarge() throws Exception {
        long res = run(readGraphFromFile("resource/large.txt"));//not solved out of memory, looks like need another algorithm
        Assert.assertEquals(-6, res);
    }

    int run(int[][][] D) {
        final int N = D[0].length;
        int res = INFINITY;
        int[][] B = new int[N][N];
        int kk = 1; // need only two arrays current k, and prev k-1
        for (int k = 1; k < N; k++) {
            for (int i = 1; i < N; i++)
                for (int j = 1; j < N; j++) {
                    int newW;
                    int p1 = D[kk - 1][i][k];
                    int p2 = D[kk - 1][k][j];
                    if (p1 == INFINITY || p2 == INFINITY) {
                        newW = INFINITY;
                    } else {
                        newW = p1 + p2;
                    }
                    if (D[kk - 1][i][j] > newW) {
                        D[kk][i][j] = newW;
                        B[i][j] = k;
                    } else {
                        D[kk][i][j] = D[kk - 1][i][j];
                    }
                }
            D[kk - 1] = D[kk];//save current
            D[kk] = new int[N][N];//prepare array for new values
        }

        for (int i = 1; i < N; i++) {
            if (D[0][i][i] < 0) { //diagonal contains negative values, so we have negative cycle, not suitable for this algorithm abrupt
                System.out.println("negative cycle");
                return Integer.MIN_VALUE;
            }
            for (int j = 1; j < N; j++) {
                if (i != j && res > D[0][i][j]) // looking up for min
                    res = D[0][i][j];
            }
        }
        return res;
    }

    static int[][][] readGraphFromFile(String filePath) {
        Charset charset = Charset.forName("UTF-8");
        Path path = Paths.get(filePath);
        int[][][] graph = null;
        int k = 0;
        int N ;
        try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
            String line = reader.readLine();
            String[] strLine = line.split("\\s+");
            N = Integer.parseInt(strLine[0]);

            int edgesNum = Integer.parseInt(strLine[1]);
            graph = new int[2][N + 1][N + 1];
            //prepare default value
            for (int i = 1; i < N + 1; i++)
                for (int j = 1; j < N + 1; j++) {
                    if (i != j) {
                        graph[k][i][j] = INFINITY;//no way between this vertexes
                    }
                }
            int i, j;//idx of vertexes
            int w;//weight
            while ((line = reader.readLine()) != null) {
                strLine = line.split("\\s+");
                i = Integer.parseInt(strLine[0]);
                j = Integer.parseInt(strLine[1]);
                w = Integer.parseInt(strLine[2]);
                graph[k][i][j] = w;
            }
        } catch (IOException x) {
            System.out.println("IOException:" + x.getMessage());
        }

        return graph;
    }

}

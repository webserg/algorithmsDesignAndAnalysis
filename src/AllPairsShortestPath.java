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
    static final long INFINITY = Long.MAX_VALUE;

    @Test
    public void testMST1() throws Exception {
        long res = run(readGraphFromFile("resource/g0.txt"));
        Assert.assertEquals(0, res);
    }

    @Test
    public void testMSTgTest1() throws Exception {
        long res = run(readGraphFromFile("resource/gTest1.txt"));
        Assert.assertEquals(-6, res);
    }

    @Test
    public void testMSTgTest2() throws Exception {
        long res = run(readGraphFromFile("resource/gTest2.txt"));
        Assert.assertEquals(-7, res);
    }

    @Test
    public void testMSTgTest3() throws Exception {
        long res = run(readGraphFromFile("resource/gTest3.txt"));
        Assert.assertEquals(Integer.MIN_VALUE, res);
    }

    @Test
    public void testMSTgTest4() throws Exception {
        long res = run(readGraphFromFile("resource/gTest4.txt"));
        Assert.assertEquals(-8, res);
    }

    @Test
    public void testMSTgTest5() throws Exception {
        long res = run(readGraphFromFile("resource/gTest5.txt"));
        Assert.assertEquals(-3, res);
    }

    @Test
    public void testMSTgTest6() throws Exception {
        long res = run(readGraphFromFile("resource/gTest6.txt"));
        Assert.assertEquals(3, res);
    }

    @Test
    public void testMST2() throws Exception {
        long res = run(readGraphFromFile("resource/g1.txt"));
        Assert.assertEquals(Long.MIN_VALUE, res);
    }

    @Test
    public void testMST3() throws Exception {
        long res = run(readGraphFromFile("resource/g2.txt"));
        Assert.assertEquals(Long.MIN_VALUE, res);
    }

    @Test
    public void testMST4() throws Exception {
        long res = run(readGraphFromFile("resource/g3.txt"));
        Assert.assertEquals(-1, res);
    }

    long run(long[][][] D) {
        final int N = D[0].length;
        long res = INFINITY;
        int[][] B = new int[N][N];
        int kk = 1;
        for (int k = 1; k < N; k++) {
            for (int i = 1; i < N; i++)
                for (int j = 1; j < N; j++) {
                    long newW;
                    long p1 = D[kk - 1][i][k];
                    long p2 = D[kk - 1][k][j];
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
            D[kk - 1] = D[kk];
            D[kk] = new long[N][N];
        }

        for (int i = 1; i < N; i++) {
            if (D[0][i][i] < 0) {
                System.out.println("negative cycle");
                return Long.MIN_VALUE;
            }
            for (int j = 1; j < N; j++) {

                if (i != j && res > D[0][i][j])
                    res = D[0][i][j];
            }
        }
        return res;
    }

    static long[][][] readGraphFromFile(String filePath) {
        Charset charset = Charset.forName("UTF-8");
        Path path = Paths.get(filePath);
        long[][][] graph = null;
        int k = 0;
        int N = 0;
        try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
            String line = reader.readLine();
            String[] strLine = line.split("\\s+");
            N = Integer.parseInt(strLine[0]);

            int edgesNum = Integer.parseInt(strLine[1]);
            graph = new long[2][N + 1][N + 1];
            int i, j;
            long w;

            while ((line = reader.readLine()) != null) {
                strLine = line.split("\\s+");
                i = Integer.parseInt(strLine[0]);
                j = Integer.parseInt(strLine[1]);
                w = Long.parseLong(strLine[2]);
                graph[k][i][j] = w;
            }
        } catch (IOException x) {
            System.out.println("IOException:" + x.getMessage());
        }

        for (int i = 1; i < N + 1; i++)
            for (int j = 1; j < N + 1; j++) {
                if (i != j && graph[k][i][j] == 0) {
                    graph[k][i][j] = INFINITY;
                }
            }
        return graph;
    }

}

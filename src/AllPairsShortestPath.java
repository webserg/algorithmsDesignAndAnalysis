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
    public void testMST1() throws Exception {
        int[][][] D = readGraphFromFile("resource/g0.txt");
        int res = run(D);
        Assert.assertEquals(5, res);
    }

    @Test
    public void testMST2() throws Exception {
        int[][][] D = readGraphFromFile("resource/g1.txt");
        int res = run(D);
        Assert.assertEquals(-1, res);
    }

    @Test
    public void testMST3() throws Exception {
        int[][][] D = readGraphFromFile("resource/g2.txt");
        int res = run(D);
        Assert.assertEquals(-1, res);
    }

    @Test
    public void testMST4() throws Exception {
        int[][][] D = readGraphFromFile("resource/g3.txt");
        int res = run(D);
        Assert.assertEquals(-1, res);
    }

    int run(int[][][] D) {
        int N = D.length;
        int res = N + 1;
        int[][] B = new int[N][N];
        int kk=1;
        for (int k = 1; k < N; k++) {
            for (int i = 1; i < N; i++)
                for (int j = 1; j < N; j++) {
                    int newW;
                    if (D[i][k][kk - 1] == INFINITY || D[k][j][kk - 1] == INFINITY) {
                        newW = INFINITY;
                    } else {
                        newW = D[i][k][kk - 1] + D[k][j][kk - 1];
                    }
                    if (D[i][j][kk] > newW) {
                        D[i][j][kk] = newW;
                        B[i][j] = k;
                    }
                }
        }
        for (int i = 1; i < N; i++)
            for (int j = 1; j < N; j++) {
                if (D[i][j][N - 1] < 0) {
                    System.out.println("negative cycle");
                    return -1;
                }
                if (i != j && res > B[i][j])
                    res = B[i][j];
            }
        return res;
    }

    static int[][][] readGraphFromFile(String filePath) {
        Charset charset = Charset.forName("UTF-8");
        Path path = Paths.get(filePath);
        int[][][] graph = null;
        int k = 0;
        try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
            String line = reader.readLine();
            String[] strLine = line.split("\\s+");
            int vertexNum = Integer.parseInt(strLine[0]);
            int edgesNum = Integer.parseInt(strLine[1]);
            graph = new int[vertexNum + 1][vertexNum + 1][2];
            int i, j;
            int w;

            while ((line = reader.readLine()) != null) {
                strLine = line.split("\\s+");
                i = Integer.parseInt(strLine[0]);
                j = Integer.parseInt(strLine[1]);
                w = Integer.parseInt(strLine[2]);
                graph[i][j][k] = w;
            }
        } catch (IOException x) {
            System.out.println("IOException:" + x.getMessage());
        }

        for (int i = 1; i < graph.length; i++)
            for (int j = 1; j < graph.length; j++) {
                if (i != j && graph[i][j][k] == 0) {
                    graph[i][j][k] = INFINITY;
                }
            }
        return graph;
    }

}

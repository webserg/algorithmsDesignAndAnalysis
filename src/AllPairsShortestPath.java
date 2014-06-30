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
    @Test
    public void testMST1() throws Exception {
        long[][] D = readGraphFromFile("resource/g0.txt");
        int res = run(D);
        Assert.assertEquals(5, res);
    }

    @Test
    public void testMST2() throws Exception {
        long[][] D = readGraphFromFile("resource/g1.txt");
        int res = run(D);
        Assert.assertEquals(-1, res);
    }

    @Test
    public void testMST3() throws Exception {
        long[][] D = readGraphFromFile("resource/g2.txt");
        int res = run(D);
        Assert.assertEquals(-1, res);
    }

    @Test
    public void testMST4() throws Exception {
        long[][] D = readGraphFromFile("resource/g3.txt");
        int res = run(D);
        Assert.assertEquals(-1, res);
    }

    int run(long[][] D) {
        int N = D.length;
        int res = N + 1;
        int[][] B = new int[N][N];
        for (int k = 1; k < N; k++)
            for (int i = 1; i < N; i++)
                for (int j = 1; j < N; j++) {
                    long newW;
                    if (D[i][k] == Long.MAX_VALUE || D[k][j] == Long.MAX_VALUE) {
                        newW = Long.MAX_VALUE;
                    } else {
                        newW = D[i][k] + D[k][j];
                    }
                    if (D[i][j] > newW) {
                        D[i][j] = newW;
                        B[i][j] = k;
                    }

                }
        for (int i = 1; i < N; i++)
            for (int j = 1; j < N; j++) {
                if (D[i][j] < 0) {
                    System.out.println("negative cycle");
                    return -1;
                }
                if (i != j && res > B[i][j])
                    res = B[i][j];
            }
        return res;
    }

    static long[][] readGraphFromFile(String filePath) {
        Charset charset = Charset.forName("UTF-8");
        Path path = Paths.get(filePath);
        long[][] graph = null;
        try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
            String line = reader.readLine();
            String[] strLine = line.split("\\s+");
            int vertexNum = Integer.parseInt(strLine[0]);
            int edgesNum = Integer.parseInt(strLine[1]);
            graph = new long[vertexNum + 1][vertexNum + 1];
            int i, j;
            long w;
            while ((line = reader.readLine()) != null) {
                strLine = line.split("\\s+");
                i = Integer.parseInt(strLine[0]);
                j = Integer.parseInt(strLine[1]);
                w = Integer.parseInt(strLine[2]);
                graph[i][j] = w;
            }
        } catch (IOException x) {
            System.out.println("IOException:" + x.getMessage());
        }
        long infin = Long.MAX_VALUE;
        for (int i = 1; i < graph.length; i++)
            for (int j = 1; j < graph.length; j++) {
                if (i != j && graph[i][j] == 0) {
                    graph[i][j] = infin;
                }
            }
        return graph;
    }

}

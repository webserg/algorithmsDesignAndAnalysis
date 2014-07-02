import junit.framework.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Created by webserg on 02.07.2014.
 * All pair shortest path using Johnson(BellMan-Ford for detecting negative circles then weighting then Dijkstra )
 * large graph
 */
public class APSP_B_F_Johnson {
    static final long INFINITY = Long.MAX_VALUE;
    static final long MIN_INFINITY = Long.MIN_VALUE;

    long runJohnson(Graph graph) {
        return 0;
    }

    long[] runBellmanFordSingleSourcePath(Graph graph, int source) {
        int N = graph.vertexes.length;
        long[] D = new long[N];
        for (int i = 0; i < N; i++) {
            D[i] = INFINITY;
        }
        D[source] = 0;
        for (int k = 1; k < N; k++) {
            for (Edge edge : graph.edges) {
                if (D[edge.u] != INFINITY && D[edge.u] + edge.w < D[edge.v]) {
                    D[edge.v] = D[edge.u] + edge.w;
                    if (k == N - 1) {
                        System.out.println("negative cycle");
                        D[N - 1] = MIN_INFINITY;
                        return D;
                    }
                }
            }
        }


        return D;
    }

    long[] weighting(Graph graph) {
        int N = graph.vertexes.length;
        long[] D = new long[N];
        for (int i = 0; i < N; i++) {
            D[i] = INFINITY;
        }
        int len = graph.edges.length;
        graph.edges = Arrays.copyOf(graph.edges, len + graph.vertexes.length -1);
        for (int i = 1; i < graph.vertexes.length; i++) {
            graph.edges[len++] = new Edge(0, i, 0);
        }

        return runBellmanFordSingleSourcePath(graph,0);
    }

    @Test
    public void testAPSP2() throws Exception {
        long res[] = runBellmanFordSingleSourcePath(readGraphFromFile("resource/g1.txt"),1);
        org.junit.Assert.assertEquals(MIN_INFINITY, res[res.length - 1]);
    }

    @Test
    public void testAPSP3() throws Exception {
        long res[] = runBellmanFordSingleSourcePath(readGraphFromFile("resource/g2.txt"),1);
        org.junit.Assert.assertEquals(MIN_INFINITY, res[res.length - 1]);
    }

    @Test
    public void testAPSPgTest3() throws Exception {
        long res[] = runBellmanFordSingleSourcePath(readGraphFromFile("resource/gTest3.txt"),1);
        org.junit.Assert.assertEquals(MIN_INFINITY, res[res.length - 1]);
    }

    @Test
    public void testBellmanFord() throws Exception {
        long res[] = runBellmanFordSingleSourcePath(readGraphFromFile("resource/bellmanFordTest.txt"),1);
        Assert.assertEquals(6, res[res.length - 1]);
        System.out.println("");
    }

    /*
    Vertex   Distance from Source
    0                0
    1                -1
    2                2
    3                -2
    4                1
    http://www.geeksforgeeks.org/dynamic-programming-set-23-bellman-ford-algorithm/
     */
    @Test
    public void testBellmanFord2() throws Exception {
        long res[] = runBellmanFordSingleSourcePath(readGraphFromFile("resource/bellmanFordTest2.txt"),1);
        Assert.assertEquals(1, res[res.length - 1]);
    }


    @Test
    public void testAPSPLarge() throws Exception {
        Graph graph = readGraphFromFile("resource/large.txt");
    }

    static class Vertex {
        private int v;
        int score;

        Vertex(int v) {
            this.v = v;
        }
    }

    static class Edge {
        private int u;
        private int v;
        private long w;

        Edge(int u, int v, long w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }
    }

    static class Graph {
        Vertex[] vertexes;
        Edge[] edges;

        Graph(Vertex[] vertexes, Edge[] edges) {
            this.vertexes = vertexes;
            this.edges = edges;
        }
    }

    static Graph readGraphFromFile(String filePath) {
        Charset charset = Charset.forName("UTF-8");
        Path path = Paths.get(filePath);
        Vertex[] vertexes = null;
        Edge[] edges = null;
        int N;
        try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
            String line = reader.readLine();
            String[] strLine = line.split("\\s+");
            N = Integer.parseInt(strLine[0]);
            int edgesNum = Integer.parseInt(strLine[1]);
            vertexes = new Vertex[N + 1];
            edges = new Edge[edgesNum];

            int i, j;//idx of vertexes
            int w;//weight
            int e = 0;
            while ((line = reader.readLine()) != null) {
                strLine = line.split("\\s+");
                i = Integer.parseInt(strLine[0]);
                j = Integer.parseInt(strLine[1]);
                w = Integer.parseInt(strLine[2]);
                edges[e++] = new Edge(i, j, w);
                if (vertexes[i] == null) {
                    vertexes[i] = new Vertex(i);
                }
            }
        } catch (IOException x) {
            System.out.println("IOException:" + x.getMessage());
        }

        return new Graph(vertexes, edges);
    }


}

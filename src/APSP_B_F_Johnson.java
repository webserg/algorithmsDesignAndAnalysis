import junit.framework.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by webserg on 02.07.2014.
 * All pair shortest path using Johnson(BellMan-Ford for detecting negative circles then reWeighting then Dijkstra )
 * large graph
 */
public class APSP_B_F_Johnson {
    static final long INFINITY = Long.MAX_VALUE;
    static final long MIN_INFINITY = Long.MIN_VALUE;

    class VertexComparator implements Comparator<Vertex> {
        @Override
        public int compare(Vertex v1, Vertex v2) {
            return v1.score < v2.score ? -1 : v1.score > v2.score ? 1 : 0;
        }
    }

    long runJohnson(Graph graph) {
        long res = INFINITY;

        long[] P = reWeighting(graph);
        graph.edges = Arrays.copyOf(graph.edges, graph.m);
        HashMap<Integer, List<Edge>> edgeMap = new HashMap<>();//adjacency list Vertex -> list of edges
        for (Edge edge : graph.edges) {
            edge.w = edge.w + P[edge.u] - P[edge.v]; // mutating to G'
            if (edgeMap.containsKey(edge.u)) {
                edgeMap.get(edge.u).add(edge);
            } else {
                List<Edge> list = new ArrayList<>();
                list.add(edge);
                edgeMap.put(edge.u, list);
            }
        }
        for (int i = 1; i <= graph.n; i++)
            for (int j = 1; j <= graph.n; j++) {

                if (i!=j) {
                    for (int v = 1; v < graph.vertexes.length-1; v++) {
                        graph.vertexes[v].score=0;
                    }
                    PriorityQueue<Vertex> priorityQueue = new PriorityQueue<>(graph.n, new VertexComparator());
                    Vertex cur = graph.vertexes[i];
                    Vertex dest = graph.vertexes[j];
                    List<Edge> adjacencyList = edgeMap.get(cur.v);
                    if (adjacencyList != null) {

                        for (Edge edge : adjacencyList) {
                            graph.vertexes[edge.v].score += edge.w;
                            priorityQueue.offer(graph.vertexes[edge.v]);
                        }
                        Vertex min=null;
                        for (int k = 1; k < graph.m; k++) {
//                        while (!cur.equals(dest)) {
                            min = priorityQueue.poll();
                            if (min == null) break;//no way
                            adjacencyList = edgeMap.get(min.v);
                            if (adjacencyList != null) for (Edge edge : adjacencyList) {
                                long newScore = edge.w + min.score;
                                addIdScoreLess(priorityQueue, graph.vertexes[edge.v], newScore);
                            }
                        }
                        long curW = min.score - P[i] + P[j];
                        System.out.println(curW);
                        if (curW < res) res = curW; // shortest shortes path
                    }
                }
            }
        return res;
    }

    private boolean addIdScoreLess(PriorityQueue<Vertex> priorityQueue, Vertex v, long newScore) {
        Vertex oldVertex = null;
        for (Vertex c : priorityQueue) {
            if (c.equals(v)) {
                oldVertex = c;
                break;
            }
        }
        if (oldVertex == null) {
            v.score = newScore;
            priorityQueue.offer(v);
        } else if (oldVertex.score > newScore) {
            oldVertex.score = newScore;
        }
        return true;
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


    Graph mutatingGtoGM(Graph graph) {
        long[] P = reWeighting(graph);
        graph.edges = Arrays.copyOf(graph.edges, graph.m);
        for (Edge edge : graph.edges) {
            edge.w = edge.w + P[edge.u] - P[edge.v];
        }
        return graph;
    }

    long[] reWeighting(Graph graph) {
        int N = graph.vertexes.length;
        long[] D = new long[N];
        for (int i = 0; i < N; i++) {
            D[i] = INFINITY;
        }
        int len = graph.edges.length;
        graph.edges = Arrays.copyOf(graph.edges, len + graph.vertexes.length - 1);
        for (int i = 1; i < graph.vertexes.length; i++) {
            graph.edges[len++] = new Edge(0, i, 0);
        }

        return runBellmanFordSingleSourcePath(graph, 0);
    }

    @Test
    public void testJohnson1() throws Exception {
        long res = runJohnson(readGraphFromFile("resource/gTest4.txt"));
        org.junit.Assert.assertEquals(-8, res);
    }


    @Test
    public void testJohnson2() throws Exception {
        long res = runJohnson(readGraphFromFile("resource/gTest5.txt"));
        org.junit.Assert.assertEquals(-3, res);
    }

    @Test
    public void testJohnson3() throws Exception {
        long res = runJohnson(readGraphFromFile("resource/g3.txt"));
        org.junit.Assert.assertEquals(-19, res);
    }

    @Test
    public void testReWeighting() throws Exception {
        long res[] = reWeighting(readGraphFromFile("resource/reweitingTest.txt"));
        Assert.assertEquals(Arrays.toString(new long[]{0, 0, -2, -3, -1, -6, 0}), Arrays.toString(res));
    }

    @Test
    public void testAPSP2() throws Exception {
        long res[] = runBellmanFordSingleSourcePath(readGraphFromFile("resource/g1.txt"), 1);
        org.junit.Assert.assertEquals(MIN_INFINITY, res[res.length - 1]);
    }

    @Test
    public void testAPSP3() throws Exception {
        long res[] = runBellmanFordSingleSourcePath(readGraphFromFile("resource/g2.txt"), 1);
        org.junit.Assert.assertEquals(MIN_INFINITY, res[res.length - 1]);
    }

    @Test
    public void testAPSPgTest3() throws Exception {
        long res[] = runBellmanFordSingleSourcePath(readGraphFromFile("resource/gTest3.txt"), 1);
        org.junit.Assert.assertEquals(MIN_INFINITY, res[res.length - 1]);
    }

    @Test
    public void testBellmanFord() throws Exception {
        long res[] = runBellmanFordSingleSourcePath(readGraphFromFile("resource/bellmanFordTest.txt"), 1);
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
        long res[] = runBellmanFordSingleSourcePath(readGraphFromFile("resource/bellmanFordTest2.txt"), 1);
        Assert.assertEquals(1, res[res.length - 1]);
    }


    @Test
    public void testAPSPLarge() throws Exception {
        Graph graph = readGraphFromFile("resource/large.txt");
    }

    static class Vertex {
        private int v;
        long score;

        Vertex(int v) {
            this.v = v;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Vertex vertex = (Vertex) o;

            if (v != vertex.v) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return v;
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
        final int m;
        final int n;
        Vertex[] vertexes;
        Edge[] edges;

        Graph(int m, int n, Vertex[] vertexes, Edge[] edges) {
            this.m = m;
            this.n = n;
            this.vertexes = vertexes;
            this.edges = edges;
        }
    }

    static Graph readGraphFromFile(String filePath) {
        Charset charset = Charset.forName("UTF-8");
        Path path = Paths.get(filePath);
        Vertex[] vertexes = null;
        Edge[] edges = null;
        int N = 0;
        int edgesNum = 0;
        try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
            String line = reader.readLine();
            String[] strLine = line.split("\\s+");
            N = Integer.parseInt(strLine[0]);
            edgesNum = Integer.parseInt(strLine[1]);
            vertexes = new Vertex[N + 1];
            edges = new Edge[edgesNum];
            for (int v = 1; v <= N; v++)
                vertexes[v] = new Vertex(v);
            int i, j;//idx of vertexes
            int w;//weight
            int e = 0;
            while ((line = reader.readLine()) != null) {
                strLine = line.split("\\s+");
                i = Integer.parseInt(strLine[0]);
                j = Integer.parseInt(strLine[1]);
                w = Integer.parseInt(strLine[2]);
                edges[e++] = new Edge(i, j, w);

            }
        } catch (IOException x) {
            System.out.println("IOException:" + x.getMessage());
        }

        return new Graph(edgesNum, N, vertexes, edges);
    }


}

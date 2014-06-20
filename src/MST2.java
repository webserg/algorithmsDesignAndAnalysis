import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by webserg on 17.06.2014.
 */
public class MST2 {

    @Test
    public void test() throws Exception {
        Graph graph = readGraphFromFile("resource/edges.txt");
        System.out.printf("%d", run(graph, 1));
    }

    @Test
    public void testMST1() throws Exception {
        Graph graph = readGraphFromFile("resource/edgesTest1.txt");
        long res = run(graph, 1);
        Assert.assertEquals(-13, res);
    }

//    @Test
//    public void testMST11() throws Exception {
//        Graph graph = readGraphFromFile("resource/edges_big.txt");
//        long res = run(graph, 1);
//        Assert.assertEquals(-469973073, res);
//    }

    @Test
    public void testMST2() throws Exception {
        Graph graph = readGraphFromFile("resource/edgesTest2.txt");
        long res = run(graph, 1);
        Assert.assertEquals(4, res);
    }

    @Test
    public void testMST3() throws Exception {
        Graph graph = readGraphFromFile("resource/edgesTest3.txt");
        long res = run(graph, 1);
        Assert.assertEquals(105, res);
    }

    @Test
    public void testMST4() throws Exception {
        Graph graph = readGraphFromFile("resource/edgesTest4.txt");
        long res = run(graph, 1);
        Assert.assertEquals(-1, res);
    }


    private Long run(Graph graph, int source) {
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(graph.vertexes.size(), new EdgeComparator());
        Vertex cur = graph.vertexes.get(new Vertex(source));
        for (Edge edge : graph.edges) {
            priorityQueue.offer(edge);
        }
        Set<Edge> tree = new HashSet<>();
        List<Vertex> x = new ArrayList<>(graph.vertexes.size());
        x.add(cur);
        Long cost = 0l;
        while (!x.containsAll(graph.vertexes.keySet())) {
            Edge min;
            boolean notfound = true;
            List<Edge> notGood = new ArrayList<>();
            do {
                min = priorityQueue.peek();
                if (min == null) return Long.MAX_VALUE;//no way
                if (x.contains(min.getTail()) && !x.contains(min.head)) {
                    tree.add(min);
                    cost += min.getLength();
                    cur = min.head;
                    x.add(cur);
                    notfound = false;
                } else {
                    notGood.add(min);
                }
            } while (notfound);
            priorityQueue.addAll(notGood);
        }
        return cost;
    }

    class EdgeComparator implements Comparator<Edge> {
        @Override
        public int compare(Edge e1, Edge e2) {
            return e1.length < e2.length ? -1 : e1.length > e2.length ? 1 : 0;
        }
    }

    static class Graph {
        Map<Vertex, Vertex> vertexes;
        Edge[] edges;

        Graph(Map<Vertex, Vertex> vertexes, Edge[] edges) {
            this.vertexes = vertexes;
            this.edges = edges;
        }
    }


    static class Vertex {
        private int v;
        boolean visited = false;

        public Vertex(int v) {
            this.v = v;
        }

        public int getV() {
            return v;
        }

        int score;

        List<Edge> adjacencyList = new ArrayList<>();

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

        @Override
        public String toString() {
            return "Vertex{" +
                    v +
                    '}';
        }
    }

    static class Edge {
        private Vertex tail;
        private Vertex head;
        private Long length;

        Edge(Vertex tail, Vertex head, long length) {
            this.tail = tail;
            this.head = head;
            this.length = length;
        }

        public Vertex getTail() {
            return tail;
        }

        public Vertex getHead() {
            return head;
        }

        public Long getLength() {
            return length;
        }
    }

    static Graph readGraphFromFile(String filePath) {
        Charset charset = Charset.forName("UTF-8");
        Path path = Paths.get(filePath);
        Map<Vertex, Vertex> vertexes = null;
        Edge[] edges = null;
        try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
            String line = reader.readLine();
            String[] strLine = line.split("\\s+");
            int vertexNum = Integer.parseInt(strLine[0]);
            int edgesNum = Integer.parseInt(strLine[1]);
            vertexes = new HashMap<>(vertexNum);
            edges = new Edge[edgesNum];
            int i = 0;
            int j = 0;
            while ((line = reader.readLine()) != null) {
                strLine = line.split("\\s+");
                Vertex tail = new Vertex(Integer.parseInt(strLine[0]));
                Vertex head = new Vertex(Integer.parseInt(strLine[1]));
                int cost = Integer.parseInt(strLine[2]);
                if (!vertexes.containsKey(head))
                    vertexes.put(head, head);
                head = vertexes.get(head);
//                Edge edge = new Edge(head, tail, cost);
                head.adjacencyList.add(new Edge(head, tail, cost));
                if (!vertexes.containsKey(tail))
                    vertexes.put(tail, tail);
                tail = vertexes.get(tail);
                Edge edge = new Edge(tail, head, cost);
                edges[j++] = edge;
                tail.adjacencyList.add(edge);
            }
            reader.close();
        } catch (IOException x) {
            System.out.println("IOException:" + x.getMessage());
        }
        return new Graph(vertexes, edges);
    }

}



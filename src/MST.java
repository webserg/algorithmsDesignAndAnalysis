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
public class MST {

    @Test
    public void testDijkstraShortestPath1() throws Exception {

        int[] resArray = new int[5];
        int[] destArray = new int[]{1, 2, 3, 4, 5};
        for (int i = 0; i < destArray.length; i++) {
            Graph graph = readGraphFromFile("resource/DijkstraTest1.txt");
            resArray[i] = run(graph, 1);
        }
        Assert.assertEquals(Arrays.toString(new int[]{0, 10, 50, 30, 60}), Arrays.toString(resArray));
    }


    private int run(Graph graph, int source) {
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(graph.vertexes.size(), new EdgeComparator());
        Vertex cur = graph.vertexes.get(new Vertex(source));
        for (Edge edge : cur.adjacencyList) {
            edge.head.score += edge.getLength();
            priorityQueue.offer(edge);
        }
        HashSet<Edge> tree = new HashSet<>();
        List<Vertex> x = new ArrayList(graph.vertexes.size());
        while (x.size() == graph.vertexes.size()) {
            Edge min;
            min = priorityQueue.poll();
            tree.add(min);
            cur = min.head;
            List<Edge> adj = cur.adjacencyList;
            for (Edge edge : adj) {
                Vertex v = edge.head;
                int newScore = edge.length + cur.score;
                if(!tree.contains(v))
                    addIdScoreLess(priorityQueue, edge, newScore);
//                priorityQueue.offer(v);
            }
            cur.adjacencyList = new ArrayList<>();
        }
        return cur.score;
    }

    private boolean addIdScoreLess(PriorityQueue<Edge> priorityQueue, Edge e, int newScore) {
        Edge oldEdge = null;
        for (Edge c : priorityQueue) {
            if (c.equals(e)) {
                oldEdge = c;
                break;
            }
        }
        if (oldEdge == null) {
            e.head.score = newScore;
            priorityQueue.offer(e);
        } else if (oldEdge.head.score > newScore) {
            oldEdge.head.score = newScore;
        }
        return true;
    }

    class EdgeComparator implements Comparator<Edge> {
        @Override
        public int compare(Edge e1, Edge e2) {
            return e1.head.score < e2.head.score ? -1 : e1.head.score > e2.head.score ? 1 : 0;
        }
    }

    static class Graph {
        Map<Vertex,Vertex> vertexes;
        Edge[] edges;

        Graph(Map<Vertex,Vertex> vertexes, Edge[] edges) {
            this.vertexes = vertexes;
            this.edges = edges;
        }
    }


    static class Vertex {
        private int v;
        int heapIdx = -1;

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
        private int length;

        Edge(Vertex tail, Vertex head, int length) {
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

        public int getLength() {
            return length;
        }
    }

    static Graph readGraphFromFile(String filePath) {
        Charset charset = Charset.forName("UTF-8");
        Path path = Paths.get(filePath);
        Map<Vertex,Vertex> vertexes = null;
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
                Vertex head = new Vertex(Integer.parseInt(strLine[2]));
                int cost = Integer.parseInt(strLine[3]);
                if(!vertexes.containsKey(tail))
                vertexes.put(tail,tail);
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



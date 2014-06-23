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
 * Created by webserg on 20.06.2014.
 * Clustering algo using Kruskal's minimum spanning tree algorithm
 */
public class Clustering {


    @Test
    public void test() throws Exception {
        Graph graph = readGraphFromFile("resource/clustering1.txt");
        Assert.assertEquals(106,doClustering(graph, 4));
    }

    @Test
    public void test1() throws Exception {
        Graph graph = readGraphFromFile("resource/clusteringTest1.txt");
        System.out.println(doClustering(graph, 2));
    }

    private static long doClustering(Graph graph, int k_cluster) {
        Collections.sort(graph.getEdges(), new EdgeComparator());
        UnionFind unionFind = new UnionFind(graph.getClusterMap());
        int i = 0;
        List<Edge> tree = new ArrayList<>();
        long res = 0;
        while (i < graph.getEdges().size()) {
            Edge edge = graph.getEdges().get(i++);
            if (!unionFind.isConnected(edge.getTail(), edge.getHead())) {
                if (unionFind.clusterMap.size() == k_cluster) {
                    return edge.length;
                }else{
                    unionFind.union(edge.getTail(), edge.getHead());
                    tree.add(edge);
                }
            }

        }

//        for (Cluster c : unionFind.getClusterMap().values()) {
//            System.out.println(c);
//        }


//        for (Edge e : tree)
//            System.out.println(e);
        return res;
    }

    private static class Graph {
        private List<Edge> edges;
        private int vertexesNum;
        private Vertex[] vertexes;
        private Map<Vertex, Cluster> clusterMap;

        Graph(List<Edge> edges, int vertexesNum, Map<Vertex, Cluster> clusterMap, Vertex[] vertexes) {
            this.edges = edges;
            this.vertexesNum = vertexesNum;
            this.clusterMap = clusterMap;
            this.vertexes = vertexes;
        }

        public List<Edge> getEdges() {
            return edges;
        }

        public Map<Vertex, Cluster> getClusterMap() {
            return clusterMap;
        }
    }

    static class Cluster {
        private Vertex leader;

        private List<Vertex> vertexes = new ArrayList<>();

        Cluster(Vertex v) {
            leader = v;
            vertexes.add(v);
        }

        public void addAllVertex(List<Vertex> vs) {
            for (Vertex v : vs) {
                v.clusterLeader = leader;
            }
            vertexes.addAll(vs);
        }

        public void union(Cluster c) {
            addAllVertex(c.vertexes);


        }

        public int getSize() {
            return vertexes.size();
        }

        @Override
        public String toString() {
            return "Cluster{" +
                    "leader=" + leader +
                    ", vertexes=" + vertexes.toString() +
                    '}';
        }
    }

    /**
     * union find data stracture
     */
    static class UnionFind {

        Map<Vertex, Cluster> clusterMap;

        UnionFind(Map<Vertex, Cluster> clusterMap) {
            this.clusterMap = clusterMap;
        }

        public void union(Cluster c1, Cluster c2) {
            if (c2.getSize() <= c1.getSize()) {
                c1.union(c2);
                clusterMap.remove(c2.leader);
            } else {
                c2.union(c1);
                clusterMap.remove(c1.leader);
            }
        }

        public void union(Vertex v1, Vertex v2) {
            Cluster c1 = find(v1);
            Cluster c2 = find(v2);
            union(c1, c2);
        }

        public Cluster find(Vertex v) {
            return clusterMap.get(v.clusterLeader);
        }

        boolean isConnected(Vertex v1, Vertex v2) {
            return v1.clusterLeader == v2.clusterLeader;
        }

        int getSize() {
            return clusterMap.size();
        }

        public Map<Vertex, Cluster> getClusterMap() {
            return clusterMap;
        }

        public void setClusterMap(Map<Vertex, Cluster> clusterMap) {
            this.clusterMap = clusterMap;
        }
    }

    static class EdgeComparator implements Comparator<Edge> {
        @Override
        public int compare(Edge e1, Edge e2) {
            return e1.length < e2.length ? -1 : e1.length > e2.length ? 1 : 0;
        }
    }

    static class Vertex {
        private int v;
        Vertex clusterLeader;

        public Vertex(int v) {
            this.v = v;
            this.clusterLeader = this;
        }

        public int getV() {
            return v;
        }

        List<Edge> adjacencyList = new ArrayList<>();

        public Vertex getClusterLeader() {
            return clusterLeader;
        }

        public void setClusterLeader(Vertex clusterLeader) {
            this.clusterLeader = clusterLeader;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Vertex vertex = (Vertex) o;
            return v == vertex.v;
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
        private long length;

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

        public long getLength() {
            return length;
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "tail=" + tail +
                    ", head=" + head +
                    ", length=" + length +
                    '}';
        }


    }

    static Graph readGraphFromFile(String filePath) {
        Charset charset = Charset.forName("UTF-8");
        Path path = Paths.get(filePath);
        List<Edge> edges = new ArrayList<>();
        Map<Vertex, Cluster> clusterMap = new HashMap<>();
        Vertex[] vertexes;
        int vertexNum = 0;
        Graph graph = null;
        try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
            String line = reader.readLine();
            String[] strLine = line.split("\\s+");
            vertexNum = Integer.parseInt(strLine[0]);
            vertexes = new Vertex[vertexNum + 1];
            while ((line = reader.readLine()) != null) {
                strLine = line.split("\\s+");
                Edge edge;
                int tailIdx = Integer.parseInt(strLine[0]);
                int headIdx = Integer.parseInt(strLine[1]);

                Vertex tail, head;
                if (vertexes[tailIdx] == null) {
                    tail = new Vertex(tailIdx);
                    vertexes[tailIdx] = tail;
                } else {
                    tail = vertexes[tailIdx];
                }
                if (vertexes[headIdx] == null) {
                    head = new Vertex(headIdx);
                    vertexes[headIdx] = head;
                } else {
                    head = vertexes[headIdx];
                }


                if (!clusterMap.containsKey(tail)) {
                    clusterMap.put(tail, new Cluster(tail));
                }
                if (!clusterMap.containsKey(head)) {
                    clusterMap.put(head, new Cluster(head));
                }

                int lenght = Integer.parseInt(strLine[2]);
                edge = new Edge(tail, head, lenght);
                edges.add(edge);
                if (!clusterMap.containsKey(tail))
                    clusterMap.put(tail, new Cluster(tail));
                if (!clusterMap.containsKey(head))
                    clusterMap.put(head, new Cluster(head));
            }
            graph = new Graph(edges, vertexNum, clusterMap, vertexes);
        } catch (IOException x) {
            System.out.println("IOException:" + x.getMessage());
        }
        return graph;
    }

}

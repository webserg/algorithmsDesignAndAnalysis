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
public class Clustering2 {

    private static Node[] nodes;
    private static Map<Integer, List<Node>> groups;

    @Test
    public void test() throws Exception {
        doClustering("resource/clustering2.txt");
        Map<Integer, List<Edge>> edges = new HashMap<>();
        UnionFind unionFind = new UnionFind();
        for (int i = 0; i < 3; i++) {
            edges.put(i, new ArrayList<Edge>());
        }
        for (int i = 0; i < nodes.length; i++) {
            Node node = nodes[i];
            unionFind.addCluster(node);
            int cardinality = node.bitSet.cardinality();
            int lowCardinality = cardinality - 2;
            int hightcardinality = cardinality + 2;
            List<Node> comparingNodes = new ArrayList<>();
            for (int k = lowCardinality; k <= hightcardinality; k++) {
                comparingNodes.addAll(groups.get(k));
            }
            for (Node n : comparingNodes) {
                if (n.n != node.n) {
                    BitSet bitSetTmp = (BitSet) node.bitSet.clone();
                    bitSetTmp.xor(n.bitSet);
                    Integer distance = bitSetTmp.cardinality();
                    if (distance <= 2) {
                        Edge edge = new Edge(node, n);
                        edges.get(distance).add(edge);
                    }
                }
            }
        }
        long res ;
        for(int i=0;i<=2;i++)
        for (Edge edge : edges.get(i)) {
            if (!unionFind.isConnected(edge.tail, edge.head)) {
                unionFind.union(edge.tail, edge.head);
            }
        }
        res = unionFind.clusterMap.size();
        System.out.println(res);
    }


    private static int doClustering(String filePath) {

        Charset charset = Charset.forName("UTF-8");
        Path path = Paths.get(filePath);
        int nodenum, bitsnum;
        try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
            String line = reader.readLine();
            String[] strLine = line.split("\\s+");
            nodenum = Integer.parseInt(strLine[0]);
            bitsnum = Integer.parseInt(strLine[1]);
            nodes = new Node[nodenum];
            groups = new HashMap<>();
            for (int i = 0; i < bitsnum; i++) {
                groups.put(i, new ArrayList<Node>());
            }
            int n = 0;
            while ((line = reader.readLine()) != null) {
                strLine = line.split("\\s+");
                BitSet bitSet = new BitSet(bitsnum);
                for (int i = 0; i < strLine.length; i++) {
                    int bit = Integer.parseInt(strLine[i]);
                    if (bit > 0)
                        bitSet.set(i);
                }
                Node node = new Node(n, bitSet);
                nodes[n++] = node;
                groups.get(bitSet.cardinality()).add(node);
            }
        } catch (IOException x) {
            System.out.println("IOException:" + x.getMessage());
        }
        return 0;
    }

    static class Edge {
        private Node tail;
        private Node head;

        Edge(Node tail, Node head) {
            this.tail = tail;
            this.head = head;
        }
    }

    static class Node {
        int n;
        Node leader;
        BitSet bitSet;

        Node(int n, BitSet bitSet) {
            this.n = n;
            this.leader = this;
            this.bitSet = bitSet;
        }
    }

    static class Cluster {
        private Node leader;
        private List<Node> nodes = new ArrayList<>();

        Cluster(Node n) {
            leader = n;
            nodes.add(n);
        }

        public void union(Cluster c) {
            for (Node n : c.nodes) {
                n.leader = leader;
                this.nodes.add(n);
            }
        }

        public int getSize() {
            return nodes.size();
        }
    }

    static class UnionFind {

        Map<Integer, Cluster> clusterMap = new HashMap<>();

        public void union(Cluster c1, Cluster c2) {
            if (c2.getSize() <= c1.getSize()) {
                c1.union(c2);
                clusterMap.remove(c2.leader.n);
            } else {
                c2.union(c1);
                clusterMap.remove(c1.leader.n);
            }
        }

        public void union(Node v1, Node v2) {
            Cluster c1 = find(v1);
            Cluster c2 = find(v2);
            union(c1, c2);
        }

        public Cluster find(Node v) {
            return clusterMap.get(v.leader.n);
        }

        boolean isConnected(Node n1, Node n2) {
            return n1.leader == n2.leader;
        }

        void addCluster(Node n) {
            if (!clusterMap.containsKey(n.n)) {
                clusterMap.put(n.n, new Cluster(n));
            }
        }
    }


}

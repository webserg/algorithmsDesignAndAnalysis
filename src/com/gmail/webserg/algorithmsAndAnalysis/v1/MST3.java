package com.gmail.webserg.algorithmsAndAnalysis.v1;

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
 * Created by webserg on 15.06.2014.
 * In this programming problem you'll code up Prim's minimum spanning tree algorithm.
 * This file describes an undirected graph with integer edge costs. It has the format

 [number_of_nodes] [number_of_edges]

 [one_node_of_edge_1] [other_node_of_edge_1] [edge_1_cost]

 [one_node_of_edge_2] [other_node_of_edge_2] [edge_2_cost]

 ...

 For example, the third line of the file is "2 3 -8874", indicating that there is an edge connecting vertex #2 and vertex
 #3 that has cost -8874.

 You should NOT assume that edge costs are positive, nor should you assume that they are distinct.

 Your task is to run Prim's minimum spanning tree algorithm on this graph. You should report the overall cost of
 a minimum spanning tree --- an integer, which may or may not be negative --- in the box below.

 IMPLEMENTATION NOTES: This graph is small enough that the straightforward O(mn) time implementation of Prim's
 algorithm should work fine.
 OPTIONAL: For those of you seeking an additional challenge, try
 implementing a heap-based version. The simpler approach, which should already give you a healthy speed-up,
 is to maintain relevant edges in a heap (with keys = edge costs). The superior approach stores the unprocessed
 vertices in the heap, as described in lecture. Note this requires a heap that supports deletions,
 and you'll probably need to maintain some kind of mapping between vertices and their positions in the heap.
 */
public class MST3 {

    @Test
    public void testMST1() throws Exception {
        Vertex[] graph = readGraphFromFile("resource/edgesTest1.txt");
        long res = run(graph, 1);
        Assert.assertEquals(-13, res);
    }
    @Test
    public void testMST2() throws Exception {
        Vertex[] graph = readGraphFromFile("resource/edgesTest2.txt");
        long res = run(graph, 1);
        Assert.assertEquals(4, res);
    }

    @Test
    public void testMST3() throws Exception {
        Vertex[] graph = readGraphFromFile("resource/edgesTest3.txt");
        long res = run(graph, 1);
        Assert.assertEquals(105, res);
    }

    @Test
    public void testMST4() throws Exception {
        Vertex[] graph= readGraphFromFile("resource/edgesTest4.txt");
        long res = run(graph, 1);
        Assert.assertEquals(-1, res);
    }

        @Test
    public void testMST11() throws Exception {
            Vertex[] graph=  readGraphFromFile("resource/edges_big.txt");
        long res = run(graph, 1);
        Assert.assertEquals(-469973073, res);
    }

    @Test
    public void test() throws Exception {
        Vertex[] graph=  readGraphFromFile("resource/edges.txt");
        System.out.printf("%d", run(graph, 1));
    }


    private long run(Vertex[] graph, int source) {
        PriorityQueue<Vertex> priorityQueue = new PriorityQueue<>(graph.length, new VertexComparator());
        Vertex cur = graph[source];
        Vertex[] x = new Vertex[graph.length];
        x[cur.getV()] = cur;
        int vertexcount =1;
        for (Edge edge : cur.adjacencyList) {
            Vertex v = edge.head;
            countScore(v,x);
            priorityQueue.offer(v);
        }
        long sum=0;
        while (graph.length-1 != vertexcount) {
            Vertex min = priorityQueue.poll();
            sum+=min.score;
            x[min.getV()] = min;
            vertexcount++;
            for (Edge edge : min.adjacencyList) {
                Vertex v = edge.head;
                if(priorityQueue.contains(v)){
                    priorityQueue.remove(v);

                }
                if(x[v.getV()] == null) {
                    countScore(v, x);
                    priorityQueue.offer(v);
                }
            }
        }
        return sum;
    }

    private void countScore(Vertex v,Vertex[] x) {
        PriorityQueue<Long> priorityQueue = new PriorityQueue<>();
        for (Edge edge : v.adjacencyList) {
            if (x[edge.head.getV()] != null) {
                priorityQueue.offer(edge.length);
            }
        }
        v.score = priorityQueue.poll();
    }

    private boolean addIdScoreLess(PriorityQueue<Vertex> priorityQueue, Vertex v, int newScore) {
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

    class VertexComparator implements Comparator<Vertex> {
        @Override
        public int compare(Vertex v1, Vertex v2) {
            return v1.score < v2.score ? -1 : v1.score > v2.score ? 1 : 0;
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

        long score;

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
    }

    static Vertex[] readGraphFromFile(String filePath) {
        Charset charset = Charset.forName("UTF-8");
        Path path = Paths.get(filePath);
        Vertex v;
        Vertex[] graph = null;
        try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
            String line = reader.readLine();
            String[] strLine = line.split("\\s+");
            int vertexNum = Integer.parseInt(strLine[0]);
            int edgesNum = Integer.parseInt(strLine[1]);
            graph = new Vertex[ vertexNum +1 ];
            while ((line = reader.readLine()) != null) {
                strLine = line.split("\\s+");
                int tailIdx = Integer.parseInt(strLine[0]);
                int headIdx = Integer.parseInt(strLine[1]);
                long cost = Long.parseLong(strLine[2]);
                Vertex head;
                Vertex tail;
                if (graph[tailIdx] == null) {
                    graph[tailIdx] = tail = new Vertex(tailIdx);
                } else {
                    tail = graph[tailIdx];
                }

                if (graph[headIdx] == null) {
                    graph[headIdx] = head = new Vertex(headIdx);
                } else {
                    head = graph[headIdx];
                }
                tail.adjacencyList.add(new Edge(tail, head, cost));
                head.adjacencyList.add(new Edge(head, tail, cost));
            }
            reader.close();
        } catch (IOException x) {
            System.out.println("IOException:" + x.getMessage());
        }
        return graph;
    }

}

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
 */
public class DijkstraShortPathUsingPriorityQueue {

    @Test
    public void testDijkstraShortestPath1() throws Exception {

        int[] resArray = new int[5];
        int[] destArray = new int[]{1, 2, 3, 4, 5};
        for (int i = 0; i < destArray.length; i++) {
            Vertex[] graph = readDijkstraGraphFromFile("resource/DijkstraTest1.txt", 5);
            resArray[i] = run(graph, 1, destArray[i]);
        }
        Assert.assertEquals(Arrays.toString(new int[]{0, 10, 50, 30, 60}), Arrays.toString(resArray));
    }

    @Test
    public void testDijkstraShortestPath2() throws Exception {
        int[] resArray = new int[6];
        int[] destArray = new int[]{1, 2, 3, 4, 5,6};
        for (int i = 0; i < destArray.length; i++) {
            Vertex[] graph = readDijkstraGraphFromFile("resource/DijkstraTest2.txt", 6);
            resArray[i] = run(graph, 1, destArray[i]);
        }
        Assert.assertEquals(Arrays.toString(new int[]{0, 45, 10, 25, 45, 1000000}), Arrays.toString(resArray));
    }

    private int run(Vertex[] graph, int source, int destination) {
        PriorityQueue<Vertex> priorityQueue = new PriorityQueue<>(graph.length, new VertexComparator());
        Vertex cur = graph[source];
        Vertex dest = graph[destination];
        for (Edge edge : cur.adjacencyList) {
            Vertex v = edge.head;
            v.score += edge.getLength();
            priorityQueue.offer(v);
        }
        while (!cur.equals(dest)) {
            Vertex min;
            min = priorityQueue.poll();
            if(min==null) return 1000000;//no way
            cur = min;
            List<Edge> adj = cur.adjacencyList;
            for (Edge edge : adj) {
                Vertex v = edge.head;
                int newScore = edge.length + cur.score;
                addIdScoreLess(priorityQueue,v,newScore);
//                priorityQueue.offer(v);
            }
            cur.adjacencyList = new ArrayList<>();
        }
        return cur.score;
    }

    private boolean addIdScoreLess(PriorityQueue<Vertex> priorityQueue, Vertex v,int newScore){
        Vertex oldVertex = null;
        for(Vertex c : priorityQueue){
            if(c.equals(v)) {
                oldVertex=c;
                break;
            }
        }
        if(oldVertex == null){
            v.score = newScore;
            priorityQueue.offer(v);
        }else if(oldVertex.score > newScore) {
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

    static Vertex[] readDijkstraGraphFromFile(String filePath, int arraySize) {
        Charset charset = Charset.forName("UTF-8");
        Path path = Paths.get(filePath);
        Vertex v;
        Vertex[] graph = new Vertex[arraySize + 1];
        try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] strLine = line.split("\\s+");
                int vertexNum = Integer.parseInt(strLine[0]);
                if (graph[vertexNum] == null) {
                    graph[vertexNum] = v = new Vertex(vertexNum);
                } else {
                    v = graph[vertexNum];
                }
                for (int i = 1; i < strLine.length; i++) {
                    String[] vertexNumLen = strLine[i].split(",");
                    int vv = Integer.parseInt(vertexNumLen[0]);
                    Vertex head;
                    if (graph[vv] == null) {
                        graph[vv] = head = new Vertex(vv);
                    } else {
                        head = graph[vv];
                    }
                    int len = Integer.parseInt(vertexNumLen[1]);
                    v.adjacencyList.add(new Edge(v, head, len));
                }
            }
            reader.close();
        } catch (IOException x) {
            System.out.println("IOException:" + x.getMessage());
        }
        return graph;
    }

}

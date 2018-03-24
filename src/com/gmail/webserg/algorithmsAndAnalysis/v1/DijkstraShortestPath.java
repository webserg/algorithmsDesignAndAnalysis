package com.gmail.webserg.algorithmsAndAnalysis.v1;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * User: webserg
 * Date: 07.03.13
 */
public class DijkstraShortestPath {
    private static Logger log = Logger.getLogger(KargerMinCut.class.getName());

    {
        log.setLevel(Level.SEVERE);
    }

    @Test
    public void testDijkstraShortestPath1() throws Exception {

        int[] resArray = new int[5];
        int[] destArray = new int[]{1, 2, 3, 4, 5};
        for (int i = 0; i < destArray.length; i++) {
            DijkstraShortestPath.MinHeap minHeap = FilesHelper.readDijkstraGraphFromFile("resource/DijkstraTest1.txt", 5);
            resArray[i] = run(minHeap, 1, destArray[i]);
        }
        Assert.assertEquals(Arrays.toString(new int[]{0, 10, 50, 30, 60}), Arrays.toString(resArray));
    }

    @Test
    public void testDijkstraShortestPath2() throws Exception {
        int[] resArray = new int[6];
        int[] destArray = new int[]{1, 2, 3, 4, 5,6};
        for (int i = 0; i < destArray.length; i++) {
            DijkstraShortestPath.MinHeap minHeap = FilesHelper.readDijkstraGraphFromFile("resource/DijkstraTest2.txt", 6);
            resArray[i] = run(minHeap, 1, destArray[i]);
        }
        Assert.assertEquals(Arrays.toString(new int[]{0, 45, 10, 25, 45, 1000000}), Arrays.toString(resArray));
    }

    @Test
    public void testDijkstraShortestPath3() throws Exception {
        int[] resArray = new int[6];
        int[] destArray = new int[]{1, 2, 3, 4, 5,6};
        for (int i = 0; i < destArray.length; i++) {
            DijkstraShortestPath.MinHeap minHeap = FilesHelper.readDijkstraGraphFromFile("resource/DijkstraTest2.txt", 6);
            resArray[i] = run(minHeap, 5, destArray[i]);
        }
        Assert.assertEquals(Arrays.toString(new int[]{1000000, 50, 1000000, 30, 0, 1000000}), Arrays.toString(resArray));
    }



    @Test
    public void testDijkstraShortestPath4() throws Exception {
        int[] resArray = new int[6];
        int[] destArray = new int[]{1, 2, 3, 4, 5,6};
        for (int i = 0; i < destArray.length; i++) {
            DijkstraShortestPath.MinHeap minHeap = FilesHelper.readDijkstraGraphFromFile("resource/DijkstraTest2.txt", 6);
            resArray[i] = run(minHeap, 6, destArray[i]);
        }
        Assert.assertEquals(Arrays.toString(new int[]{1000000, 23, 1000000, 3, 33, 0}), Arrays.toString(resArray));
    }

    public static void main(String[] args) throws Exception {
        int[] resArray = new int[10];
        int[] destArray = new int[]{7,37,59,82,99,115,133,165,188,197};
        DijkstraShortestPath dijkstraShortestPath = new DijkstraShortestPath();
        for (int i = 0; i < destArray.length; i++) {
            DijkstraShortestPath.MinHeap minHeap = FilesHelper.readDijkstraGraphFromFile("resource/dijkstraData.txt", 200);
            resArray[i] = dijkstraShortestPath.run(minHeap, 1, destArray[i]);
        }
        System.out.println(Arrays.toString(resArray));
        Assert.assertEquals(Arrays.toString(new int[]{2599, 2610, 2947, 2052, 2367, 2399, 2029, 2442, 2505, 3068}), Arrays.toString(resArray));
    }

    private int run(DijkstraShortestPath.MinHeap minHeap, int source, int destination) {
        Vertex cur = minHeap.graph[source];
        Vertex dest = minHeap.graph[destination];
//        int curScore = 0;
        for (Edge edge : cur.adjacencyList) {
            Vertex v = edge.head;
//            v.score = edge.length;
            minHeap.minHeapInsert(v,edge.length);
        }
        while (!cur.equals(dest)) {
            Vertex min;
            try{
                min = minHeap.extractMin();
            }catch (NoWayException e){
                return 1000000;
            }
//            min.score += cur.score;
            cur = min;
            List<Edge> adj = cur.adjacencyList;
            for (Edge edge : adj) {
                Vertex v = edge.head;
//                v.score = edge.length + cur.score;
                    minHeap.minHeapInsert(v,edge.length + cur.score);
            }
            cur.adjacencyList = new ArrayList<>();
        }
        return cur.score;
    }

    static class NoWayException extends RuntimeException{
        NoWayException(String message) {
            super(message);
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

    static class MinHeap {
        private int N;
        private Vertex[] heap;
        Vertex[] graph;
        int size = -1;

        MinHeap(int n) {
            N = n;
            graph = new Vertex[N + 1];
            heap = new Vertex[N];
        }

        MinHeap deepCopy() {
            MinHeap newHeap = new MinHeap(N);
            newHeap.graph = Arrays.copyOf(graph, graph.length);
            return newHeap;
        }

        int parent(int i) {
            return i >>> 1; // i/2
        }

        int left(int i) {
            return i << 1;
        }

        int right(int i) {
            return (i << 1) + 1;
        }

        /*
        * see Cormen s 154 p 6.2
        * */
        void minHeapify(int i) {
            int l = left(i);
            int r = right(i);
            int smallest;
            if (l <= size && heap[l].score < heap[i].score) {
                smallest = l; //violation of min
            } else {
                smallest = i;
            }

            if (r <= size && heap[r].score < heap[smallest].score) {
                smallest = r; //violation of min by left leaf
            }

            if (smallest != i) {

                //exchange i and smallest
                Vertex temp = heap[i];
                heap[i] = heap[smallest];
                heap[i].heapIdx = i;
                heap[smallest] = temp;
                heap[smallest].heapIdx = smallest;
                //check smallest
                minHeapify(smallest);
            }
        }

        void buildMinHeap() {
            for (int i = (size >>> 1) - 1; i >= 0; i--)
                minHeapify(i);
        }

        Vertex extractMin() {
            if (size < 0) throw new NoWayException("heap underflow");
            Vertex min = heap[0];
            heap[0] = heap[size];
            heap[0].heapIdx = 0;
            heap[size] = null;
            size--;
            minHeapify(0);
            min.heapIdx = -1;
            return min;
        }

        void decreaseScore(int i, int score) {
            if (score > heap[i].score) throw new RuntimeException("new key is bigger than current key");
            heap[i].score = score;
            while (i >= 0 && heap[parent(i)].score > heap[i].score) {
                //exchange i and smallest
                int p = parent(i);
                Vertex temp = heap[i];
                heap[i] = heap[p];
                heap[i].heapIdx = i;
                heap[p] = temp;
                heap[p].heapIdx = p;
                i = p;
            }
        }

        void minHeapInsert(Vertex v,int score) {
            if (v.heapIdx > -1) {
                if(heap[v.heapIdx].score > score)
                    decreaseScore(v.heapIdx, score);
            } else {
                size += 1;
                if (heap.length <= size) {
                    int newCapacity = heap.length << 1;
                    heap = Arrays.copyOf(heap, newCapacity);
                }
                heap[size] = v;
                v.heapIdx = size;
                v.score = 1000000;
                decreaseScore(size, score);
            }
        }
    }

}


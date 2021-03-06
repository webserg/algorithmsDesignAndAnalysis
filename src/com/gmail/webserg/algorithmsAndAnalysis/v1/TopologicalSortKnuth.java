package com.gmail.webserg.algorithmsAndAnalysis.v1;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by webse on 11/3/2016.
 * <p>
 * L ← Empty list that will contain the sorted elements
 * S ← Set of all nodes with no incoming edges
 * while S is non-empty do
 * remove a node n from S
 * add n to tail of L
 * for each node m with an edge e from n to m do
 * remove edge e from the graph
 * if m has no other incoming edges then
 * insert m into S
 * if graph has edges then
 * return error (graph has at least one cycle)
 * else
 * return L (a topologically sorted order)
 */
public class TopologicalSortKnuth {

    public static void main(String[] args) throws java.lang.Exception {
        Graph graph = null;
        if (true) {
            Scanner in = new Scanner(System.in);
            int N = in.nextInt();
            int m = in.nextInt();
            if (!check1(N, m)) {
                System.out.println("Sandro fails.");
                return;
            }
            graph = new Graph(N, m);
            int k = m;
            while (k > 0) {
                int x = in.nextInt();
                int y = in.nextInt();
                graph.addEdge(x, y);
                k--;
            }
        } else {
            List<String> lines;
            lines = Files.readAllLines(Paths.get("./resource/topoSearchKnuthTest.txt"));
            String[] line = lines.get(1).split(" ");
            int N = Integer.parseInt(line[0]);
            int m = Integer.parseInt(line[1]);
            graph = new Graph(N, m);
            for (int i = 0; i < m; i++) {
                line = lines.get(i + 2).split(" ");
                graph.addEdge(Integer.parseInt(line[0]), Integer.parseInt(line[1]));
            }
        }
        List<Integer> topoList = topoSort(graph);
        if (graph.M != 0) {
            System.out.println("Sandro fails.");
            System.exit(0);
        } else {
            PrintWriter prw = new PrintWriter(System.out, false);
            for (int i = 0; i < topoList.size(); i++) {
                prw.print(topoList.get(i) + " ");
            }
            prw.flush();
        }
    }

    private static List<Integer> topoSort(Graph graph) {
        List<Integer> topoList = new ArrayList<>(graph.getN());
        Queue<Integer> s = new ArrayDeque<>();
        for (int i = 1; i < graph.incomingEdgeCounter.length; i++) {
            if(graph.incomingEdgeCounter[i] == 0){
                s.offer(i);
            }
        }
        while (!s.isEmpty()) {
            int n = s.poll();
            topoList.add(n);
            TreeSet<Integer> localZeroIncomeList = new TreeSet<>();
            List<Integer> adjList = graph.adjacencyList.get(n);
            if (adjList != null) {
                for (int i = 0; i < adjList.size(); i++) {
                    Integer v = adjList.get(i);
                    graph.incomingEdgeCounter[v]--;
                    graph.M--;
                    if (graph.incomingEdgeCounter[v] == 0) {
                        localZeroIncomeList.add(v);
                    }
                }
                localZeroIncomeList.forEach(s::offer);
            }
        }
        return topoList;
    }

    private static boolean check1(int n, int m) {
        return n > 0 && n < 10001 && m > 0 && m < 1000000;
    }

    private static boolean check2(int x) {
        return x > 0 && x < 10001;
    }


    static private class Graph {
        final int N;
        int M;
        Map<Integer, List<Integer>> adjacencyList;
        int[] incomingEdgeCounter;

        public Graph(int n, int m) {
            N = n;
            M = m;
            adjacencyList = new HashMap<>(n);
            incomingEdgeCounter = new int[n+1];
        }

        public int getN() {
            return N;
        }

        void addEdge(int x, int y) {
            incomingEdgeCounter[y]++;
            List<Integer> adjList = adjacencyList.get(x);
            if (adjList == null) {
                adjList = new ArrayList<>();
                adjList.add(y);
                adjacencyList.put(x, adjList);
            } else {
                adjList.add(y);
            }
        }
    }


}


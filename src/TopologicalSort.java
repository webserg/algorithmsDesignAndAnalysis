import java.util.*;

/**
 * Created by webse on 10/22/2016.
 * Input:
 * 8 9
 * 1 4
 * 1 2
 * 4 2
 * 4 3
 * 3 2
 * 5 2
 * 3 5
 * 8 2
 * 8 6
 * Output:
 * 1 4 3 5 7 8 2 6
 */
public class TopologicalSort {
    public static void main(String[] args) throws java.lang.Exception {
        test(args);
    }

    public static void test(String[] args) throws java.lang.Exception {
        java.io.BufferedReader r = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
        String s = r.readLine();
        String[] line = s.split(" ");
        int N = Integer.parseInt(line[0]);
        int m = Integer.parseInt(line[1]);
        int lines = 0;
        Graph graph = new Graph(N);
        while (lines < m) {
            lines++;
            line = r.readLine().split(" ");
            addPath(graph, Integer.parseInt(line[0]), Integer.parseInt(line[1]));
        }
//        for (Vertex v : graph.adjacencyList.keySet()) {
//            System.out.print(v);
//            System.out.println(graph.adjacencyList.get(v));
//        }
        toposearch(graph);
        StringBuilder result = new StringBuilder();
        int counter = 0;
        while (!graph.topoStack.isEmpty()) {
            result.append(graph.topoStack.take().name).append(" ");
            counter++;
        }
        if (counter != graph.allVisited()) {
            System.out.println("Sandro fails.");
        } else {
            System.out.println(result.toString());
        }
    }


    private static void toposearch(Graph graph) {
        Set<Vertex> vertexSet = graph.adjacencyList.keySet();
        for (Vertex v : vertexSet) {
            if (!graph.isVertexVisited(v)) {
                dfs(graph, v);
            }
        }
    }


    private static void dfs(Graph graph, Vertex s) {
        graph.vertexVisit[s.name] = 1;
        List<Vertex> secondVertexofTheEdge = graph.adjacencyList.get(s);
        if (secondVertexofTheEdge != null)
            for (Vertex v : secondVertexofTheEdge) {
                if (!graph.isVertexVisited(v)) {
                    dfs(graph, v);
                }
            }
        graph.topoStack.put(s);
    }

    private static void addPath(Graph graph, int v1, int v2) {
        if (!graph.adjacencyList.containsKey(new Vertex(v1))) {
            graph.adjacencyList.put(new Vertex(v1), new ArrayList<>());
        }
        graph.adjacencyList.get(new Vertex(v1)).add(new Vertex(v2));
    }


    static private class Graph {
        private int N;
        Map<Vertex, List<Vertex>> adjacencyList;
        int[] vertexVisit;
        Stack<Vertex> topoStack = new Stack<Vertex>(new ArrayDeque<Vertex>(N));

        public Graph(int n) {
            N = n;
            adjacencyList = new HashMap<>();
            vertexVisit = new int[10000+1];
        }

        public int getN() {
            return N;
        }

        boolean isVertexVisited(Vertex v) {
            return vertexVisit[v.name] == 1;
        }

        int allVisited() {
            int s = 0;
            for (int i = 0; i < vertexVisit.length; i++) {
                if (vertexVisit[i] == 1) {
                    s++;
                }
            }
            return s;
        }
    }

    static private class Vertex {
        private final int name;

        Vertex(int name) {
            this.name = name;
        }

        public int getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Vertex vertex = (Vertex) o;

            return name == vertex.getName();
        }


        @Override
        public int hashCode() {
            return name;
        }

        @Override
        public String toString() {
            return "Vertex{" + name + '}';
        }
    }

    private static class Stack<E> {
        Deque<E> deque;

        Stack(Deque<E> deque) {
            this.deque = deque;
        }

        E take() {
            return deque.poll();
        }

        public boolean put(E e) {
            return deque.offerFirst(e);
        }

        boolean isEmpty() {
            return deque.isEmpty();
        }
    }
}

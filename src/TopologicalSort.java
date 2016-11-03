import java.nio.file.Files;
import java.nio.file.Paths;
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
        Graph graph = null;
        java.io.BufferedReader r = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
        String s = r.readLine();
        if (!s.isEmpty()) {
//        if (false) {
            String[] line = s.split(" ");
            int N = Integer.parseInt(line[0]);
            int m = Integer.parseInt(line[1]);
            if (!check1(N, m)) {
                System.out.println("Sandro fails.");
                return;
            }
            int l = 0;
            graph = new Graph(N, m);
            while (l < m) {
                l++;
                line = r.readLine().split(" ");
                int x = Integer.parseInt(line[0]);
                int y = Integer.parseInt(line[1]);
                if (!check2(x) || !check2(y)) {
                    System.out.println("Sandro fails.");
                    return;
                }
                addPath(graph, x, y);
            }
        } else {
            List<String> lines;
            lines = Files.readAllLines(Paths.get("./resource/topoSearch5.txt"));
            String[] line = lines.get(1).split(" ");
            int N = Integer.parseInt(line[0]);
            int m = Integer.parseInt(line[1]);
            graph = new Graph(N, m);
            for (int i = 0; i < m; i++) {
                line = lines.get(i + 2).split(" ");
                addPath(graph, Integer.parseInt(line[0]), Integer.parseInt(line[1]));
            }
        }
        toposearch(graph);
        StringBuilder result = new StringBuilder();
        if (graph.hasCycle) {
            result.append("Sandro fails.");
        } else {
            while (!graph.topoStack.isEmpty()) {
                result.append(graph.topoStack.take().name).append(" ");
            }
        }
        System.out.println(result.toString().trim());
//        assert result.toString().trim().equals(lines.get(0)) : lines.get(0);
    }

    private static boolean check1(int n, int m) {
        return n > 0 && n < 10001 && m > 0 && m < 1000000;
    }

    private static boolean check2(int x) {
        return x > 0 && x < 10001;
    }


    private static void toposearch(Graph graph) {
        Set<Vertex> vertexSet = graph.adjacencyList.keySet();
        for (Vertex v : vertexSet) {
            if (!graph.isVertexVisited(v)) {
                dfs(graph, v);
                if (graph.hasCycle) return;
            }
        }
    }


    private static void dfs(Graph graph, Vertex s) {
        graph.markVertextVisited(s);
        s.onStack = true;
        if (graph.adjacencyList.get(s) != null) {
            List<Vertex> secondVertexofTheEdge = graph.adjacencyList.get(s);
            Collections.sort(secondVertexofTheEdge, Vertex::compareTo);
            for (Vertex v : secondVertexofTheEdge) {
                if (v.onStack) {
                    graph.hasCycle = true;
                    System.out.println("Sandro fails."); System.exit(0);
                }
                if (!graph.isVertexVisited(v)) {
                    dfs(graph, v);
                }
            }
        }
        s.onStack = false;
        graph.topoStack.put(s);
    }


    private static void addPath(Graph graph, int v1, int v2) {
        Vertex ver1 = graph.createVertex(v1);
        Vertex ver2 = graph.createVertex(v2);
        if (!graph.adjacencyList.containsKey(ver1)) {
            graph.adjacencyList.put(ver1, new ArrayList<>());
        }
        graph.adjacencyList.get(ver1).add(ver2);
    }


    static private class Graph {
        boolean hasCycle = false;
        private int N;
        Map<Vertex, List<Vertex>> adjacencyList;
        HashMap<Integer, Vertex> vertex;
        Stack<Vertex> topoStack = new Stack<>(new ArrayDeque<>(N));

        public Graph(int n, int m) {
            N = n;
            adjacencyList = new HashMap<>(m);
            vertex = new HashMap<>(m);
        }

        public int getN() {
            return N;
        }

        boolean isVertexVisited(Vertex v) {
            return v.isVisited;
        }

        private void markVertextVisited(Vertex s) {
            s.isVisited = true;
        }

        Vertex createVertex(int v) {
            if (!vertex.keySet().contains(v)) {
                vertex.put(v, new Vertex(v));
            }
            return vertex.get(v);
        }
    }

    static private class Vertex implements Comparable {
        private final int name;
        boolean onStack = false;
        boolean isVisited = false;

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
        public int compareTo(Object o) {
            int ov = ((Vertex) o).name;
            return name < ov ? 1 : name > ov ? -1 : 0;
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

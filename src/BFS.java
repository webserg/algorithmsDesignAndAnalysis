import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by webserg on 13.06.2014.
 * bfs search
 */
public class BFS {
    public static void main(String[] args) {
        int N = 6;
        BFS bfs = new BFS();
        Graph graph = bfs.initGraph(N);
        bfs.bfs(graph);
        for (Vertex v : graph.vertexVisit) {
            System.out.println("vertex " + v + " was visited");
            System.out.println("path from s to" + v + " is " + graph.distance.get(v));
        }
    }


    private void bfs(Graph graph) {
        Queue<Vertex> queue = new LinkedList<>();
        queue.offer(new Vertex("s"));
        while (!queue.isEmpty()) {
            Vertex s = queue.poll();
            Integer ds = graph.distance.get(s);
            if (ds == null) ds = 0;
            List<Vertex> vertex = graph.adjacencyList.get(s);
            for (Vertex v : vertex) {
                if (!graph.vertexVisit.contains(v)) {
                    queue.offer(v);
                    if(graph.distance.get(v) == null)//we are looking for short path
                        graph.distance.put(v, ds + 1);
                }
            }
            if (!graph.vertexVisit.contains(s))
                graph.vertexVisit.add(s);
        }
    }

    private Graph initGraph(int n) {
        Graph graph = new Graph(n);
        List<String> vs = Arrays.asList("s", "a", "b", "c", "d", "e");
        for (String vn : vs) {
            Vertex v = new Vertex(vn);
            graph.vertexRange.add(v);
            graph.adjacencyList.put(v, new ArrayList<Vertex>(n));
        }
        addPath(graph, "s", "a");
        addPath(graph, "s", "b");
        addPath(graph, "a", "c");
        addPath(graph, "b", "c");
        addPath(graph, "b", "d");
        addPath(graph, "c", "e");
        addPath(graph, "c", "d");
        addPath(graph, "d", "e");
        addPath(graph, "e", "d");
        addPath(graph, "e", "c");
        return graph;
    }

    private void addPath(Graph graph, String v1, String v2) {
        graph.adjacencyList.get(new Vertex(v1)).add(new Vertex(v2));
    }


    class Graph {
        private int N;
        Map<Vertex, List<Vertex>> adjacencyList;
        List<Vertex> vertexRange;
        List<Vertex> vertexVisit;
        Map<Vertex, Integer> distance;
        private Logger log = Logger.getLogger(Graph.class.getName());

        {
            log.setLevel(Level.SEVERE);
        }

        public Graph(int n) {
            N = n;
            adjacencyList = new HashMap<>();
            vertexRange = new ArrayList<>(N);
            distance = new HashMap<>(N);
            vertexVisit = new ArrayList<>(N);
        }

        public int getN() {
            return N;
        }
    }

    class Vertex {
        private final String name;

        Vertex(String name) {
            this.name = name;
        }

        Vertex(Integer name) {
            this.name = name + "";
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Vertex vertex = (Vertex) o;

            if (name != null ? !name.equals(vertex.name) : vertex.name != null) return false;

            return true;
        }


        @Override
        public int hashCode() {
            return name != null ? name.hashCode() : 0;
        }

        @Override
        public String toString() {
            return "Vertex{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by webserg on 13.06.2014.
 *
 */
public class BFS {
    public static void main(String[] args) {
        int N=6;
        Graph graph = initGraph(N);
        bfs(graph);
        for (int i = 0; i < N; i++) {
            if(graph.vertexVisit.get(i)){
                System.out.println("vertex " + i + " was visited");
            }
        }
    }

    private static void bfs( Graph graph){
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        while (!queue.isEmpty()){
            int s = queue.poll();
            List<Integer> vertex = graph.adjacencyList[s];
            for(Integer v : vertex){
                if (!graph.vertexVisit.get(v)) {
                    queue.offer(v);
                }
            }
            graph.vertexVisit.set(s);
        }


    }

    private static Graph initGraph(int n) {
        Graph graph = new Graph(n);
        for (int i = 0; i < n; i++) {
            graph.vertexRange.add(i);
            graph.adjacencyList[i] = new ArrayList<>(n);
        }
        graph.adjacencyList[0].add(1);
        graph.adjacencyList[0].add(2);
        graph.adjacencyList[1].add(3);
        graph.adjacencyList[2].add(3);
        graph.adjacencyList[2].add(4);
        graph.adjacencyList[3].add(5);
        graph.adjacencyList[3].add(4);
        graph.adjacencyList[4].add(5);
        return graph;
    }

}

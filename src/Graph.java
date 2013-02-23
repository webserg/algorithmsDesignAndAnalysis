import java.util.ArrayList;
import java.util.List;

/**
* User: webserg
* Date: 22.02.13
*/ //        List<Integer> vertexRange = new ArrayList<>(N);
public class Graph {
    private int N;
    List<Integer>[] adjacencyList = (ArrayList<Integer>[]) new ArrayList[N];
    List<Integer> vertexRange = new ArrayList<>(N);


    public Graph(int n) {
        N = n;
    }

    public int getN() {
        return N;
    }

    private boolean mergeVertex(int v1, int v2) {
//            adjacencyList[v2].remove(new Integer(v1));
        for (Integer v : adjacencyList[v1]) {
            if (v != v2) { //remove self loops
                adjacencyList[v2].add(v);
                adjacencyList[v].add(v2);
            }
            System.out.print(v);
            System.out.print(" ");
            adjacencyList[v].remove(new Integer(v1));
        }
        adjacencyList[v1] = null;
        vertexRange.remove(new Integer(v1));
        System.out.println(" end merge");
        return true;
    }

    public boolean isEquals(List<Integer> oVertex) {
        if (oVertex.size() != vertexRange.size()) return false;
        for (Integer v : oVertex) {
            if (!vertexRange.contains(v)) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return vertexRange.hashCode();
    }
}

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * User: webserg
 * Date: 22.02.13
 */ //        List<Integer> vertexRange = new ArrayList<>(N);
public class Graph {
    private int N;
    List<Integer>[] adjacencyList;
    List<Integer> vertexRange;
    BitSet vertexVisit;
    private static Logger log = Logger.getLogger(Graph.class.getName());

    {
        log.setLevel(Level.SEVERE);
    }

    public Graph(int n) {
        N = n;
        adjacencyList = (ArrayList<Integer>[]) new ArrayList[N];
        vertexRange = new ArrayList<>(N);
        vertexVisit = new BitSet(N);
    }

    public int getN() {
        return N;
    }

    protected boolean mergeVertex(int v1, int v2) {
        for (Integer v : adjacencyList[v1]) {
            if (v != v2) { //remove self loops
                adjacencyList[v2].add(v);
                adjacencyList[v].add(v2);
            }
            adjacencyList[v].remove(new Integer(v1));
        }
        adjacencyList[v1] = null;
        vertexRange.remove(new Integer(v1));
        return true;
    }

    public Graph deepCopy() {
        Graph copy = new Graph(this.N);
        for (int i = 0; i < this.adjacencyList.length; i++) {
            copy.adjacencyList[i] = new ArrayList<>(this.adjacencyList[i]);
        }
        copy.vertexRange = new ArrayList<>(this.vertexRange);
        return copy;
    }

}

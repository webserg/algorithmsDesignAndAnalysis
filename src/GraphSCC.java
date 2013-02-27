import gnu.trove.list.array.TIntArrayList;

import java.util.BitSet;

/**
 * User: webserg
 * Date: 27.02.13
 */
public class GraphSCC {
    private int N;
    TIntArrayList[] adjacencyList;
    BitSet vertexVisit;
    public GraphSCC(int n) {
        N = n;
        adjacencyList =(TIntArrayList[]) new TIntArrayList[N];
        vertexVisit = new BitSet(N);
    }
}

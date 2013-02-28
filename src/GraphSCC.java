import gnu.trove.list.array.TIntArrayList;

import java.util.BitSet;

/**
 * User: webserg
 * Date: 27.02.13
 */
public class GraphSCC {
    private int N;
    TIntArrayList[] adjacencyListIn;
    TIntArrayList[] adjacencyListOut;
    BitSet vertexVisit;
    public GraphSCC(int n) {
        N = n;
        adjacencyListIn =(TIntArrayList[]) new TIntArrayList[N];
        adjacencyListOut =(TIntArrayList[]) new TIntArrayList[N];
        vertexVisit = new BitSet(N);
    }
}

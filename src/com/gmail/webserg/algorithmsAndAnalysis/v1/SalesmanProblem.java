package com.gmail.webserg.algorithmsAndAnalysis.v1;

import com.gmail.webserg.algorithmsAndAnalysis.v1.Combination;
import junit.framework.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.BitSet;
import java.util.HashMap;

/**
 * Created by webserg on 07.07.2014.
 */
public class SalesmanProblem {
    static final int INFINITY = Integer.MAX_VALUE;
    static final long MIN_INFINITY = Long.MIN_VALUE;

    @Test
    public void testBellmanFord() throws Exception {
        long res[] = runBellmanFordSingleSourcePath(readGraphFromFile("resource/salesmanTest.txt"), 1);
        Assert.assertEquals(6, res[res.length - 1]);
        System.out.println("");
    }

    int run(HashMap<BitSet, Integer> A, int N, int C[][]) {
        A = new HashMap<>(N);
        BitSet S = new BitSet();
        S.set(1);
        A.put(S, 1);
        for (int i = 0; i < N; i++) {
            BitSet set = new BitSet();
//                A.put(new HashSet<Integer>(N));

        }
        return 0;
    }

    static int[] A = new int[16777216];

    @Test
    public void binomSet() {
        int n = 5, k = 5;
        int d = 0;
        int d2 = (int) Math.pow(2, n);

        BitSet[] B = new BitSet[d2];
        Integer[][] A = new Integer[d2][n];
        for (int i = 0; i < (1 << n); i++) {
            BitSet set = new BitSet(n);
            for (int j = 0; j < n; j++) {
                if (((i >> j) & 1) == 1) { // bit j is on
//                    subset.add(j);
                    set.set(j);
                }
            }
//            if(set.get(0)) {
//                B[d] = set;
//                d++;
//            }
////            System.out.println(subset.toString());
//            if(set.get(0))
            B[d++] = set;
            System.out.println(d + " - bitset" + set.toString());
        }
        Assert.assertEquals(d2, d);
        System.out.println("d=" + d);
//        System.out.println("A="+Arrays.toString(A[0]));
        /*
        4 6
1 2 10
1 3 5
1 4 9
2 3 6
4 3 3
4 2 9
         */
        int min = 0;
        int q = 4, w = 6;
        int C[][] = new int[q * 2][w * 2];
        C[0][1] = 10;
        C[0][2] = 5;
        C[0][3] = 9;
        C[1][2] = 6;
        C[3][2] = 3;
        C[3][1] = 9;
        for (int i = 0; i <= q * 2; i++)
            for (int j = 0; j <= w * 2; j++) {
                if (C[i][j] == 0)
                    C[i][j] = C[j][i];
            }
        for (int i = 0; i < d2; i++)
            for (int j = 0; j < n; j++) {
                A[i][j] = INFINITY;
            }
        A[0][0] = 0;
        for (int m = 2; m < n; m++) {
            for (BitSet set : B) {
                if (set.cardinality() == m) {
                    for (int p = 0; p <= m; p++) {
                        p = set.nextSetBit(p);
                        if (p < 0) break;
//                        A[][]
                    }
                }
            }
        }

    }

    @Test
    public void salesManRun() {
        int C[][];
        int A[][];
        int n = 5;
        int min = 0;
        for (int m = 2; m < n; m++) {
            Combination comboNM = new Combination(n, m);
            long len = comboNM.size();
            for (int i = 0; i < len; i++) {
                int[] curCombo = new int[m];
                comboNM.get(i, curCombo);
                for (int j = 0; j < m; j++) {
                   // A[] we have subset comboNM how to find idx ?
//                    A[i][j] = Math.max()
                }
            }
        }

    }

    @Test
    public  void testIdx(){
        Assert.assertEquals(53,getIdxFromSubSet(new int[]{2,0,1,3}));
        Assert.assertEquals(16,getIdxFromSubSet(new int[]{0,1,2,3}));
        //  (1, 2, 5)  -->  4
        Assert.assertEquals(4,getIdxFromSubSet(new int[]{1,2,5}));
    }

    public static int getIdxFromSubSet(int [] s){
        int res=0;
        int l = s.length;
        for(int i=0;i<s.length;i++){
           res += s[i] * factorial(l--);
        }
        return res;
    }

    public static int factorial(int n) {
        int fact = 1; // this  will be the result
        for (int i = 1; i <= n; i++) {
            fact *= i;
        }
        return fact;
    }

    long[] runBellmanFordSingleSourcePath(Graph graph, int source) {
        int N = graph.vertexes.length;
        long[] D = new long[N];
        for (int i = 0; i < N; i++) {
            D[i] = INFINITY;
        }
        D[source] = 0;
        BitSet bitSet = new BitSet(N);
        for (int k = 1; k < N; k++) {
            for (Edge edge : graph.edges) {
                if (D[edge.u] != INFINITY) {
                    if (D[edge.u] + edge.w < D[edge.v]) {
                        D[edge.v] = D[edge.u] + edge.w;
                        bitSet.set(edge.u);
                        bitSet.set(edge.v, false);
                    }
                }
                if (D[edge.v] != INFINITY && D[edge.v] + edge.w < D[edge.u]) {
                    D[edge.u] = D[edge.v] + edge.w;
                    bitSet.set(edge.v);
                    bitSet.set(edge.u, false);
                }

            }
        }
        bitSet.set(N - 1);
        System.out.println(bitSet.toString());
        return D;
    }

    static class Vertex {
        private int v;
        long score;
        boolean visited;

//        private List<Edge> adjacencyList = new ArrayList<>();

        Vertex(int v) {
            this.v = v;
        }

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

        @Override
        public String toString() {
            return "Vertex{" +
                    v +
                    '}';
        }
    }

    static class Edge {
        private int u;
        private int v;
        private long w;

        Edge(int u, int v, long w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }
    }

    static class Graph {
        final int m;
        final int n;
        Vertex[] vertexes;
        Edge[] edges;

        Graph(int m, int n, Vertex[] vertexes, Edge[] edges) {
            this.m = m;
            this.n = n;
            this.vertexes = vertexes;
            this.edges = edges;
        }
    }

    static Graph readGraphFromFile(String filePath) {
        Charset charset = Charset.forName("UTF-8");
        Path path = Paths.get(filePath);
        Vertex[] vertexes = null;
        Edge[] edges = null;
        int N = 0;
        int edgesNum = 0;
        try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
            String line = reader.readLine();
            String[] strLine = line.split("\\s+");
            N = Integer.parseInt(strLine[0]);
            edgesNum = Integer.parseInt(strLine[1]);
            vertexes = new Vertex[N + 1];
            edges = new Edge[edgesNum];
            for (int v = 1; v <= N; v++)
                vertexes[v] = new Vertex(v);
            int i, j;//idx of vertexes
            int w;//weight
            int e = 0;
            while ((line = reader.readLine()) != null) {
                strLine = line.split("\\s+");
                i = Integer.parseInt(strLine[0]);
                j = Integer.parseInt(strLine[1]);
                w = Integer.parseInt(strLine[2]);
                edges[e++] = new Edge(i, j, w);

            }
        } catch (IOException x) {
            System.out.println("IOException:" + x.getMessage());
        }
        System.out.println("graph ready");
        return new Graph(edgesNum, N, vertexes, edges);
    }
}


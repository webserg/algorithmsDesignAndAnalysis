package com.gmail.webserg.algorithmsAndAnalysis.v1;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * User: webserg
 * Date: 20.02.13
 */
public class KargerMinCut {


    //    List<Integer>[] graph = (ArrayList<Integer>[]) new ArrayList[N];
//    CopyOnWriteArrayList<List<Integer>> graph = new CopyOnWriteArrayList<>();
    private static Logger log = Logger.getLogger(KargerMinCut.class.getName());

    {
        log.setLevel(Level.SEVERE);
    }

    @Test
    public void testKargerMinCut1() throws Exception {
        testKargerMinCut(4, 2, "");
    }

    @Test
    public void testKargerMinCut2() throws Exception {
        testKargerMinCut(8, 2, "resource/KargerMinCutTest1.txt");
    }

    @Test
    public void testKargerMinCut3() throws Exception {
        testKargerMinCut(11, 1, "resource/KargerMinCutTest2.txt");
    }

    @Test
    public void testKargerMinCut4() throws Exception {
        testKargerMinCut(12, 3, "resource/KargerMinCutTest3.txt");
    }

    @Test
    public void testKargerMinCut5() throws Exception {
        testKargerMinCut(20, 4, "resource/KargerMinCutTest4.txt");
    }

    @Test
    public void testKargerMinCut6() throws Exception{
        testKargerMinCut(200,17,"resource/kargerMinCutHw3.txt");
    }


    public void testKargerMinCut(int N, int expectedMinCat, String fileName) throws Exception {
        int minCatFinal = 0;
        final Random random = new Random();
        int maxMinCats = N - 1;
        int counter = 0;
        while (counter <= maxMinCats) {
            log.info("start");
            Graph graph;
            Graph initGraph = null;
            if (initGraph == null) {
                if (!fileName.isEmpty()) initGraph = FilesHelper.readGraphFromFile(fileName, N);
                else initGraph = initGraph(N);

            }
            graph = initGraph.deepCopy();


            while (graph.vertexRange.size() > 2) {
                int randomVertex1 = graph.vertexRange.get(random.nextInt(graph.vertexRange.size()));
                if (graph.adjacencyList[randomVertex1] == null) continue;
                int randomVertex2 = random.nextInt(graph.adjacencyList[randomVertex1].size());
                graph.mergeVertex(randomVertex1, graph.adjacencyList[randomVertex1].get(randomVertex2));
            }
            log.info("vertexRange = " + Arrays.toString(graph.vertexRange.toArray()));
            for (int v : graph.vertexRange) {
                log.info(Arrays.toString(graph.adjacencyList[v].toArray()));
            }
            int currentMinCat = graph.adjacencyList[graph.vertexRange.get(0)].size();
            if (minCatFinal == 0 || minCatFinal > currentMinCat) minCatFinal = currentMinCat;
            counter++;
        }
        Assert.assertEquals(expectedMinCat, minCatFinal);
    }

    private Graph initGraph(int n) {
        Graph graph = new Graph(n);
        for (int i = 0; i < n; i++) {
            graph.vertexRange.add(i);
            graph.adjacencyList[i] = new ArrayList<>(n);
        }
        graph.adjacencyList[0].add(1);
        graph.adjacencyList[0].add(2);
        graph.adjacencyList[1].add(0);
        graph.adjacencyList[1].add(2);
        graph.adjacencyList[1].add(3);
        graph.adjacencyList[2].add(0);
        graph.adjacencyList[2].add(1);
        graph.adjacencyList[2].add(3);
        graph.adjacencyList[3].add(1);
        graph.adjacencyList[3].add(2);
        return graph;
    }

}

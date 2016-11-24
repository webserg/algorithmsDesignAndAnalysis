import gnu.trove.list.array.TIntArrayList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * User: webserg
 * Date: 16.02.13
 */
public class FilesHelper {

    private static Logger log = Logger.getLogger(FilesHelper.class.getName());

    static {
        log.setLevel(Level.SEVERE);
    }

    public static void writeIntArrayToFile(String filePath, int[] arr) {
        Charset charset = Charset.forName("UTF-8");
        Path path = Paths.get(filePath);
        try (BufferedWriter writer = Files.newBufferedWriter(path, charset)) {
            for (int i = 0; i < arr.length; i++) {
                writer.write("" + arr[i], 0, ("" + arr[i]).length());
                writer.newLine();
            }
            writer.flush();
            writer.close();
        } catch (IOException x) {
            log.severe("IOException:" + x.getMessage());
        }
    }

    public static void writeLongToFile(String filePath, long longNumber) {
        Charset charset = Charset.forName("UTF-8");
        Path path = Paths.get(filePath);
        try (BufferedWriter writer = Files.newBufferedWriter(path, charset)) {
            writer.write("" + longNumber, 0, ("" + longNumber).length());

            writer.flush();
            writer.close();
        } catch (IOException x) {
            log.severe("IOException:" + x.getMessage());
        }
    }

    static int[] readIntArrayFromFile(String filePath, int arraySize) {
        Charset charset = Charset.forName("UTF-8");
        Path path = Paths.get(filePath);
        int result[] = new int[arraySize];
        int idx = 0;
        try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
            String line;
            while ((line = reader.readLine()) != null) {
                result[idx++] = Integer.parseInt(line);
            }
            reader.close();
        } catch (IOException x) {
            log.severe("IOException:" + x.getMessage());
        }
        return result;
    }

    static int[] readIntRangeArrayFromFile(String filePath, int arraySize, int startRange, int endRange) {
        Charset charset = Charset.forName("UTF-8");
        Path path = Paths.get(filePath);
        int result[] = new int[arraySize];
        int result2[] = null;
        int idx = 0;
        try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
            String line;
            while ((line = reader.readLine()) != null) {
                int n = Integer.parseInt(line);
                if (n <= endRange)
                    result[idx++] = Integer.parseInt(line);
            }

            reader.close();
            result2 = new int[idx];

            for(int i=0;i<idx;i++){
                result2[i] = result[i];
            }
            return result2;
        } catch (IOException x) {
            log.severe("IOException:" + x.getMessage());
        }
        return result2;
    }

    static long[] readLongArrayFromFile(String filePath, int arraySize, int startRange, int endRange) {
        Charset charset = Charset.forName("UTF-8");
        Path path = Paths.get(filePath);
        long result[] = new long[arraySize];
        long result2[] = null;
        int idx = 0;
        try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
            String line;
            while ((line = reader.readLine()) != null) {
                long n = Long.parseLong(line);
                result[idx++] = n;
            }

            reader.close();
            result2 = new long[idx];

            for(int i=0;i<idx;i++){
                result2[i] = result[i];
            }
            return result2;
        } catch (IOException x) {
            log.severe("IOException:" + x.getMessage());
        }
        return result2;
    }

    static Graph readGraphFromFile(String filePath, int arraySize) {
        Charset charset = Charset.forName("UTF-8");
        Path path = Paths.get(filePath);
        Graph graph = new Graph(arraySize);
        try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] strNumber = line.split("\\s+");
                int v = Integer.parseInt(strNumber[0]) - 1;
                graph.adjacencyList[v] = new ArrayList<>(strNumber.length - 1);
                graph.vertexRange.add(v, v);
                log.info(line);
                for (int i = 1; i < strNumber.length; i++) {
                    int vv = Integer.parseInt(strNumber[i]) - 1;
                    graph.adjacencyList[v].add(vv);
                }
            }

            reader.close();
        } catch (IOException x) {
            log.severe("IOException:" + x.getMessage());
        }
        return graph;
    }

    static GraphSCC readGraphSCCFromFile(String filePath, int arraySize) {
        Charset charset = Charset.forName("UTF-8");
        Path path = Paths.get(filePath);
        GraphSCC graph = new GraphSCC(arraySize);
        try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
            String line;
            int curVertex = 0;
            TIntArrayList tails = new TIntArrayList();
//            TIntArrayList tailsRev = new TIntArrayList();
            while ((line = reader.readLine()) != null) {
                String[] strNumber = line.split("\\s+");
                int head = Integer.parseInt(strNumber[0]) - 1;
                if (curVertex != head) {
                    graph.adjacencyListIn[curVertex] = tails;
//                    graph.adjacencyListOut[curVertex] = tailsRev;
                    tails = new TIntArrayList();
//                    tailsRev = new TIntArrayList();
                    curVertex = head;
                }
                int tail = Integer.parseInt(strNumber[1]) - 1;
                tails.add(tail);
            }
            graph.adjacencyListIn[curVertex] = tails;
            reader.close();
        } catch (IOException x) {
            log.severe("IOException:" + x.getMessage());
        }
        return graph;
    }


    static DijkstraShortestPath.MinHeap readDijkstraGraphFromFile(String filePath, int arraySize) {
        Charset charset = Charset.forName("UTF-8");
        Path path = Paths.get(filePath);
        DijkstraShortestPath.Vertex v;
        DijkstraShortestPath.MinHeap minHeap = new DijkstraShortestPath.MinHeap(arraySize);
        try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] strLine = line.split("\\s+");
                int vertexNum = Integer.parseInt(strLine[0]);
                if (minHeap.graph[vertexNum] == null) {
                    minHeap.graph[vertexNum] = v = new DijkstraShortestPath.Vertex(vertexNum);
                } else {
                    v = minHeap.graph[vertexNum];
                }
                for (int i = 1; i < strLine.length; i++) {
                    String[] vertexNumLen = strLine[i].split(",");
                    int vv = Integer.parseInt(vertexNumLen[0]);
                    DijkstraShortestPath.Vertex head;
                    if (minHeap.graph[vv] == null) {
                        minHeap.graph[vv] = head = new DijkstraShortestPath.Vertex(vv);
                    } else {
                        head = minHeap.graph[vv];
                    }
                    int len = Integer.parseInt(vertexNumLen[1]);
                    DijkstraShortestPath.Edge e = new DijkstraShortestPath.Edge(v, head, len);
                    v.adjacencyList.add(e);
                }
            }
            reader.close();
        } catch (IOException x) {
            log.severe("IOException:" + x.getMessage());
        }
        return minHeap;
    }
}

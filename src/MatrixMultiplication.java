import java.util.Arrays;

/**
 * Created by webserg on 02.06.2014.
 *
 */
public class MatrixMultiplication {
    static int N = 2;

    public static void main(String[] args) {
        int x[][] = {{1, 2}, {3, 4}};
        int y[][] = {{5, 6}, {7, 8}};
        int z[][] = {{19, 22}, {43, 50}};
        System.out.println(Arrays.deepToString(multiply(x, y)));
        assert Arrays.deepEquals(z, multiply(x, y));


    }

    public static int[][] multiply(int x[][], int y[][]) {
        int res[][] = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++)
                for (int k = 0; k < N; k++) {
                    res[i][j] += x[i][k] * y[k][j];
                }
            }
            return res;
        }
    }

//    public static int[][] multiplyR(int x[][], int y[][]) {
//        int res[][] = new int[N][N];
//        for (int i = 0; i < N; i++) {
//            for (int j = 0; j < N; j++)
//                for (int k = 0; k < N; k++) {
//                    res[i][j] +=  multiplyRec(x[i][k] , y[k][j]);
//                }
//        }
//        return res;
//    }

//    public static int[][] multiplyRec(int x[][], int y[][]) {
//}

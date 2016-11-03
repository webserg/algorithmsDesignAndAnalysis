import java.util.Scanner;

/**
 * Created by webse on 11/3/2016.
 */
public class TestScaner {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int ii = in.nextInt();
        System.out.println(ii + "fgdfg");
        int iii = in.nextInt();
        System.out.println(iii);
        while(in.hasNext()) {
            int i = in.nextInt();
            System.out.println(i);
        }
    }
}

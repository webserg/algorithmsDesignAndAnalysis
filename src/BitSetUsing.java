import java.util.BitSet;

/**
 * Created by webserg on 23.06.2014.
 */
public class BitSetUsing {
    public static void main(String[] args) {
        BitSet bitSet = new BitSet(24);
        bitSet.set(2);
        bitSet.set(24);
        BitSet bitSet2 = new BitSet(24);
        bitSet2.set(2);
        bitSet2.set(24);
        BitSet bitSet3 = new BitSet(24);
        bitSet3 =(BitSet) bitSet.clone();
        System.out.println(bitSet.cardinality());
        System.out.println(bitSet2.cardinality());
        System.out.println(bitSet3.cardinality());
        bitSet3.xor(bitSet2);
        System.out.println(bitSet3.cardinality());
    }
}

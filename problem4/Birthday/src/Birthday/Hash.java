package Birthday;

import java.util.*;

public class Hash {

    private static final int KEY_SIZE = 24;
    private static final int[] INIT_VECTOR = new int[MDES.BLOCK_SIZE];


    public static int[] computeHash(int[] bitstring) {
        if (bitstring.length == 0) {
            return bitstring;
        }

        int[] hash = new int[MDES.BLOCK_SIZE];

        int[] paddedBits = IOControl.addPadding(bitstring, KEY_SIZE);

        int[][] keys = IOControl.divideBitStrIntoBlocks(paddedBits, KEY_SIZE);

        int n = keys.length;

        int[] iv = CTR.genIV();

        int[][] outs = new int[n][MDES.BLOCK_SIZE];
        Arrays.fill(INIT_VECTOR, 0);
        outs[0] = CTR.decrypt(INIT_VECTOR, keys[0], iv);
        for (int i = 1; i < n; i++) {
            outs[i] = CTR.decrypt(outs[i-1], keys[i], iv);
        }

        return outs[n-1];
    }
}


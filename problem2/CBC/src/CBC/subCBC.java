package CBC;
import java.io.*;
import java.util.*;
public class subCBC {

	 public static int[] genIV() {
	        int[] v = new int[MDES.BLOCK_SIZE];
	        int randInt = (int)(Math.random() * 9973); 
	        for (int i = 0; i < 16; i++) {
	            v[i] = randInt & 0x1;
	            randInt = randInt >> 1;
	        }
	        return v;
	    }


	    public static int[] encrypt(int[] bitstring, int[] key, int[] iv) {
	        assert key.length == MDES.KEY_LEN * MDES.ENC_PASSES;
	        return CBC_Framework(bitstring, key, iv, 'e');
	    }


	    public static int[] decrypt(int[] bitstring, int[] key, int[] iv) {
	        assert key.length == MDES.KEY_LEN * MDES.ENC_PASSES;
	        return CBC_Framework(bitstring, key, iv, 'd');
	    }


	    private static int[] CBC_Framework(int[] in, int[] key,
	                                       int[] iv, char encOrDec) {
	        assert encOrDec == 'e' || encOrDec == 'd';

	        if (in.length == 0) {  
	            return in;
	        }

	        int [][] internalKey = IOControl.divideBitStrIntoBlocks(key, MDES.KEY_LEN);

	        int[] bitStr = IOControl.addPadding(in, MDES.BLOCK_SIZE);

	        int[] outBitStr = {0};  
	        if (encOrDec == 'e') {
	            outBitStr = encryptInternal(bitStr, internalKey, iv);
	        } else if (encOrDec == 'd') {
	            outBitStr = decryptInternal(bitStr, internalKey, iv);
	        } else { 
	        }

	        return outBitStr;
	    }

	    private static int[] encryptInternal(int[] bs, int[][] key, int[] iv) {
	        assert bs.length % MDES.BLOCK_SIZE == 0;

	        int[] result = new int[bs.length];

	        int[][] xs = IOControl.divideBitStrIntoBlocks(bs, MDES.BLOCK_SIZE);
	        int[][] ys = new int[xs.length][MDES.BLOCK_SIZE];

	        int[] t = IOControl.bitStrXOR(iv, xs[0]);
	        ys[0] = MDES.encryptKernel(t, key);
	        int resultOffset = IOControl.copyIntArrIntoArr(result, 0, ys[0]);

	        for (int i = 1; i < xs.length; i++) {
	            t = IOControl.bitStrXOR(ys[i-1], xs[i]);
	            ys[i] = MDES.encryptKernel(t, key);

	            resultOffset = IOControl.copyIntArrIntoArr(result, resultOffset, ys[i]);
	        }
	        return result;
	    }


	    private static int[] decryptInternal(int[] bs, int[][] key, int[] iv) {
	        assert bs.length % MDES.BLOCK_SIZE == 0;

	        int[] result = new int[bs.length];

	        int[][] ys = IOControl.divideBitStrIntoBlocks(bs, MDES.BLOCK_SIZE);
	        int[][] xs = new int[ys.length][MDES.BLOCK_SIZE];

	        int[] t = MDES.decryptKernel(ys[0], key);
	        xs[0] = IOControl.bitStrXOR(t, iv);
	        int resultOffset = IOControl.copyIntArrIntoArr(result, 0, xs[0]);

	        for (int i = 1; i < ys.length; i++) {
	            t = MDES.decryptKernel(ys[i], key);
	            xs[i] = IOControl.bitStrXOR(t, ys[i-1]);

	            resultOffset = IOControl.copyIntArrIntoArr(result, resultOffset, xs[i]);
	        }
	        return result;
	    }
	
}

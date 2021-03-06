package CTR;


import java.util.*;

public class subCTR {

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
     return CTR_Framework(bitstring, key, iv, 'e');
 }


 public static int[] decrypt(int[] bitstring, int[] key, int[] iv) {
     assert key.length == MDES.KEY_LEN * MDES.ENC_PASSES;
     return CTR_Framework(bitstring, key, iv, 'd');
 }


 private static int[] CTR_Framework(int[] in, int[] key,
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

     int[] t;
     int c = IOControl.bitStrToInt(iv);
     int[] cstr;
     int resultOffset = 0;
     for (int i = 0; i < xs.length; i++) {
         t = MDES.encryptKernel(IOControl.intToBitStr(c + i, MDES.BLOCK_SIZE),
                                key);
         ys[i] = IOControl.bitStrXOR(xs[i], t);

         resultOffset = IOControl.copyIntArrIntoArr(result, resultOffset, ys[i]);
     }
     return result;
 }

 private static int[] decryptInternal(int[] bs, int[][] key, int[] iv) {
     return encryptInternal(bs, key, iv);
 }
}

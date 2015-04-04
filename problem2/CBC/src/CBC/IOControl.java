package CBC;

import java.io.*;
import java.util.*;

public class IOControl {

	public static int readInput(char[] charBuffer) throws IOException {
        int i = 0;
        int ch;
        
        Scanner s =new Scanner(System.in);
        while (i < charBuffer.length && (ch = System.in.read()) != -1) {
            charBuffer[i++] = (char)ch;
        }
        return i;
    }


    public static int[] addPadding(int[] bs, int blocksize) {
        if (bs.length % blocksize == 0) {
            return bs;
        } else {

            double t = (double)bs.length / blocksize;
            int bitBufLen = ((int)Math.ceil(t)) * blocksize;
            int[] code = new int[bitBufLen];

            Arrays.fill(code, 0);

     
            for (int i = 0; i < bs.length; i++) {
                code[i] = bs[i];
            }
            return code;
        }
    }


    static int[] intToBitStr(int a, int outputLength) {
        assert outputLength >= 0 && outputLength <= 32 : outputLength;

        int[] s = new int[outputLength];
        for (int i = outputLength - 1; i >= 0; i--) {

            s[i] = a & 0x1;
            a = a >> 1;
        }
        return s;
    }

    static int bitStrToInt(int[] a) {
        int s = 0;
        for (int i = 0; i < a.length; i++) {
            s += a[i] * Math.pow(2, (a.length - i - 1));
        }
        return s;
    }


    static int[] bitStrXOR(int[] a, int[] b) {
        assert a.length == b.length
            : "a.length: " + a.length + ", b.length: " + b.length;

        int[] r = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            r[i] = a[i] ^ b[i];
        }
        return r;
    }


    static int[][] divideBitStrIntoBlocks(int[] bs, int blocksize) {
        assert bs.length % blocksize == 0;
        int n = bs.length / blocksize;
        int[][] r = new int[n][blocksize];
        for (int i = 0; i < n; i++) {
            r[i] = Arrays.copyOfRange(bs, i*blocksize, (i+1)*blocksize);
        }
        return r;
    }


    public static int[] strBitsToIntBits(String bits) {
        byte[] bytes = bits.getBytes();
        int[] k = new int[bytes.length];
        int c = 0;
        for (int i = 0; i < bytes.length; i++) {
            char ch = (char)bytes[i];
            if (ch == '1') {
                k[i] = 1;
            } else if (ch == '0') {
                k[i] = 0;
            } else {
                throw new IllegalArgumentException("bad bit: " + ch);
            }
        }
        return k;
    }

    public static String intBitsToStrBits(int[] bits) {
        char[] cs = new char[bits.length];
        for (int i = 0; i < bits.length; i++) {
            cs[i] = (bits[i] == 1 ? '1' : '0');
        }
        return new String(cs);
    }

    public static void printBitString(int[] a) {
    	System.out.println("Encryption");
        if (a.length == 0) {
            return;
        }
        for (int i : a) {
        	
            System.out.print(i);
        }
        System.out.println();
    }

    static int copyIntArrIntoArr(int[] target, int targetOffset,
                                 int[] src) {
        for (int i = 0; i < src.length; i++) {
            target[targetOffset++] = src[i];
        }
        return targetOffset;
    }
	
}

package CBC;

import java.io.*;
import java.util.Arrays;


public class CBC {

	 public static void main(String[] args) {

	     
	        String in =new String("cryptography is an important tool for network security. but there are other issues for network security"); 
	        System.out.println("plaintext is:"+in);


	        int[] code = MDES.txtToCode(in.toCharArray());
	        IOControl.printBitString(code);


	        String key = "101101010010100101101011";
	        int[] iv = subCBC.genIV();
	        int[] encout = subCBC.encrypt(code, IOControl.strBitsToIntBits(key), iv);
	        IOControl.printBitString(encout);
	        int[] decout = subCBC.decrypt(encout, IOControl.strBitsToIntBits(key), iv);
	        IOControl.printBitString(decout);


	        char[] txt = MDES.codeToTxt(decout);
	        System.out.println(txt);
	    }
}

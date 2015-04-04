package CTR;

import java.io.*;
import java.util.Arrays;
public class CTR {

	public static void main(String[] args) {

     
        String in = new String("cryptography is an important tool for network security. but there are other issues for network security."); 
        System.out.println("plaintext is:"+in);

        int[] code = MDES.txtToCode(in.toCharArray());
        IOControl.printBitString(code);


        String key = "101101010010100101101011";
        int[] iv = subCTR.genIV();
        int[] encout = subCTR.encrypt(code, IOControl.strBitsToIntBits(key), iv);
        IOControl.printBitString(encout);
        int[] decout = subCTR.decrypt(encout, IOControl.strBitsToIntBits(key), iv);
        IOControl.printBitString(decout);

        // bit string to text
        char[] txt = MDES.codeToTxt(decout);
        System.out.println(txt);
    }
}

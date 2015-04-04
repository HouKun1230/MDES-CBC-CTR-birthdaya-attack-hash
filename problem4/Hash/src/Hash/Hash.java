package Hash;
import java.io.*;
public class Hash {
	
	public static void main(String[] args) {

      
        String in = new String("cryptography is an important tool for network security. but there are other issues for network security"); 
        System.out.println("plaintext is:"+in);


        int[] code = MDES.txtToCode(in.toCharArray());
        IOControl.printBitString(code);


        int[] hash = subHash.computeHash(code);
        IOControl.printBitString(hash);
    }

}

package des;

import java.util.ArrayList;

public class Substitution {

	public char Library[] = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',' ','.',',','?','(',')'};
	public char[] Plaintext1;
	public char[] Plaintext2;
	public char[] Ciphertext;
	public int[] Integercode;
	public int[] Integercode2;
	public String Binarystring1; // for encryption
	public String Binarystring2 = new String(); // for decryption
	
	public Substitution(){
		System.out.println("The substitution function was created.");
	}
	
	/*********************************For Character to binary**************************************************/
	
	//Set plaintext string
	public void Setplaintext(String plaintext){
		
		this.Plaintext1 = plaintext.toCharArray();
		
		System.out.println("The length is "+Plaintext1.length);
		for(int i=0;i<Plaintext1.length;i++){
			System.out.print(Plaintext1[i]+" ");
		}
		System.out.println();
	}
	
	//Convert character to integer.
	public void Charactertointeger(){
		int i,j;
		Integercode = new int[Plaintext1.length];
		for(i=0;i<Plaintext1.length;i++){
			for(j=0;j<Library.length;j++){
				if(Plaintext1[i] == Library[j]){
					Integercode[i] = j;
				}
			}
		}
		
		System.out.print("The integer stream: ");
		for(i=0;i<Integercode.length;i++){
			System.out.print(Integercode[i]+" ");
		}
		System.out.println();
		
	}
	
	//Convert integer to 5 bits unit binary.
	public void Integertobinary(){
		for(int i=0;i<Integercode.length;i++){
			Binarystring1 += toFullBinaryString(Integercode[i]);
		}
		System.out.println("Binary String: "+Binarystring1);
	}
	
	//Convert character to binary, then return a binary string.
	public String Charactertobinary(){
		Binarystring1 = new String(); // clean the Binarystring1
		Charactertointeger();
		Integertobinary();
		return Binarystring1;	
	}
	
	/*********************************For binary to character**************************************************/

	//Set Cipertext which should be substitute to Plaintext1
	public void Setbinarystring(String binarystring){
		this.Binarystring2 = binarystring;
		System.out.println("Binary String: "+Binarystring2);
	}
		
	//convert binary string to integer array
	public void Binarytointeger(){
		int start,end;
		String temp;

		int Integerlengh = Binarystring2.length()/5; //calculate how many 5 bits string in the binary string
		Integercode2 = new int[Integerlengh];
		for(int i=0;i<Integercode2.length;i++){
			start = (0+5*i);
			end = (5+5*i);
			temp = Binarystring2.substring(start, end); //extract 5 bits string from the binary string
			Integercode2[i] = Integer.valueOf(temp, 2); //convert 5 bits binary string to decimal integer
		}
		
		System.out.print("The integer stream: ");
		for(int i=0;i<Integercode2.length;i++){
			System.out.print(Integercode2[i]+" ");
		}
		System.out.println();
	}
	
	//Convert integer array to plaintext
	public void Integertocharacter(){
		Plaintext2 = new char[Integercode2.length];
		for(int i=0;i<Integercode2.length;i++){
			for(int j=0;j<Library.length;j++){
				if(Integercode2[i] == j){
					Plaintext2[i] = Library[j];
				}
			}
		}
		
		System.out.println("The length is "+Plaintext2.length);
		for(int i=0;i<Plaintext2.length;i++){
			System.out.print(Plaintext2[i]+" ");
		}
		System.out.println();	
	}
	
	//convert binary to plaintext, then return plaintext string.
	public String Binarytocharacter(){
		Binarytointeger();
		Integertocharacter();
		String plainstring = new String(Plaintext2);
		//System.out.println("The sentence : "+plainstring);
		return plainstring;
	}
	
	
	/*********************************For decimal to binary with omitted 0**************************************************/
	public static String toFullBinaryString(int num) {
        char[] chs = new char[5];
        for(int i = 0; i < 5; i++) {
            chs[5 - 1 - i] = (char)((num >> i & 1) + '0');
        }
        return new String(chs);
    }
	
}

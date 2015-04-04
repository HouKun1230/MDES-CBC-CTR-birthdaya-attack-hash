package des;

import java.util.ArrayList;
import java.util.Scanner;

import des.Substitution;
import des.Roundkey;
import des.Ffunction;

public class DES {


	public static String Plaintext1 = "how do you like computer science";

	public static String Plaintext2= new String();
	public static String Plainbinary1 = new String();
	public static String Plainbinary2;
	public static String Cipherbinary1;
	public static String Cipherbinary2 = new String();
	public static String Ciphertext1 = new String();
	public static String Ciphertext2 = new String();;
	public static String Key ="101101010010100101101011";
	public static String Leftstring = new String();
	public static String Rightstring = new String();
	public static String Leftstring2 = new String();
	public static String Rightstring2 = new String();
	public static String[] Keys = new String[4];
	public static String[] Roundkeys = new String[4];
	public static Substitution mySubstitution = new Substitution();
	public static Ffunction myFfunction = new Ffunction();
	public static Roundkey myRoundkey = new Roundkey();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// generate four round keys
		Generateroundkey();
		
		// input what functions you want to process, you should do encryption at first.
		Scanner input = new Scanner(System.in); 
		while(true){
			System.out.println("************************");
			System.out.println("You should do encryption at first.");
			System.out.println("input 1 for encryption");
			System.out.println("input 2 for decryption");
			System.out.println("input 0 for exit");
			System.out.println("************************");
			System.out.print("Please input function: ");
			int choice= input.nextInt();
			
			if(choice == 1){
				Encryption();
			}
			else if(choice == 2){
				Decryption();
			}
			else if(choice == 0){
				input.close();
				break;
			}
		}
		
		
		
	}
	
	/***********************Generate Key and Round key************************************/
	//Generate the Key
	public static String Nkey(int num){
		String Newkey = new String();
		String Tempkey = new String();
		System.out.println("*****New key round "+num+"*****");
		if(num == 0){
			Newkey = Key;
		}
		else{
			Tempkey = Nkey(num-1);
			myRoundkey.Setkey(Tempkey);
			Newkey = myRoundkey.Getnewkey();
		}
		System.out.println("*****end New key round "+num+"*****");
		return Newkey;
	}
	
	//Generate four round keys
	public static void Generateroundkey(){
		for(int i=0;i<4;i++){
			Keys[i] = Nkey(i);
			myRoundkey.Setkey(Keys[i]);
			Roundkeys[i] = myRoundkey.Getroundkey();
		}
		
		System.out.println("*****************************");
		System.out.println("*********Round keys**********");
		System.out.println("*****************************");
		for(int i=0;i<4;i++){
			System.out.println("Key "+i+" = "+Keys[i]);
			System.out.println("Round key "+i+" = "+Roundkeys[i]);
		}
		System.out.println("*********************************");
		System.out.println("*********end Round keys**********");
		System.out.println("*********************************");
	}
	
	/***********************Encryption************************************/
	public static void Encryption(){
		int times,start,end;
		Cipherbinary1 = new String(); //clean the Cipherbinary1
		String block;
		mySubstitution.Setplaintext(Plaintext1);
		Plainbinary1 = mySubstitution.Charactertobinary();
		times = Plainbinary1.length()/16; // calculate how many blocks the plaintext binary code included
		for(int i=0;i<times;i++){
			start = 0+i*16;
			end = 16+i*16; // substring function take bitstring before the end position
			block = Plainbinary1.substring(start, end); //extract each block from plaintext binary code
			
			System.out.println("***************************************");
			System.out.println("************Block "+i+" process************");
			System.out.println("***************************************");
			Cipherbinary1 += Blockprocess(block);
			System.out.println("***************************************");
			System.out.println("**********end Block "+i+" process**********");
			System.out.println("***************************************");
		}

		System.out.println("****************************");
		System.out.println("Cipher binary code: "+Cipherbinary1);
		System.out.println("****************************");
		
		mySubstitution.Setbinarystring(Cipherbinary1);
		Ciphertext1 = mySubstitution.Binarytocharacter();
		System.out.println("****************************");
		System.out.println("Ciphertext : "+Ciphertext1);
		System.out.println("****************************");
		
		Ciphertext2 = Ciphertext1; //let Ciphertext of encryption = Ciphertext of decryption
	}
	
	//process each 16 bits block for encryption
	public static String Blockprocess(String block){
		Leftstring = block.substring(0, 8);
		Rightstring = block.substring(8,16);
		String Lstring = Left(4); //get L4
		String Rstring = Right(4); //get R4
		String bitstring = Lstring + Rstring;
		return bitstring;
	}
	
	//Left string function for encryption
	public static String Left(int num){
		String Lstring = new String();
		System.out.println("*****Left round "+num+"*****");
		if(num == 0){
			Lstring = Leftstring;
		}
		else{
			Lstring = Right(num-1);
		}
		System.out.println("*****end Left round "+num+"*****");
		return Lstring;
	}
	
	//Right string function for encryption
	public static String Right(int num){
		String Rstring = new String();
		System.out.println("*****Right round "+num+"*****");
		if(num == 0){
			Rstring = Rightstring;
		}
		else{
			
			String bitstring1 = Left(num-1);
			String bitstring2 = myFfunction.Setting(Right(num-1), Roundkeys[num-1]); //num-1 guarantee that rounk key begain with the first one
			Rstring = XOR(bitstring1, bitstring2);
		}
		System.out.println("*****end Right round "+num+"*****");
		return Rstring;
	}
	
	/***********************Decryption************************************/
	public static void Decryption(){
		int times,start,end;
		Plainbinary2 = new String();  //clean the Plainbinary2
		String block;
		mySubstitution.Setplaintext(Ciphertext2);
		Cipherbinary2 = mySubstitution.Charactertobinary();

		times = Cipherbinary2.length()/16;
		for(int i=0;i<times;i++){
			start = 0+i*16;
			end = 16+i*16; 
			block = Cipherbinary2.substring(start, end);
			
			System.out.println("***************************************");
			System.out.println("************Block "+i+" process************");
			System.out.println("***************************************");
			Plainbinary2 += Blockprocess2(block);
			System.out.println("***************************************");
			System.out.println("**********end Block "+i+" process**********");
			System.out.println("***************************************");
		}
		System.out.println("****************************");
		System.out.println("Plain binary code: "+Plainbinary2);
		System.out.println("****************************");
		
		mySubstitution.Setbinarystring(Plainbinary2);
		Plaintext2 = mySubstitution.Binarytocharacter();
		System.out.println("****************************");
		System.out.println("Plaintext : "+Plaintext2);
		System.out.println("****************************");
		
	}
	
	//process each 16 bits block for decryption
	public static String Blockprocess2(String block){
		Leftstring2 = block.substring(0, 8);
		Rightstring2 = block.substring(8,16);
		String Lstring = Left2(0); //get L0
		String Rstring = Right2(0); //get R0
		String bitstring = Lstring + Rstring;
		return bitstring;
	}
	
	//Left string function for decryption
	public static String Left2(int num){
		String Lstring = new String();
		System.out.println("*****Left round "+num+"*****");
		if(num == 4){
			Lstring = Leftstring2;
		}
		else{
			String bitstring1 = Right2(num+1);
			String bitstring2 = myFfunction.Setting(Right2(num), Roundkeys[num+1-1]); //num+1-1 guarantee that rounk key begain with the first one
			Lstring = XOR(bitstring1, bitstring2);	
		}
		System.out.println("*****end Left round "+num+"*****");
		return Lstring;
	}
		
	//Right string function for decryption
	public static String Right2(int num){
		String Rstring = new String();
		System.out.println("*****Right round "+num+"*****");
		if(num == 4){
			Rstring = Rightstring2;
		}
		else{
			Rstring = Left2(num+1);
		}
		System.out.println("*****end Right round "+num+"*****");
		return Rstring;
	}
	
	/****************************XOR function with omitted 0***************************************/
	public static String XOR(String bitstring1, String bitstring2){
		char[] bitchar1 = bitstring1.toCharArray();
		char[] bitchar2 = bitstring2.toCharArray();
		char[] Xorresult = new char[8];
		for(int i=0;i<8;i++){
			if(bitchar1[i] == bitchar2[i]){
				Xorresult[i] = '0';
			}
			else Xorresult[i] = '1';
		}
		String XORstring = new String(Xorresult);
		System.out.println("(bitstring 1)"+bitstring1+" XOR (bitstring 2)"+bitstring2+" = "+XORstring);
		
		return XORstring;
	}
}

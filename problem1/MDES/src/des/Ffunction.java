package des;

public class Ffunction {
	
	public String Bitstring;
	public String Expandedbitstring;
	public String Key;
	public String XORstring;
	public String Sboxstring;
	public int[][] Sbox1 = {{15,1,8,14,6,11,3,4,9,7,2,13,12,0,5,10},{3,13,4,7,15,2,8,14,12,0,1,10,6,9,11,5},{0,14,7,11,10,4,13,1,5,8,12,6,9,3,2,15},{13,8,10,1,3,15,4,2,11,6,7,12,0,5,14,9}};
	public int[][] Sbox2 = {{7,13,14,3,0,6,9,10,1,2,8,5,11,12,4,15},{13,8,11,5,6,15,0,3,4,7,2,12,1,10,14,9},{10,6,9,0,12,11,7,13,15,1,3,14,5,2,8,4},{3,15,0,6,10,1,13,8,9,4,5,11,12,7,2,14}};
	
	public Ffunction(){
		System.out.println("The f(x) function was created.");
	}
	
	public String Setting(String bitstring,String key){
		System.out.println("*****f(x) function*****");
		this.Key = key;
		this.Bitstring = bitstring;
		Expansion();
		XOR();
		Sbox();
		System.out.println("*****end f(x) function*****");
		return Sboxstring;
	}
	//Expand 8 bits Bitstring to 12 bits
	public void Expansion(){
		char[] Chararray = Bitstring.toCharArray();
		String Bits = new String();
		for(int i=0;i<Chararray.length;i++){
			if(i%2 == 0){
				Bits += Chararray[i];
			}
		}

		Expandedbitstring = Bitstring+Bits;
		System.out.println("The expanded bitstring is "+Expandedbitstring);
	}
	
	//This XOR function cannot appear the omitted digit in the header.
	public String XOR2(){
		int a,b;
		String Xorresult;
		a = Integer.valueOf(Expandedbitstring, 2);
		b = Integer.valueOf(Key,2);
		Xorresult = Integer.toBinaryString(a^b);
		return Xorresult;
	}
	
	//XOR function
	public void XOR(){
		char[] Charbitstring = Expandedbitstring.toCharArray();
		char[] Charkey = Key.toCharArray();
		char[] Xorresult = new char[12];
		for(int i=0;i<12;i++){
			if(Charbitstring[i] == Charkey[i]){
				Xorresult[i] = '0';
			}
			else Xorresult[i] = '1';
		}
		XORstring = new String(Xorresult);
		System.out.println("(Expanded bitstring)"+Expandedbitstring+" XOR (Key)"+Key+" = "+XORstring);
	}
	
	public void Sbox(){
		String Bitstring1 = XORstring.substring(0, 6);
		String Bitstring2 = XORstring.substring(6, 12);
		
		/****************S-box1 part*****************/
		String Sbox1rowstring = Bitstring1.substring(0,1)+Bitstring1.substring(5,6); // take the first and last bit from bitstring
		String Sbox1colunmstring = Bitstring1.substring(1, 5); //take the rest 4 bits except first and last bit
		int Sbox1row = Integer.valueOf(Sbox1rowstring, 2); //convert binary string to decimal integer to get row
		int Sbox1column = Integer.valueOf(Sbox1colunmstring, 2); //convert binary string to decimal integer to get column
		
		System.out.println("S-box1 row:"+Sbox1row);
		System.out.println("S-box1 column:"+Sbox1column);
		
		int Sbox1value = Sbox1[Sbox1row][Sbox1column]; //take value from S-box 1
		String Sbox1string = toFullBinaryString(Sbox1value); // convert decimal integer to binary string
		
		System.out.println("The expanded 4 bits bitstring part1 coming from S-box1 is "+Sbox1string+"("+Sbox1value+")");
		
		/****************S-box2 part*****************/
		String Sbox2rowstring = Bitstring2.substring(0,1)+Bitstring2.substring(5,6); // take the first and last bit from bitstring
		String Sbox2colunmstring = Bitstring2.substring(1, 5); //take the rest 4 bits except first and last bit
		int Sbox2row = Integer.valueOf(Sbox2rowstring, 2); //convert binary string to decimal integer to get row
		int Sbox2column = Integer.valueOf(Sbox2colunmstring, 2); //convert binary string to decimal integer to get column
		
		System.out.println("S-box2 row:"+Sbox2row);
		System.out.println("S-box2 column:"+Sbox2column);
		
		int Sbox2value = Sbox2[Sbox2row][Sbox2column]; //take value from S-box 2
		String Sbox2string = toFullBinaryString(Sbox2value); // convert decimal integer to binary string
		
		System.out.println("The expanded 4 bits bitstring part2 coming from S-box2 is "+Sbox2string+"("+Sbox2value+")");
		
		/****************Combine two string*****************/
		Sboxstring = Sbox1string+Sbox2string;
		System.out.println("The new 8 bits bitstring coming from S-box is "+Sboxstring);
	}
	
	// convert integer to binary string with omitted 0 in the header part.
	public static String toFullBinaryString(int num) {
        char[] chs = new char[4];
        for(int i = 0; i < 4; i++) {
            chs[4 - 1 - i] = (char)((num >> i & 1) + '0');
        }
        return new String(chs);
    }
}

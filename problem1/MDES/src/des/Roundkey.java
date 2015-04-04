package des;

public class Roundkey {
	
	public String Key;
	public int[] Pi = {23,24,21,12,13,22,8,16,18,19,20,1,15,3,5,10,9,6,2,14,11,7,17,4}; // permutation table pi
	public char[] Charkey;
	public char[] Newkey = new char[24];
	
	public Roundkey(){
		System.out.println("The round key function was created.");
	}
	
	// set a key
	public void Setkey(String key){
		this.Key = key;
		Charkey = Key.toCharArray();
		System.out.println("Key is "+key);
	}
	
	// get a round key using the key
	public String Getroundkey(){
		String Roundkey = Key.substring(0, 12);
		System.out.println("Round Key is "+Roundkey);
		return Roundkey;
	}

	// get a new key using the permutation table
	public String Getnewkey(){
		int i,j;
		for(i=0;i<24;i++){
			j = (Pi[i]-1);
			Newkey[j] = Charkey[i];
		}
		
		String result = new String(Newkey);
		System.out.println("New Key is "+result);
		return result;
	}
}

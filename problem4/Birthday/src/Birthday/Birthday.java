package Birthday;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

class BirthdayAttack {
    private static final String Msg = "cryptography is an important tool for network security.  but there are other issues for network security.";

    public static void main(String[] args) {

      
        String in = new String("cryptography is an important tool for network security.  but there are other issues for network security."); 
        System.out.println("Input");
        System.out.println("plaintext is:"+in);


        String[] varMsgs = getEnoughVariations(in, MDES.BLOCK_SIZE);
        ArrayList<Message> myList = new ArrayList<Message>(varMsgs.length);
        for (int i = 0; i < varMsgs.length; i++) {

            int[] code = MDES.txtToCode(varMsgs[i].toCharArray());

            int[] hash = Hash.computeHash(code);

            myList.add(new Message(varMsgs[i],IOControl.intBitsToStrBits(hash)));
        }


        System.out.println("Hash Message");
        for (Message r : myList) {
            System.out.println(r.getMessage());
            System.out.println(r.getHash());
        }


        System.out.println("message");
        System.out.println(Msg);
       // System.out.println("---- Now try 1000 times ----");
        for (int j = 0; j < 5000; j++) {
            String v = genVariation(Msg); 
            int[] hash = Hash.computeHash(MDES.txtToCode(v.toCharArray()));
            String h = IOControl.intBitsToStrBits(hash);
            int index = findHashInList(h, myList);
            if (index != -1) {
                System.out.println("**********************************************************************************************************************************");
                System.out.println("Hash number " + index);
                System.out.println("hash: " + h); 
                System.out.println("authentic:");
                System.out.println(myList.get(index).getMessage()); 
                System.out.println("message:");
                System.out.println(v); 
            }
        }
    }


    private static int findHashInList(String hash, ArrayList<Message> list) {
        int index = -1;
        for (int i = 0; i < list.size(); i++) {
            Message m = list.get(i);
            if (m.getHash().equals(hash)) {
                return i;
            }
        }

        return index;
    }


    private static String[] getEnoughVariations(String msg, int m) {
        int n = (int)Math.pow(2.0, (m / 2.0)); 
        String[] r = new String[n];
        for (int i = 0; i < n; i++) {
            r[i] = genVariation(msg);
        }
        return r;
    }

    private static String genVariation(String msg) {
        StringBuilder sb = new StringBuilder(msg);
        for (int j = 0; j < 4; j++) {
            int randomIndex = (int)(Math.random() * 32);
            int randomCode = (int)(Math.random() * 32);
            sb.setCharAt(randomIndex, MDES.intToChar(randomCode));
        }
        return sb.toString();
    }
}

class Message {
    private String msg;       
    private String hash;        

    public Message(String s, String h) {
        msg = s;
        hash = h;
    }

    public String getMessage() {
        return msg;
    }

    public String getHash() {
        return hash;
    }
}

class MessageComparator implements Comparator<Message> {
    public int compare(Message o1, Message o2) {
        return o1.getHash().compareTo(o2.getHash());
    }
}


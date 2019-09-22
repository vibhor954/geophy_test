package utils;

public class DecodeUtil {
	
	private DecodeUtil(){
		
	}


    public static String decode(String encodedText) {
        int len = encodedText.length();
        char[] encrypted = new char[len];
        char[] decrypted = new char[len];
        for (int i = 0; i < len; i++) {
            encrypted[i] = encodedText.charAt(i);
            encrypted[i] -= 7;
            decrypted[i] = encrypted[i];
        }
        return String.valueOf(decrypted);


    }
}


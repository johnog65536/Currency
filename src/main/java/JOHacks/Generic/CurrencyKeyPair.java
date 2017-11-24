package JOHacks.Generic;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

public class CurrencyKeyPair {
	private KeyPair keypair;
	private String label;
	private double balance;
		
	public CurrencyKeyPair(String ipLabel) throws NoSuchAlgorithmException {
		balance = 0;
		label=ipLabel;
		
		keypair = CryptoUtils.getKeyPair();
	}

	public String getPubKeyAsHashedString() {
		return CryptoUtils.calcHash(keypair.getPublic().toString());
	}
	
	public String getPubKeyAsString() {
		byte[] encodedKey= keypair.getPublic().getEncoded();
		String base64Encoded = CryptoUtils.utfToBase64(encodedKey);
		return base64Encoded;
	}
	
	public String toString() {
        String pubKey=keypair.getPublic().toString();
		return "KeyPair: "+ getLabel()+ "\n"+pubKey;
	}
	
	public String getLabel() {return label;}
	
	public KeyPair getKeyPair() {return keypair;}
	
}

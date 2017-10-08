package JOHacks.Generic;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class CurrencyKeyPair {
	private KeyPair keypair;
	private String label;
	private double balance;
	
	public CurrencyKeyPair(String ipLabel) {
		balance = 0;
		label=ipLabel;
		
		KeyPairGenerator keyGen=null;
		try {
			keyGen = KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        keyGen.initialize(512);
        keypair  = keyGen.genKeyPair();

	}
	
	public String toString() {
        //String priKey=keypair.getPrivate().toString();
        String pubKey=keypair.getPublic().toString();
        
		return "KeyPair: "+ getLabel()+ "\n"+pubKey;
	}
	
	
	public String getLabel() {return label;}
	
}

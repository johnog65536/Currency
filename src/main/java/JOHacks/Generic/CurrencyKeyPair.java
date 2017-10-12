package JOHacks.Generic;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Base64;
import java.util.Formatter;

import sun.misc.BASE64Encoder;

public class CurrencyKeyPair {
	private KeyPair keypair;
	private String label;
	private double balance;
	
	

	
	public CurrencyKeyPair(String ipLabel) throws NoSuchAlgorithmException {
		balance = 0;
		label=ipLabel;
		
		KeyPairGenerator keyGen=null;
		keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(512);
        keypair  = keyGen.genKeyPair();

	}

	public String getPubKeyAsHashedString() {
		return CryptoUtils.calcHash(keypair.getPublic().toString());
	}
	
	public String getPubKeyAsString() {
		byte[] encodedKey= keypair.getPublic().getEncoded();
		String base64Encoded = CryptoUtils.utfToBase64(encodedKey);
		return base64Encoded;
		//return keypair.getPublic().toString();
	}
	
	public String toString() {
        //String priKey=keypair.getPrivate().toString();
        String pubKey=keypair.getPublic().toString();
        
		return "KeyPair: "+ getLabel()+ "\n"+pubKey;
	}
	
	
	public String getLabel() {return label;}
	
	public KeyPair getKeyPair() {return keypair;}
	
}

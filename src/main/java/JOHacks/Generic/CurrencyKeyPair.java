package JOHacks.Generic;

import java.io.UnsupportedEncodingException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CurrencyKeyPair {
	private KeyPair keypair;
	private String label;
	private double balance;
		
	public CurrencyKeyPair(String ipLabel) throws NoSuchAlgorithmException {
		balance = 0;
		label=ipLabel;
		
		keypair = CryptoUtils.getKeyPair();
	}

	
	public CurrencyKeyPair(PortableKeyPair portableKeyPair) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeySpecException {
		balance = 0;
		label=portableKeyPair.getName();
		
		keypair = CryptoUtils.getKeyPair(portableKeyPair);
	}
	
	public String getPubKeyAsHashedString() {
		return CryptoUtils.calcHash(keypair.getPublic().toString());
	}
	
	public String getPubKeyAsString() {
		byte[] encodedKey= keypair.getPublic().getEncoded();
		String base64Encoded = CryptoUtils.utfToBase64(encodedKey);
		return base64Encoded;
	}

	public String getPrivKeyAsString() {
		byte[] encodedKey= keypair.getPrivate().getEncoded();
		String base64Encoded = CryptoUtils.utfToBase64(encodedKey);
		return base64Encoded;
	}
	
	
	public String toString() {
        String pubKey=keypair.getPublic().toString();
		return "KeyPair: "+ getLabel()+ "\n"+pubKey;
	}
	
	public String getLabel() {return label;}
	
	public KeyPair getKeyPair() {return keypair;}
	
	public String getJSONRepresentation() {
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		
    	PortableKeyPair portableKeyPair=new PortableKeyPair(getPubKeyAsString(),getPrivKeyAsString(),label);
    	return gson.toJson(portableKeyPair);
	}
	

}

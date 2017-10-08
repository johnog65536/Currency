package JOHacks.Generic;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;

import sun.misc.BASE64Encoder;

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
	
	public final String HASH_ALGORITHM="SHA1WithRSA";
	public final String BYTES_ENCODING="UTF8";
	
	private byte[] getBytes(String stringToCovert) throws UnsupportedEncodingException {
		return stringToCovert.getBytes(BYTES_ENCODING);
	}
	
	public byte[]  signMessage(String thingToSign) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, UnsupportedEncodingException {
		byte[] data =getBytes(thingToSign);
		
		Signature sig = Signature.getInstance(HASH_ALGORITHM);
        sig.initSign(keypair.getPrivate());
        
        sig.update(data);
        byte[] signatureBytes = sig.sign();        
		
        //return new BASE64Encoder().encode(signatureBytes);
        return signatureBytes;
	}
	
	public boolean verifySignature(String messageToVerify, byte[] messageSignature) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, SignatureException {		
		Signature sig = Signature.getInstance(HASH_ALGORITHM);
		sig.initVerify(keypair.getPublic());
	
		byte[] messageToVerifyBytes=getBytes(messageToVerify);
		//byte[] signatureBytes=getBytes(messageSignature);
		byte[] signatureBytes=messageSignature;
		
        sig.update(messageToVerifyBytes);
                
        return sig.verify(signatureBytes);
	}
	
	public String toString() {
        //String priKey=keypair.getPrivate().toString();
        String pubKey=keypair.getPublic().toString();
        
		return "KeyPair: "+ getLabel()+ "\n"+pubKey;
	}
	
	
	public String getLabel() {return label;}
	
}

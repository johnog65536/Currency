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
	
	
	public final String HASH_ALGORITHM="SHA1WithRSA";
	public final String BYTES_ENCODING="UTF-8";
	
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
	
	private byte[] getBytes(String stringToCovert) throws UnsupportedEncodingException {
		return stringToCovert.getBytes(BYTES_ENCODING);
	}
	
	public String signMessage(String thingToSign) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, UnsupportedEncodingException {
		byte[] data =getBytes(thingToSign);
			
		Signature sig = Signature.getInstance(HASH_ALGORITHM);
        sig.initSign(keypair.getPrivate());
        
        sig.update(data);
        byte[] signatureBytes = sig.sign();        
       
		// Signature returns a UTF-8 Byte Array, need to get that into Base64 for later manipulation
        String signatureAsUTF8 = Base64.getEncoder().encodeToString(signatureBytes);        
        
        return signatureAsUTF8;
	}
	
	public boolean verifySignature(String messageToVerify, String messageSignature) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, SignatureException {		
		Signature sig = Signature.getInstance(HASH_ALGORITHM);
		sig.initVerify(keypair.getPublic());
		
		//messageSignature arrives as a base 64 encoded string, need to get it into UTF-8 byte array
        byte[] srcBytes=getBytes(messageSignature);
        byte[] signatureBytes = Base64.getDecoder().decode(srcBytes);
		
		byte[] messageToVerifyBytes=getBytes(messageToVerify);
			
        sig.update(messageToVerifyBytes);
                
        return sig.verify(signatureBytes);
	}
	
	public String getPubKeyAsString() {
		return CryptoUtils.calcHash(keypair.getPublic().toString());
	}
	

	
	public String toString() {
        //String priKey=keypair.getPrivate().toString();
        String pubKey=keypair.getPublic().toString();
        
		return "KeyPair: "+ getLabel()+ "\n"+pubKey;
	}
	
	
	public String getLabel() {return label;}
	
}

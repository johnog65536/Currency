package JOHacks.Generic;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Formatter;

public class CryptoUtils {
	public final static String HASH_ALGORITHM = "SHA1WithRSA";
	public final static String KEY_TYPE = "RSA";
	public final static String BYTES_ENCODING = "UTF-8";

	public static String calcHash(String toHash) {
		try {
			// System.out.println("++++ hashing "+toHash);

			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(toHash.getBytes("UTF-8"));
			return byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static boolean verifySignature(String messageToVerify, String messageSignature, CurrencyKeyPair keypair)
			throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, SignatureException {

		PublicKey pubKey = keypair.getKeyPair().getPublic();

		return verifySignature(messageToVerify, messageSignature, pubKey);
	}

	public static boolean verifySignature(String messageToVerify, String messageSignature, PublicKey pubKey)
			throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, SignatureException {
		Signature sig = Signature.getInstance(HASH_ALGORITHM);
		sig.initVerify(pubKey);

		// messageSignature arrives as a base 64 encoded string, need to get it into
		// UTF-8 byte array
		byte[] srcBytes = getBytes(messageSignature);
		byte[] signatureBytes = Base64ToUTF(srcBytes);

		byte[] messageToVerifyBytes = getBytes(messageToVerify);

		sig.update(messageToVerifyBytes);

		return sig.verify(signatureBytes);
	}

	private static byte[] getBytes(String stringToCovert) throws UnsupportedEncodingException {
		return stringToCovert.getBytes(BYTES_ENCODING);
	}

	public static String signMessage(String thingToSign, CurrencyKeyPair keypair)
			throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, UnsupportedEncodingException {
		byte[] data = getBytes(thingToSign);

		Signature sig = Signature.getInstance(HASH_ALGORITHM);
		sig.initSign(keypair.getKeyPair().getPrivate());

		sig.update(data);
		byte[] signatureBytes = sig.sign();

		// Signature returns a UTF-8 Byte Array, need to get that into Base64 for later
		// manipulation
		String signatureAsUTF8 = utfToBase64(signatureBytes);

		return signatureAsUTF8;
	}

	public static String utfToBase64(byte[] bytes) {
		String rtnVal = Base64.getEncoder().encodeToString(bytes);
		return rtnVal;
	}
	
	public static byte[] Base64ToUTF(byte[] srcBytes) {
		byte[] decodedBytes = Base64.getDecoder().decode(srcBytes);
		return decodedBytes;
	}
	
	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	public static boolean validateKeyPair(PublicKey pubKey, CurrencyKeyPair keyPair)
			throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, UnsupportedEncodingException {
		String THING_TO_SIGN = "Something";
		String messageSignature = signMessage(THING_TO_SIGN, keyPair);
		boolean legit = verifySignature(THING_TO_SIGN, messageSignature, pubKey);
		
		if (legit==false) throw new InvalidKeyException("Keys dont match");
		
		String pubKeyAsString=utfToBase64(pubKey.getEncoded());
		String otherPubKeyAsString=keyPair.getPubKeyAsString();
		
		if ( pubKeyAsString.equals(otherPubKeyAsString) == false) throw new InvalidKeyException("Keys dont match");
		
		return true;
	}


	
	public static PublicKey generatePubKey (String inputString) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException {

		byte[] encodedByteKey = getBytes(inputString);
		byte[] byteKey=Base64ToUTF(encodedByteKey);
		X509EncodedKeySpec X509publicKey = new X509EncodedKeySpec(byteKey); 
		KeyFactory kf = KeyFactory.getInstance(KEY_TYPE); 
		
		return kf.generatePublic(X509publicKey);  
		
	}
}

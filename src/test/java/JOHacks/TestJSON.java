package JOHacks;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import JOHacks.Generic.CurrencyKeyPair;
import JOHacks.Generic.PortableKeyPair;
import JOHacks.Generic.Transaction;
import JOHacks.Generic.TransactionInput;
import JOHacks.Generic.TransactionOutput;
import JOHacks.Wallet.Wallet;

public class TestJSON {

	@Test
	public void testConvertKeysToJSON() throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeySpecException {
		final GsonBuilder builder = new GsonBuilder();
		final Gson gson = builder.create();
		
        final Wallet wallet = new Wallet();
    	final String genesysKeyLabel = "Genesys Key Pair";    	
    	final CurrencyKeyPair genesysKeyPair = wallet.GenerateKeyPair(genesysKeyLabel);    	
    	final String jsonString = genesysKeyPair.getJSONRepresentation();
    	
    	log ("success on conv to json:"+jsonString);
	}


	@Test
	public void testConvertKeysFromJSON() throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeySpecException {
		final GsonBuilder builder = new GsonBuilder();
		final Gson gson = builder.create();
		
		final String jsonString="{\"name\":\"Genesys Key Pair\",\"pubKey\":\"MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAOvmgWv7vdBNW+bv4OeFzlfYSc7fMNtRxUx/mfbBBuZ9tI4hlWevDXf/cB7QAscp3P4APSb7gi8jdqIoc1oLFqMCAwEAAQ\u003d\u003d\",\"privKey\":\"MIIBUwIBADANBgkqhkiG9w0BAQEFAASCAT0wggE5AgEAAkEA6+aBa/u90E1b5u/g54XOV9hJzt8w21HFTH+Z9sEG5n20jiGVZ68Nd/9wHtACxync/gA9JvuCLyN2oihzWgsWowIDAQABAkB/nAXO4DNiBzb7yi+jjqJ8qqfinKHVQnq0UyJ6dRjCfZvJyEKSyotq25tALK9Gkz4vk5OU3NTAsiWNOl1FZebBAiEA+07PNflHeDTF/y6FOFnn349p55VRJ+0lktFHSsK1y0sCIQDwTg1lGXLGLqMVhZ+gm6CjQ6cX1yhcwnnuVZLktxUzCQIgHxBl0rEmoXg8hUeV1hpe7CaJG8Q8TOgmfdh6rIYW/LMCIHTMmzoeimjFVkMptZKs0gFI6rhtvZKIfBdZxJIiYs1xAiBriEVrrbW3x2PFNaHk1J90jSiMQ38KJsZrdcBnnJJAWw\u003d\u003d\"}";
		final PortableKeyPair portableKeyPair = gson.fromJson(jsonString, PortableKeyPair.class); 
		
		final Wallet wallet = new Wallet();
		
		final CurrencyKeyPair genesysKeyPair = wallet.GenerateKeyPair(portableKeyPair);
		log ("sucess! (re)generated a keypar from JSON repo");
	}
	
    //todo unbodge this
	final private boolean LOGGING_ON = true;
    private void log(String tolog) {
    	if (LOGGING_ON)
    		System.out.println(tolog);
    }
}


/*
	String brand;
	int doors;
	
	
		String json = "{\"brand\":\"Jeep\", \"doors\": 3}";		
		TestJSON test = gson.fromJson(json, TestJSON.class);
		log (test.brand);
		log (""+test.doors);
*/
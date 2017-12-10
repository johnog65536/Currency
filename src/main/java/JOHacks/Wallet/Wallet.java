package JOHacks.Wallet;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.HashMap;

import JOHacks.Generic.CurrencyKeyPair;
import JOHacks.Generic.PortableKeyPair;
import JOHacks.Generic.Transaction;
import JOHacks.Generic.TransactionOutput;

public class Wallet {

	private HashMap<String,CurrencyKeyPair> keyPairs = null;

	
	public Wallet() {
		keyPairs=new HashMap<String,CurrencyKeyPair>();
	}
	
	
	public CurrencyKeyPair GenerateKeyPair(String name) throws NoSuchAlgorithmException {
		CurrencyKeyPair pair = new CurrencyKeyPair(name);
		keyPairs.put(name,pair);
		return pair;
	}
	
	
	public CurrencyKeyPair GenerateKeyPair(PortableKeyPair portableKeyPair) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeySpecException {
		CurrencyKeyPair pair = new CurrencyKeyPair(portableKeyPair);
		keyPairs.put(portableKeyPair.getName(),pair);
		return pair;
	}
	
	public CurrencyKeyPair getKeyPair(String name) {
		return keyPairs.get(name);
	}
	
	
	private void UpdateBalances() {
		// get a miner
		// get block chain
		//scan for all keys in the wallet		
	}
	
	private void SendTransaction(Transaction transaction)
	{
		// get a miner
		// send
	}

	
    public TransactionOutput createTxnOutput(String keyLabel, int index, double value) throws NoSuchAlgorithmException {
    	CurrencyKeyPair outputKeyPair = getKeyPair(keyLabel);
    	
    	// bit hacky - todo
    	if (outputKeyPair==null) 
    		outputKeyPair = GenerateKeyPair(keyLabel);
    	
    	String pubKeyString = outputKeyPair.getPubKeyAsString();    	
    	
    	return new TransactionOutput(index,value,pubKeyString);    	
    }
}

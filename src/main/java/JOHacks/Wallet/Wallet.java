package JOHacks.Wallet;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

import JOHacks.Generic.CurrencyKeyPair;
import JOHacks.Generic.Transaction;

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
		
}

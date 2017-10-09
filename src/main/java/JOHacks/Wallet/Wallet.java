package JOHacks.Wallet;

import java.util.ArrayList;

import JOHacks.Generic.CurrencyKeyPair;
import JOHacks.Generic.Transaction;

public class Wallet {

	private ArrayList<CurrencyKeyPair> keyPairs = null;

	
	public Wallet() {
		keyPairs=new ArrayList<CurrencyKeyPair>();
	}
	
	
	public CurrencyKeyPair GenerateKeyPair(String name) {
		CurrencyKeyPair pair = new CurrencyKeyPair(name);
		keyPairs.add(pair);
		return pair;
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

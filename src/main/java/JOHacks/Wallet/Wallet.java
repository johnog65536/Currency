package JOHacks.Wallet;

import java.util.ArrayList;

import JOHacks.Generic.KeyPair;
import JOHacks.Generic.Transaction;

public class Wallet {

	private ArrayList<KeyPair> keyPairs = null;

	
	public Wallet() {
		keyPairs=new ArrayList<KeyPair>();
	}
	
	
	public KeyPair GenerateKeyPair() {
		KeyPair pair = new KeyPair();
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

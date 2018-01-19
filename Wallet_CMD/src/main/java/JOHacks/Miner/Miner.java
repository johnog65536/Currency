package JOHacks.Miner;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Formatter;

import JOHacks.Generic.Transaction;

public class Miner {
	private BlockChain blockChain;
	private ArrayList<Miner> miningNetwork;
	private String address;
	//private Key pubKey;
	
	//public double getBalance(Key pubKey) {return 0;}

	public void receiveTransactionFromWallet(Transaction transaction) {}
	public void receiveTransactionFromNetwork(Transaction transaction) {}

	public void sendTransactionToNetwork(Transaction transaction) {}
	
	public void sendBlockToNetwork(Block block) {}
	public void receiveBlockFromNetwork(Block block) {}
	
	public void RegisterMiner(Miner miner) {}
	
	public void mine(ArrayList<Transaction> transactions) {}

}

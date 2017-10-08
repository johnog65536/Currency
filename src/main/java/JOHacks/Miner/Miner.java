package JOHacks.Miner;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Formatter;

import JOHacks.Generic.Key;
import JOHacks.Generic.Transaction;

public class Miner {
	private BlockChain blockChain;
	private ArrayList<Miner> miningNetwork;
	private String address;
	private Key pubKey;
	
	public double getBalance(Key pubKey) {return 0;}

	public void receiveTransactionFromWallet(Transaction transaction) {}
	public void receiveTransactionFromNetwork(Transaction transaction) {}

	public void sendTransactionToNetwork(Transaction transaction) {}
	
	public void sendBlockToNetwork(Block block) {}
	public void receiveBlockFromNetwork(Block block) {}
	
	public void RegisterMiner(Miner miner) {}
	
	public void mine(ArrayList<Transaction> transactions) {}
	
	public String calcHash(String toHash) {
	    try
	    {
	        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
	        crypt.reset();
	        crypt.update(toHash.getBytes("UTF-8"));
	        return byteToHex(crypt.digest());
	    }
	    catch(NoSuchAlgorithmException e)
	    {
	        e.printStackTrace();
	    }
	    catch(UnsupportedEncodingException e)
	    {
	        e.printStackTrace();
	    }
	    return "";
	}
	
	private static String byteToHex(final byte[] hash)
	{
	    Formatter formatter = new Formatter();
	    for (byte b : hash)
	    {
	        formatter.format("%02x", b);
	    }
	    String result = formatter.toString();
	    formatter.close();
	    return result;
	}
}

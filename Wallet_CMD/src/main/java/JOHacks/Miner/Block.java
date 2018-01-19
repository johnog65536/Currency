package JOHacks.Miner;

import java.security.Timestamp;
import java.util.ArrayList;

import JOHacks.Generic.Transaction;

public class Block {
	private int ID;
	private int previousID;
	private String hash;
	private ArrayList<Transaction> transactions;
	private ArrayList<Data> data;
	//private Key minedBy;
	private Timestamp timestamp;
	
}

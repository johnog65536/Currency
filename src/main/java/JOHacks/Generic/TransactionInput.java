package JOHacks.Generic;

public class TransactionInput {
	//private Key pubKey;
	
	private String previousTransactionHash;
	private int previousTransactionIndex;
	private int validationLength;
	
	public TransactionInput(String previousTransactionHash, int previousTransactionIndex) {
		this.previousTransactionHash = previousTransactionHash;
		this.previousTransactionIndex = previousTransactionIndex;
	}
	
	private String getTransactionHash() {
		return "";
	}
	
	public String getOutputString() {
		return "   Input " + previousTransactionHash + " " + previousTransactionIndex;
	}
}

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
	
	
	// TODO this feels really dodgy
	public String getTransactionHash() {
		return CryptoUtils.calcHash(previousTransactionHash+previousTransactionIndex);
	}
	
	public String getOutputString() {
		return "   Input " + previousTransactionHash + " " + previousTransactionIndex;
	}
}

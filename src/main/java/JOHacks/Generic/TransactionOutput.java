package JOHacks.Generic;

public class TransactionOutput {
	
	private int index;
	private double value;
	private int lenPubKey;
	private String pubKey;
	
	public TransactionOutput(int index, double value, String pubKey) {
		this.index = index;
		this.value = value;
		this.pubKey = pubKey;
		
		lenPubKey=pubKey.length();
	}
	
	public String getOutputString() {
		return "   Output " + index + " " + value + " " + pubKey;
	}
}

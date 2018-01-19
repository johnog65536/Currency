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
				
		return "   Output: Idx:" + index + " Value:" + value + " PubKey:" + pubKey;
	}
	
	public int getIndex() {return index;}
	public String getHash() {return CryptoUtils.calcHash(getOutputString());}
	public String getPubKey() {return pubKey;}
	public double getValue() {return value;}
}

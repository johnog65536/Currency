package JOHacks.Generic;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Formatter;

public class Transaction {


	private ArrayList<TransactionInput> inputs;
	private ArrayList<TransactionOutput> outputs;
	private Timestamp timestamp;
	
	public Transaction (ArrayList<TransactionInput> ipInputs, ArrayList<TransactionOutput> ipOutputs) {
		inputs=ipInputs;
		outputs=ipOutputs;
	}
	
	public String getOutputString() {
		
		StringBuffer ioBuffer=new StringBuffer();
		for (TransactionInput input:inputs) {
			ioBuffer.append(input.getOutputString());
			ioBuffer.append("\n");
		}
		for (TransactionOutput output:outputs) {
			ioBuffer.append(output.getOutputString());
			ioBuffer.append("\n");
		}
		
		StringBuffer returnVal=new StringBuffer();
		returnVal.append("Transaction:");
		returnVal.append(CryptoUtils.calcHash(ioBuffer.toString()));
		returnVal.append("\n");
		returnVal.append(ioBuffer);
		
		return returnVal.toString();
	}
	
	
	public TransactionInput getInput(int index) {
		return inputs.get(index);
	}

	public TransactionOutput getOutput(int index) {
		return outputs.get(index);
	}
}

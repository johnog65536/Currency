package JOHacks.Generic;

import java.security.Timestamp;
import java.util.ArrayList;

public class Transaction {

	private String id;
	private ArrayList<TransactionElement> inputs;
	private ArrayList<TransactionElement> outputs;
	private Timestamp timestamp;
	
	public Transaction (ArrayList<TransactionElement> ipInputs, ArrayList<TransactionElement> ipOutputs) {
		inputs=ipInputs;
		outputs=ipOutputs;
	}
}

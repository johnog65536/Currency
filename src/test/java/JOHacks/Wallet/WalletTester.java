package JOHacks.Wallet;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.ArrayList;

import JOHacks.AppTest;
import JOHacks.Generic.CurrencyKeyPair;
import JOHacks.Generic.Transaction;
import JOHacks.Generic.TransactionInput;
import JOHacks.Generic.TransactionOutput;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class WalletTester     extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public WalletTester( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( WalletTester.class );
    }

	
    public void testCreateWallet()
    {
        Wallet wallet = new Wallet();
    }
    
    public void testCreateWalletWithKeys()
    {
        Wallet wallet = new Wallet();

    	CurrencyKeyPair keypair = wallet.GenerateKeyPair("My First KeyPair");    
    	
    }

    public void testCreateWalletBasicSignMessage() throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, SignatureException
    {
        Wallet wallet = new Wallet();
    	CurrencyKeyPair keypairFrom = wallet.GenerateKeyPair("Paying From");
    	
    	final String STRING_TO_SIGN= "Thing being signed";
    	final String RANDOM_STRING = "Some Random String";
    	
    	final String messageSignature = keypairFrom.signMessage(STRING_TO_SIGN);
    	
    	boolean legit = keypairFrom.verifySignature(STRING_TO_SIGN, messageSignature);
    	assertTrue("Good Signature Passed",legit);  	
    	
    	boolean notlegit = keypairFrom.verifySignature(RANDOM_STRING, messageSignature);
    	assertFalse("Bad Signature Failed",notlegit);
    }
    
    public void testCreateWalletCreateTransaction() throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, SignatureException
    {
        Wallet wallet = new Wallet();
    	CurrencyKeyPair keypairFrom = wallet.GenerateKeyPair("Paying From");
    	CurrencyKeyPair outputKeyPair1 = wallet.GenerateKeyPair("Paying to 1");
    	CurrencyKeyPair outputKeyPair2 = wallet.GenerateKeyPair("Paying to 2");
    	
    	final String STRING_TO_SIGN= "pay <key> <value>";    	
    	final String messageSignature = keypairFrom.signMessage(STRING_TO_SIGN);
    	
    	Transaction transaction1 = createRootTransaction(outputKeyPair1,outputKeyPair2);
    	
    	System.out.println("testCreateWalletCreateTransaction() vvvv");
    	System.out.println(transaction1.getOutputString());
    	
    	// this txn needs its inputs to be hung off the outputs of the previous txn
    	String inputTxnHash1=transaction1.getInput(0).getTransactionHash();
    	String inputTxnHash2=transaction1.getInput(1).getTransactionHash();
    	double outputValue1=4.5;
    	double outputValue2=5.5;
    	Transaction transaction2 = createRealTransaction ( inputTxnHash1,  inputTxnHash2,  outputKeyPair1,outputValue1,  outputKeyPair1, outputValue2);
    	System.out.println(transaction2.getOutputString());
    	
    	System.out.println("testCreateWalletCreateTransaction() ^^^^");
    }
    

    private Transaction createRealTransaction(String inputTxnHash1,String inputTxnHash2, 
    										  CurrencyKeyPair outputKeyPair1, double outputValue1,
    										  CurrencyKeyPair outputKeyPair2, double outputValue2  ) {
    	
    	TransactionInput input1 = new TransactionInput("no Txn",-1);
    	TransactionInput input2 = new TransactionInput("no Txn",-2);
    	ArrayList<TransactionInput> inputs= new ArrayList<TransactionInput>();
    	inputs.add(input1);
    	inputs.add(input2);
    	
    	TransactionOutput output1 = new TransactionOutput(0,10,outputKeyPair1.getPubKeyAsString());
    	TransactionOutput output2 = new TransactionOutput(0,4,outputKeyPair2.getPubKeyAsString());
    	ArrayList<TransactionOutput> outputs= new ArrayList<TransactionOutput>();
    	outputs.add(output1);
    	outputs.add(output2);
    	
    	Transaction transaction = new Transaction(inputs,outputs);
    	
    	return transaction;
    }
    
    private Transaction createRootTransaction(CurrencyKeyPair outputKey1 ,CurrencyKeyPair outputKey2) {
    	TransactionInput input1 = new TransactionInput("no Txn",-1);
    	TransactionInput input2 = new TransactionInput("no Txn",-2);
    	TransactionInput input3 = new TransactionInput("no Txn",-3);   	
    	ArrayList<TransactionInput> inputs= new ArrayList<TransactionInput>();
    	inputs.add(input1);
    	inputs.add(input2);
    	inputs.add(input3);
    	
    	TransactionOutput output1 = new TransactionOutput(0,8,outputKey1.getPubKeyAsString());
    	TransactionOutput output2 = new TransactionOutput(0,6,outputKey2.getPubKeyAsString());
    	ArrayList<TransactionOutput> outputs= new ArrayList<TransactionOutput>();
    	outputs.add(output1);
    	outputs.add(output2);
    	
    	Transaction transaction = new Transaction(inputs,outputs);
    	
    	return transaction;
    }
    
	public final String BYTES_ENCODING="UTF-8";
	private byte[] getBytes(String stringToCovert) throws UnsupportedEncodingException {
		return stringToCovert.getBytes(BYTES_ENCODING);
	}
}

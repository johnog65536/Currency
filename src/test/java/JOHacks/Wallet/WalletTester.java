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
    	CurrencyKeyPair firstRightOutput = wallet.GenerateKeyPair("Paying to 1");
    	CurrencyKeyPair secondRightOutput = wallet.GenerateKeyPair("Paying to 2");
    	CurrencyKeyPair keypairToWrong1 = wallet.GenerateKeyPair("Not Paying to 1");
    	CurrencyKeyPair keypairToWrong2 = wallet.GenerateKeyPair("Not Paying to 2");
    	
    	final String STRING_TO_SIGN= "pay <key> <value>";    	
    	final String messageSignature = keypairFrom.signMessage(STRING_TO_SIGN);
    	
    	Transaction transaction1 = createRootTransaction(firstRightOutput,secondRightOutput);
    	System.out.println(transaction1.getOutputString());
    	
    	Transaction transaction2 = createTransaction(keypairToWrong1,keypairToWrong2);
    	System.out.println(transaction2.getOutputString());
    }
    

    private Transaction createTransaction(CurrencyKeyPair outputKey1 ,CurrencyKeyPair outputKey2) {
    	TransactionInput input1 = new TransactionInput("no Txn",-1);
    	TransactionInput input2 = new TransactionInput("no Txn",-2);
    	ArrayList<TransactionInput> inputs= new ArrayList<TransactionInput>();
    	inputs.add(input1);
    	inputs.add(input2);
    	
    	TransactionOutput output1 = new TransactionOutput(0,10,outputKey1.getPubKeyAsString());
    	TransactionOutput output2 = new TransactionOutput(0,4,outputKey2.getPubKeyAsString());
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

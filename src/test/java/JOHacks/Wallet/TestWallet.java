package JOHacks.Wallet;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;

import JOHacks.TestApp;
import JOHacks.Generic.CryptoUtils;
import JOHacks.Generic.CurrencyKeyPair;
import JOHacks.Generic.Transaction;
import JOHacks.Generic.TransactionInput;
import JOHacks.Generic.TransactionOutput;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestWallet     extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public TestWallet( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( TestWallet.class );
    }

	
    public void testCreateWallet()
    {
        Wallet wallet = new Wallet();
    }
    
    /** check a wallet can be created, and a key pair added
     * 
     * @throws NoSuchAlgorithmException
     */
    public void testCreateWalletWithKeys() throws NoSuchAlgorithmException
    {
        Wallet wallet = new Wallet();
    	CurrencyKeyPair keypair = wallet.GenerateKeyPair("My First KeyPair");    
    }

    /** check a message can be signed, and that the singature can be verified (positive) and that a bad signature is spotten 
     * 
     * @throws UnsupportedEncodingException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws SignatureException
     */
    public void testCreateWalletBasicSignMessage() throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, SignatureException
    {
        Wallet wallet = new Wallet();
    	CurrencyKeyPair keypairFrom = wallet.GenerateKeyPair("Paying From");
    	
    	final String STRING_TO_SIGN= "Thing being signed";
    	final String RANDOM_STRING = "Some Random String";    	
    	final String messageSignature = CryptoUtils.signMessage(STRING_TO_SIGN,keypairFrom);
    	
    	boolean legit = CryptoUtils.verifySignature(STRING_TO_SIGN, messageSignature,keypairFrom);
    	assertTrue("Good Signature Passed",legit);  	
    	
    	boolean notlegit = CryptoUtils.verifySignature(RANDOM_STRING, messageSignature,keypairFrom);
    	assertFalse("Bad Signature Failed",notlegit);
    }
    
    /** Check that a root transaction can be created and a subsequent one hung off one of the root transaction's outputs
     * 
     * @throws UnsupportedEncodingException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws SignatureException
     * @throws InvalidKeySpecException
     */
    public void testCreateTransaction() throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, SignatureException, InvalidKeySpecException
    {
        Wallet wallet = new Wallet();
    	CurrencyKeyPair keypairFrom = wallet.GenerateKeyPair("Paying From");
    	CurrencyKeyPair outputKeyPair0 = wallet.GenerateKeyPair("Paying to 0");
    	CurrencyKeyPair outputKeyPair1 = wallet.GenerateKeyPair("Paying to 1");
    	
    	final String STRING_TO_SIGN= "pay <key> <value>";    	
    	final String messageSignature = CryptoUtils.signMessage(STRING_TO_SIGN,keypairFrom);
    	
    	ArrayList<CurrencyKeyPair> outputKeyPairs=new ArrayList<CurrencyKeyPair>();
    	outputKeyPairs.add(outputKeyPair0);
    	outputKeyPairs.add(outputKeyPair1);
    	
    	
    	Transaction transaction0 = createRootTransaction(outputKeyPairs);
    	
    	System.out.println("testCreateWalletCreateTransaction() vvvv");
    	System.out.println(transaction0.getOutputString());
    	
    	// this txn needs its inputs to be hung off the outputs of the previous txn
    	// and needs to validate those
    	
    	double outputValue0=4.5;
    	double outputValue1=5.5;
    	Transaction transaction2 = createRealTransaction ( transaction0, outputKeyPair0,outputValue0,  outputKeyPair1, outputValue1);
    	System.out.println(transaction2.getOutputString());
    	
    	System.out.println("testCreateWalletCreateTransaction() ^^^^");
    }
    
    /** internal method which creates a transaction, hanging it off a pervious output
     * 
     * @param priorTransaction
     * @param outputKeyPair0
     * @param outputValue0
     * @param outputKeyPair1
     * @param outputValue1
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws SignatureException
     * @throws UnsupportedEncodingException
     * @throws InvalidKeySpecException
     */
    private Transaction createRealTransaction(Transaction priorTransaction,  
    										  CurrencyKeyPair outputKeyPair0, double outputValue0,
    										  CurrencyKeyPair outputKeyPair1, double outputValue1  ) 
      throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, UnsupportedEncodingException, InvalidKeySpecException{
    	
    	ArrayList<TransactionInput> inputs= new ArrayList<TransactionInput>();
    	TransactionOutput priorOutput0 = priorTransaction.getOutput(0);
    	TransactionOutput priorOutput1 = priorTransaction.getOutput(1);
    	TransactionInput input0 =new TransactionInput(priorOutput0.getHash(),priorOutput0.getIndex());
    	TransactionInput input1 =new TransactionInput(priorOutput1.getHash(),priorOutput1.getIndex());
    	inputs.add(input0);
    	inputs.add(input1);
    	
    	//check the pubKey in the inputTransaction matches that of the person initiating the spend
    	//todo make this a proper signature check
    	
    	// todo why is keypair 0 and 1 the same?!

    	CryptoUtils.validateKeyPair(CryptoUtils.generatePubKey(priorOutput0.getPubKey()),outputKeyPair0);
    	CryptoUtils.validateKeyPair(CryptoUtils.generatePubKey(priorOutput1.getPubKey()),outputKeyPair1);
    	
    	boolean thrown=false;
    	try {
    		CryptoUtils.validateKeyPair(CryptoUtils.generatePubKey(priorOutput0.getPubKey()),outputKeyPair1);
    	} catch (InvalidKeyException e) {thrown = true;}
    	assertTrue("Key validation should have failed",thrown);
    	
    	
    	TransactionOutput output0 = new TransactionOutput(0,7,outputKeyPair0.getPubKeyAsString());
    	TransactionOutput output1 = new TransactionOutput(1,7,outputKeyPair1.getPubKeyAsString());
    	ArrayList<TransactionOutput> outputs= new ArrayList<TransactionOutput>();
    	outputs.add(output0);
    	outputs.add(output1);
    	
    	Transaction transaction = new Transaction(inputs,outputs);
    	
    	return transaction;
    }
    
    /** Internal method which creates the root transaction
     * 
     * @param outputKeys ArrayList of output keys
     * @return a new Root Transaction for a blockchain
     */
    private Transaction createRootTransaction(ArrayList<CurrencyKeyPair> outputKeys) {
 	
    	ArrayList<TransactionInput> inputs= new ArrayList<TransactionInput>();
    	TransactionInput input1 = new TransactionInput("no Txn",-1);
    	inputs.add(input1);

    	ArrayList<TransactionOutput> outputs= new ArrayList<TransactionOutput>();
    	int index=0;
    	
    	for (CurrencyKeyPair outputKey: outputKeys) {
        	TransactionOutput output = new TransactionOutput(index++,5.5,outputKey.getPubKeyAsString());
        	outputs.add(output);
    	}
    	
    	return new Transaction(inputs,outputs);
    	
    }
    

}

package JOHacks.Wallet;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;

import JOHacks.AppTest;
import JOHacks.Generic.CryptoUtils;
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
    
    public void testCreateWalletWithKeys() throws NoSuchAlgorithmException
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
    	
    	final String messageSignature = CryptoUtils.signMessage(STRING_TO_SIGN,keypairFrom);
    	
    	boolean legit = CryptoUtils.verifySignature(STRING_TO_SIGN, messageSignature,keypairFrom);
    	assertTrue("Good Signature Passed",legit);  	
    	
    	boolean notlegit = CryptoUtils.verifySignature(RANDOM_STRING, messageSignature,keypairFrom);
    	assertFalse("Bad Signature Failed",notlegit);
    }
    
    public void testCreateWalletCreateTransaction() throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, SignatureException, InvalidKeySpecException
    {
        Wallet wallet = new Wallet();
    	CurrencyKeyPair keypairFrom = wallet.GenerateKeyPair("Paying From");
    	CurrencyKeyPair outputKeyPair0 = wallet.GenerateKeyPair("Paying to 0");
    	CurrencyKeyPair outputKeyPair1 = wallet.GenerateKeyPair("Paying to 1");
    	
    	final String STRING_TO_SIGN= "pay <key> <value>";    	
    	final String messageSignature = CryptoUtils.signMessage(STRING_TO_SIGN,keypairFrom);
    	
    	Transaction transaction0 = createRootTransaction(outputKeyPair0,outputKeyPair1);
    	
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
    
    private Transaction createRootTransaction(CurrencyKeyPair outputKey0 ,CurrencyKeyPair outputKey1) {
    	TransactionInput input1 = new TransactionInput("no Txn",-1);
 	
    	ArrayList<TransactionInput> inputs= new ArrayList<TransactionInput>();
    	inputs.add(input1);

    	
    	TransactionOutput output0 = new TransactionOutput(0,5.5,outputKey0.getPubKeyAsString());
    	TransactionOutput output1 = new TransactionOutput(1,4.5,outputKey1.getPubKeyAsString());
    	ArrayList<TransactionOutput> outputs= new ArrayList<TransactionOutput>();
    	outputs.add(output0);
    	outputs.add(output1);
    	
    	Transaction transaction = new Transaction(inputs,outputs);
    	
    	return transaction;
    }
    

}

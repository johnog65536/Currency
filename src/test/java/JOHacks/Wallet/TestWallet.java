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
        final Wallet wallet = new Wallet();
    	final String rootKeyPayLabel0 = "Paying to 0";
    	final String rootKeyPayLabel1 = "Paying to 1";
    	final double rootValue=10;
    	
    	final ArrayList<TransactionInput> inputs= new ArrayList<TransactionInput>();
    	final ArrayList<TransactionOutput> outputs= new ArrayList<TransactionOutput>();
    	
    	// VVVV Create Root Transaction VVVVVV    	    
    	TransactionInput input1 = new TransactionInput("no Txn",-1);
    	inputs.add(input1);
    
    	createTxnOutput(wallet,rootKeyPayLabel0,0,rootValue,outputs);
    	createTxnOutput(wallet,rootKeyPayLabel1,1,rootValue,outputs);
    	
    	final Transaction transaction0 = new Transaction(inputs,outputs);
    	// ^^^^^   Create Root Transaction ^^^^^^^
    	    	
    	System.out.println("testCreateWalletCreateTransaction() vvvv");
    	System.out.println(transaction0.getOutputString());
    	    	
    	final double paymentValue=4.5;
    	final int prevOutputIndex=0;
    	
    	final CurrencyKeyPair outputKeyPair0=wallet.getKeyPair(rootKeyPayLabel0);
    	final CurrencyKeyPair keypairTo=wallet.getKeyPair(rootKeyPayLabel1);
    	
    	Transaction transaction1 = createRealTransaction ( transaction0, prevOutputIndex,outputKeyPair0,keypairTo, paymentValue);
    	System.out.println(transaction1.getOutputString());
    	
    	final double txn2Value=1.5;
    	final int txn2prevOutputIndex=1;
    	Transaction transaction2 = createRealTransaction ( transaction1, txn2prevOutputIndex,outputKeyPair0,keypairTo, txn2Value);
    	System.out.println(transaction2.getOutputString());    	

    	// Check the create transction fails if the key doing the spend doesnt match the key on the prior transactions prior output
    	final double txn3paymentValue=0.5;
    	final int txn3prevOutputIndex=0;
    	boolean failed=false;
    	try {
    		Transaction transaction3 = createRealTransaction ( transaction2, txn3prevOutputIndex,outputKeyPair0,keypairTo, txn3paymentValue);
    		System.out.println(transaction3.getOutputString());
    	} catch(InvalidKeyException e) {failed=true;}
    	assertTrue("Last transaction should have failed with invalid keys",failed);

    	
    	// Check the create transction fails if the outputs exceed the inputs
    	final double txn4Value=20;
    	final int txn4prevOutputIndex=1;
        failed=false;
    	try {
    		Transaction transaction4 = createRealTransaction ( transaction2, txn4prevOutputIndex,outputKeyPair0,keypairTo, txn4Value);
    		System.out.println(transaction4.getOutputString());
    	} catch(InvalidKeyException e) {failed=true;}
    	assertTrue("Last transaction should have failed with too big a spend",failed);

    	
    	System.out.println("testCreateWalletCreateTransaction() ^^^^");
    }
    
    /** internal method which creates a transaction, hanging it off a previous output
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
    private Transaction createRealTransaction(Transaction priorTransaction, int prevOutputIndex, CurrencyKeyPair sourceKey,
    		CurrencyKeyPair outputKeyPair, double outputValue  ) 
      throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, UnsupportedEncodingException, InvalidKeySpecException{
    	
    	// build the inputs
    	ArrayList<TransactionInput> inputs= new ArrayList<TransactionInput>();
    	TransactionOutput priorOutput0 = priorTransaction.getOutput(prevOutputIndex);
    	TransactionInput input0 =new TransactionInput(priorOutput0.getHash(),priorOutput0.getIndex());
    	inputs.add(input0);
    	
    	// Validate that the pubKey listed on the previous transaction == that of the key trying to do the spend    	
    	CryptoUtils.validateKeyPair(CryptoUtils.generatePubKey(priorOutput0.getPubKey()),sourceKey);
    	
    	// Check the above signing is working, by cross validating against a known crap key
    	boolean thrown=false;
    	try {
    		Wallet w = new Wallet();
    		CurrencyKeyPair badKeyPair = w.GenerateKeyPair("Bad Key Pair");
    		CryptoUtils.validateKeyPair(CryptoUtils.generatePubKey(priorOutput0.getPubKey()),badKeyPair);
    	} catch (InvalidKeyException e) {thrown = true;}
    	assertTrue("Key validation should have failed",thrown);
    	
    	// build the outputs
    	ArrayList<TransactionOutput> outputs= new ArrayList<TransactionOutput>();
    	TransactionOutput output0 = new TransactionOutput(0,outputValue,outputKeyPair.getPubKeyAsString());
    	
    	double origTransactionValue=priorOutput0.getValue();
    	String origTransactionPubKey=priorOutput0.getPubKey();
    	
    	double changeAmount=origTransactionValue - outputValue;
    	if (changeAmount<0) throw new InvalidKeyException("Insufficient Funds");
    	TransactionOutput change = new TransactionOutput(1,changeAmount,origTransactionPubKey);
    	outputs.add(output0);
    	outputs.add(change);
    	Transaction transaction = new Transaction(inputs,outputs);
    	
    	return transaction;
    }
    
    private void createTxnOutput(Wallet wallet, String keyLabel, int index, double value,ArrayList<TransactionOutput> outputs) throws NoSuchAlgorithmException {
    	CurrencyKeyPair outputKeyPair = wallet.GenerateKeyPair(keyLabel);
    	String pubKeyString = outputKeyPair.getPubKeyAsString();
    	TransactionOutput outputTransaction = new TransactionOutput(index,value,pubKeyString);
    	outputs.add(outputTransaction);
    }

}

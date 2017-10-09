package JOHacks.Wallet;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.ArrayList;

import JOHacks.AppTest;
import JOHacks.Generic.CurrencyKeyPair;
import JOHacks.Generic.Transaction;
import JOHacks.Generic.TransactionElement;
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
    	CurrencyKeyPair keypairToRight = wallet.GenerateKeyPair("Paying to");
    	CurrencyKeyPair keypairToWrong = wallet.GenerateKeyPair("Not Paying to");
    	
    	final String STRING_TO_SIGN= "pay <key> <value>";    	
    	final String messageSignature = keypairFrom.signMessage(STRING_TO_SIGN);
    	
    	TransactionElement input1 = new TransactionElement(3.5);
    	TransactionElement input2 = new TransactionElement(2.5);
    	TransactionElement input3 = new TransactionElement(4);   	
    	ArrayList<TransactionElement> inputs= new ArrayList<TransactionElement>();
    	inputs.add(input1);
    	inputs.add(input2);
    	inputs.add(input3);
    	
    	TransactionElement output1 = new TransactionElement(8);
    	TransactionElement output2 = new TransactionElement(2);
    	ArrayList<TransactionElement> outputs= new ArrayList<TransactionElement>();
    	outputs.add(output1);
    	outputs.add(output2);
    	
    	Transaction transaction = new Transaction(inputs,outputs);
    	
    }
    
	public final String BYTES_ENCODING="UTF-8";
	private byte[] getBytes(String stringToCovert) throws UnsupportedEncodingException {
		return stringToCovert.getBytes(BYTES_ENCODING);
	}
}

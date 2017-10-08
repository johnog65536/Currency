package JOHacks.Wallet;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

import JOHacks.AppTest;
import JOHacks.Generic.CurrencyKeyPair;
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
    	//getBytes("UTF8");
    }

    public void testCreateWalletSignMessage() throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, SignatureException
    {
        Wallet wallet = new Wallet();
    	CurrencyKeyPair keypair = wallet.GenerateKeyPair("My First KeyPair");    
    	
    	final String STRING_TO_SIGN= "Thing being signed";
    	final String RANDOM_STRING = "Some Random String";
    	
    	final String messageSignature = keypair.signMessage(STRING_TO_SIGN);
    	
    	boolean legit = keypair.verifySignature(STRING_TO_SIGN, messageSignature);
    	assertTrue("Good Signature Passed",legit);  	
    	
    	boolean notlegit = keypair.verifySignature(RANDOM_STRING, messageSignature);
    	assertFalse("Bad Signature Failed",notlegit);
    	
    }
    
    
    
	public final String BYTES_ENCODING="UTF-8";
	private byte[] getBytes(String stringToCovert) throws UnsupportedEncodingException {
		return stringToCovert.getBytes(BYTES_ENCODING);
	}
}

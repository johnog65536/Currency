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
    	
    	final String THING_TO_SIGN= "Thing being signed";
    	final String RANDOM_THING = "Some Random String";
    	
    	byte[] messageSignature = keypair.signMessage(THING_TO_SIGN);
    	// converting the signature to strings totally messes it up!!
    	// todo need to fix that
    	
    	boolean legit = keypair.verifySignature(THING_TO_SIGN, messageSignature);
    	assertTrue("Good Signature Passed",legit);  	
    	
    	boolean notlegit = keypair.verifySignature(RANDOM_THING, messageSignature);
    	assertFalse("Bad Signature Failed",notlegit);
    	
    }
}

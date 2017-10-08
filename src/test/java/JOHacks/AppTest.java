package JOHacks;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

import JOHacks.Generic.CurrencyKeyPair;
import JOHacks.Miner.Miner;
import JOHacks.Wallet.Wallet;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import sun.misc.BASE64Encoder;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }


    
    public void testMinerSha1doStuff() throws UnsupportedEncodingException {

    	
    }
    
    private void out(String label, String message) {
    	System.out.println("\""+label+"\""+message+"\"");
    }
    
    

}

package JOHacks;

import JOHacks.Generic.CurrencyKeyPair;
import JOHacks.Miner.Miner;
import JOHacks.Wallet.Wallet;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

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

    public void testCreateWallet()
    {
        Wallet wallet = new Wallet();
    }
    
    public void testCreateWalletWithKeys()
    {
        Wallet wallet = new Wallet();

    	CurrencyKeyPair keypair = wallet.GenerateKeyPair("My First KeyPair");    
    	System.out.println(keypair);
    }

    public void testMinerSha1Hash() {
    	Miner miner = new Miner();
    	String helloWorld="hello world";
    	String helloWorldHash= miner.calcHash(helloWorld);
    	assertEquals("2aae6c35c94fcfb415dbe95f408b9ce91ee846ed",helloWorldHash);
    	
    	String todaysTheDay="todays\nthe\nday";
    	String todaysTheDayHash= miner.calcHash(todaysTheDay);
    	assertEquals("13d88910aa105741c1cf716c5c28cdaa9bba50a0",todaysTheDayHash);
    }
}

package JOHacks.Miner;

import JOHacks.AppTest;
import JOHacks.Generic.CryptoUtils;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestMiner     extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public TestMiner( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( TestMiner.class );
    }


    
    public void testMinerSha1Hash() {
    	Miner miner = new Miner();
    	String helloWorld="hello world";
    	String helloWorldHash= CryptoUtils.calcHash(helloWorld);
    	assertEquals("2aae6c35c94fcfb415dbe95f408b9ce91ee846ed",helloWorldHash);
    	
    	String todaysTheDay="todays\nthe\nday";
    	String todaysTheDayHash= CryptoUtils.calcHash(todaysTheDay);
    	assertEquals("13d88910aa105741c1cf716c5c28cdaa9bba50a0",todaysTheDayHash);
    }
}
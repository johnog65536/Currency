package JOHacks.Wallet;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

import JOHacks.AppTest;
import JOHacks.Generic.CryptoUtils;
import JOHacks.Generic.CurrencyKeyPair;
import JOHacks.Generic.TransactionOutput;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestKeyPair extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public TestKeyPair(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(TestKeyPair.class);
	}

    public void testMinerSha1Hash() throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException {
    	
        Wallet wallet = new Wallet();
    	CurrencyKeyPair keypair = wallet.GenerateKeyPair("Test KeyPair");
    	TransactionOutput outputTxn = new TransactionOutput(0,7,keypair.getPubKeyAsString());
    	
    	PublicKey pubKey = CryptoUtils.generatePubKey(outputTxn.getPubKey());
    	//todo Fix!
    	//CryptoUtils.validateKeyPair(pubKey,outputTxn.get);
    }
}

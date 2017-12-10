package JOHacks.Generic;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import JOHacks.Wallet.Wallet;

public class TestHttpCallOnMiner {

	final String ROOT_URL= "http://localhost:8080/api";
	
	// todo uin bodge this - throws File Not Found in current form
	//final String REGISTER_GENESYS_KEYS=ROOT_URL+"/register-genesys";
	private final String REGISTER_GENESYS_KEYS=ROOT_URL+"/create";

	private final String PENDING_URL=ROOT_URL+"/pending";	
	private final String CONFIRMED_URL=ROOT_URL+"/confirmed";
	private final String CREATE_URL=ROOT_URL+"/create";
	private final String BLOCKCHAIN_URL=ROOT_URL+"/blockchain";
	private final String BLOCK_URL=ROOT_URL+"/block/";
	private final String CONFIRM_TRANSACTIONS_URL=ROOT_URL+"/confirm-transactions";

	private final Wallet wallet ;
	private final CurrencyKeyPair genesysKeyPair;
	private final String genesysKeyLabel = "Genesys Key Pair"; 
	
	private final GsonBuilder builder = new GsonBuilder();
	private final Gson gson = builder.create();
	
	public TestHttpCallOnMiner() throws NoSuchAlgorithmException {
		wallet = new Wallet();
		genesysKeyPair = wallet.GenerateKeyPair(genesysKeyLabel);  
	}
	
	@Test
	public void testHttpMining() throws IOException, NoSuchAlgorithmException {
		registerGenesysKeysPost();
		
		createSimpleTransaction(10.0,genesysKeyLabel,"MyFirstKey","Pay from the genesis key to MyFirstKey!");
		createSimpleTransaction(5.0,"MyFirstKey","MySecondKey",   "Pay from MyFirstKey to MySecondKey!");
		createSimpleTransaction(2.0,"MyFirstKey","MyThirdKey",    "Pay from MyFirstKey to MyThirdKey!");
		createSimpleTransaction(2.0,"MySecondKey","MyThirdKey",   "Pay from MySecondKey to MyThirdKey!");
		
		getPendingTransactions();
		getConfirmedTransactions();
	}
	
	
	
	private void registerGenesysKeysPost() throws IOException, NoSuchAlgorithmException {
		System.out.println("");
		
		final String jsonString = genesysKeyPair.getJSONRepresentation();		
		final String urlParameters = "requestjson="+jsonString;
		System.out.println("registerGenesysKeysPost() Posting to : "+urlParameters);
		
		final String response = HttpUtils.sendPost(REGISTER_GENESYS_KEYS,urlParameters);
		System.out.println("registerGenesysKeysPost() : "+response);
	}

	
	// this is the simple version needs replacing with the complex one in due course
	private void createSimpleTransaction(double value, String inputKeyLabel,String outputKeyLabel,String comment) throws IOException, NoSuchAlgorithmException {
		System.out.println("");
		
		final CurrencyKeyPair fromKey = wallet.getKeyPair(inputKeyLabel);
		final CurrencyKeyPair toKey   = wallet.getKeyPair(outputKeyLabel);

		final String from = fromKey.getPubKeyAsString();
		final String to   = toKey.getPubKeyAsString();
		
		final PortableTransaction pt = new PortableTransaction(value,from,to,comment);
		final String jsonString = gson.toJson(pt);
		final String urlParameters = "requestjson="+jsonString;
		
		System.out.println("createSimpleTransaction() Posting to : "+REGISTER_GENESYS_KEYS + " " +urlParameters);
		
		final String response = HttpUtils.sendPost(REGISTER_GENESYS_KEYS,urlParameters);			
		System.out.println("createSimpleTransaction() testGetPendingTransactions: "+ response);
	}
	


	
	class PortableTransaction{
		private double amount;
		private String fromAddress;
		private String toAddress;
		private String comment;
		
		public PortableTransaction(double amount, String fromAddress, String toAddress, String comment) {
			this.amount = amount;
			this.fromAddress = fromAddress;
			this.toAddress = toAddress;
			this.comment = comment;
		}

		public double getAmount() {return amount;}
		public String getFromAddress() {return fromAddress;	}
		public String getToAddress() {return toAddress;	}
		public String getComment() {return comment;}
					
	}
	
	// this is the more complex version
	private void createComplexTransaction() throws IOException, NoSuchAlgorithmException {
		System.out.println("");

    	final ArrayList<TransactionInput> inputs= new ArrayList<TransactionInput>();
    	final ArrayList<TransactionOutput> outputs= new ArrayList<TransactionOutput>();
		
    	final String OUTPUT_KEY_LABEL_0="Key Paying to 0";
    	final String OUTPUT_KEY_LABEL_1="Key Paying to 1";
		
		wallet.GenerateKeyPair(OUTPUT_KEY_LABEL_0);
		wallet.GenerateKeyPair(OUTPUT_KEY_LABEL_1);
		
		//this is a bit hacky - needs to hang off the genesys transaction & have the right values in the outputs (ie not 5.0 each)
		//needs the get transaction method to be available
    	final TransactionInput input0 = new TransactionInput("no Txn",-1);
    	inputs.add(input0);
    
    	outputs.add(wallet.createTxnOutput(OUTPUT_KEY_LABEL_0,0,5.0));
    	outputs.add(wallet.createTxnOutput(OUTPUT_KEY_LABEL_1,1,5.0));
		
    	final Transaction transaction = new Transaction(inputs,outputs);
		
		//todo un bodge
		final String jsonString = gson.toJson(transaction);
		
		final String urlParameters = "requestjson="+jsonString;
		System.out.println("createComplexTransaction() Posting to : "+urlParameters);
		
		final String response = HttpUtils.sendPost(REGISTER_GENESYS_KEYS,urlParameters);			
		System.out.println("createComplexTransaction() testGetPendingTransactions: "+ response);
	}
 	

	private void getPendingTransactions() throws IOException {
		System.out.println("");
		
		final String response = HttpUtils.sendGet(PENDING_URL);		
		System.out.println("getPendingTransactions() : "+ response);
	}
	
	

	private void getConfirmedTransactions() throws IOException {
		System.out.println("");
		
		final String response = HttpUtils.sendGet(CONFIRMED_URL);		
		System.out.println("getConfirmedTransactions() : "+ response);
	}

}



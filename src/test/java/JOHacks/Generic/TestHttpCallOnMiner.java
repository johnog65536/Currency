package JOHacks.Generic;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import JOHacks.Wallet.Wallet;

public class TestHttpCallOnMiner {

	final String ROOT_URL= "http://localhost:8080/api";
	
	final String PENDING_URL=ROOT_URL+"/pending";
	final String CONFIRMED_URL=ROOT_URL+"/confirmed";
	final String CREATE_URL=ROOT_URL+"/create";
	final String BLOCKCHAIN_URL=ROOT_URL+"/blockchain";
	final String BLOCK_URL=ROOT_URL+"/block/";
	final String CONFIRM_TRANSACTIONS_URL=ROOT_URL+"/confirm-transactions";
	
	@Test
	public void testGetPendingTransactions() throws IOException {
		final String response = HttpUtils.sendGet(PENDING_URL);		
		System.out.println("testGetPendingTransactions: "+ response);
	}
	
	
	@Test
	public void testGetConfirmedTransactions() throws IOException {
		final String response = HttpUtils.sendGet(CONFIRMED_URL);		
		System.out.println("testGetConfirmedTransactions: "+ response);
	}

	
	
	@Test
	public void testCreateTransactionPost() throws IOException, NoSuchAlgorithmException {
		final GsonBuilder builder = new GsonBuilder();
		final Gson gson = builder.create();

		final Wallet wallet = new Wallet();
		final String genesysKeyLabel = "Genesys Key Pair";    	
		final CurrencyKeyPair genesysKeyPair = wallet.GenerateKeyPair(genesysKeyLabel);    	
		final String jsonString = genesysKeyPair.getJSONRepresentation();
		
		
		final String urlParameters = "requestjson="+jsonString+"&id=1";
		
		final String response = HttpUtils.sendPost(CREATE_URL,urlParameters);		
		System.out.println("testCreateTransactionPost: "+response);
	}

}



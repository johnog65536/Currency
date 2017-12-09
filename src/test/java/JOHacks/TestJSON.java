package JOHacks;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TestJSON {

	@Test
	public void test() {
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		
		
		String json = "{\"brand\":\"Jeep\", \"doors\": 3}";		
		TestJSON test = gson.fromJson(json, TestJSON.class);
		log (test.brand);
		log (""+test.doors);
	}

	String brand;
	int doors;
	
	
    //todo unbodge this
	final private boolean LOGGING_ON = true;
    private void log(String tolog) {
    	if (LOGGING_ON)
    		System.out.println(tolog);
    }
}

package JOHacks;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestJSON {

	@Test
	public void test() {
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		
	}

	
	
	
    //todo unbodge this
	final private boolean LOGGING_ON = true;
    private void log(String tolog) {
    	if (LOGGING_ON)
    		System.out.println(tolog);
    }
}

package JOHacks.Generic;

public class PortableKeyPair{
	private final String name;
	private final String pubKey;
	private final String privKey;

	
	public PortableKeyPair(String ipPubKey,String ipPrivKey,String ipName){
		
		pubKey=ipPubKey;
		privKey=ipPrivKey;
		name=ipName;
	}


	public String getName() {
		
		return name;
	}


	public String getPubKey() {
		return pubKey;
	}


	public String getPrivKey() {
		return privKey;
	}
	
	
}
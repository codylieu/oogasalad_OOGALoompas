package main.java.engine.network;

public class ClientMain {

	private static final String MY_IP = "10.190.28.228";
	
	 public static void main(String[] args) {
		 TDClient client = new TDClient(MY_IP, 12345);
		 client.makeConnection("Hello I would like to connect?!?!");
	 }

}

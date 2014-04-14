package main.java.engine.network;

public class ClientMain {

	private static final String MY_IP = "10.190.54.128";
	
	 public static void main(String[] args) {
		 TDClient client = new TDClient(MY_IP, 12345, 2000);
		 String temp = "Hello I would like to connect!";
		 client.sendObjectToServer("java.lang.String", temp);
		 client.sendObjectToServer("java.lang.String", temp);
		 client.sendObjectToServer("java.lang.String", temp);
	 }

}

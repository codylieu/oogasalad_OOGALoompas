package main.java.engine.network;

public class ServerMain {

	   public static void main (String[] args) {
		   TDServer server = new TDServer(12345);
		   server.runServer();
	   }
}

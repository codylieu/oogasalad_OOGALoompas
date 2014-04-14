package main.java.engine.network;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ServerMain {

	public static void main (String[] args) {
		TDServer server = new TDServer(12345);
		System.out.println("My IP Address is: " + server.getMyIPAddress());
		server.startServer();
	}
}

package main.java.engine.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ServerMain {

	private static final String IP = "10.190.29.166";
	private static final int PORT_NUMBER_A = 12345;
	private static final int PORT_NUMBER_B = 3555;

	public static void main (String[] args) throws IOException, ClassNotFoundException {
		new Server(PORT_NUMBER_A).start();
		
//		System.out.println("My IP Address is: " + server.getMyIPAddress());
		
		while (true) {
			Scanner in = new Scanner(System.in);
			String input = "Server A: " + in.nextLine();
			new Client (IP, PORT_NUMBER_B, input);
		}

	}
}

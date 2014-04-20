package main.java.engine.network;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

public class ServerMainTwo {

	private static final String IP = "10.190.29.166"; // IP address of the computer you want to connect to
	private static final int PORT_NUMBER_A = 12345; // designated port of your computer for connection
	private static final int PORT_NUMBER_B = 3555; // designated port of the computer that you want to connect to

	public static void main (String[] args) throws IOException, ClassNotFoundException {
		
		// Display your IP address so that you can tell the other person which address he/she should connect to
		System.out.println("My IP Address is: " + InetAddress.getLocalHost().getHostAddress());
		
		// Start a server thread to listen to any incoming connection
		new Server(PORT_NUMBER_B).start();
		
		// Type stuff in the console and send it
		while (true) {
			Scanner in = new Scanner(System.in);
			String input = "Server B: " + in.nextLine();
			
			// Send your words to the other person
			new Client(IP, PORT_NUMBER_A, input);
		}
	}
}

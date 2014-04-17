package main.java.engine.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ServerMain {

	public static void main (String[] args) throws IOException {
		TDServer server = new TDServer(12345);
		System.out.println("My IP Address is: " + server.getMyIPAddress());
		while (true) {
			//		 start a console remote conversation
//			Scanner in = new Scanner(System.in);
//			System.out.print("Server : ");
//			String input = in.nextLine();       
			server.receiveObjectFromClient();	
//			server.sendObjectToClient(input);
		}

	}
}

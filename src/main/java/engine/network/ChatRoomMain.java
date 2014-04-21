package main.java.engine.network;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * A demo of console chat room to showcase the working of network
 * 
 * @author Lawrence Lin
 *
 */
public class ChatRoomMain {
	public static void main (String[] args) throws UnknownHostException {
		
		// Display your IP address so that you can tell the other person which address he/she should connect to
		System.out.println("My IP Address is: " + InetAddress.getLocalHost().getHostAddress());
		
		new ChatRoomParticipant("10.190.27.28", 12345, 3555, "LawrenceB");
	}
}

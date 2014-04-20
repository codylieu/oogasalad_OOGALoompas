package main.java.engine.network;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Create a participant to join the chat. 
 * 
 * @author Lawrence Lin
 *
 */
public class ChatRoomParticipant {

	private String myName;
	private String IPAddress;
	private int myPort;
	private int otherPort;

	/**
	 * @param IP : IP address to connect to (i.e. the other person's IP address)
	 * @param myPort : my designated port for connection
	 * @param otherPort : the other person's designated port for connection
	 * @param name : the name you would like to display in conversation
	 */
	public ChatRoomParticipant(String IP, int myPort, int otherPort, String name) {
		this.IPAddress = IP;
		this.myPort = myPort;
		this.otherPort = otherPort;
		this.myName = name;
		startServer();
		try {
			startChat();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get the server up and running to listen for any incoming connection
	 */
	private void startServer() {
		new Server(myPort).start();
	}

	/**
	 * 
	 * 
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	private void startChat() throws UnknownHostException, IOException {
		while (true) {
			Scanner in = new Scanner(System.in);
			String input = myName + ": " + in.nextLine();

			// Send your words to the other person
			new Client(IPAddress, otherPort, input);
		}
	}

}

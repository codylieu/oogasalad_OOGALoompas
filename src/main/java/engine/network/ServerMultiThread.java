package main.java.engine.network;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * A thread which is created whenever Server receives a new connection.
 * The thread deals with the object(s) received. 
 * 
 * @author Lawrence Lin
 *
 */
public class ServerMultiThread extends Thread {
	private Socket socket;

	public ServerMultiThread (Socket socket) {
		super("ServerMultiThread");
		this.socket = socket;
	}

	/* 
	 * Read in the object(s) received. 
	 * 
	 * (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		try {
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			while (true) {
				Object obj = in.readObject();
				handleObjectReceived(obj);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Modify this method to customize handling of the object(s) received
	 * 
	 * @param obj: object received
	 */
	private void handleObjectReceived (Object obj) {
		System.out.println((String) obj);
	}


}

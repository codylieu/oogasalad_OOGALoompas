package main.java.engine.network;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class TDServer extends TDNetwork{

	private int portNumber;
	private boolean connectionEstablished;

	public TDServer(int port) {
		portNumber = port;
		connectionEstablished = false;
	}
	
	public boolean connectionEstablished() {
		return connectionEstablished;
	}

	public void startServer () {
		try {

			ServerSocket serverS = new ServerSocket(portNumber);
			//			Socket clientSocket = serverS.accept();

			while (true) {

				Socket clientSocket = serverS.accept();
				connectionEstablished = true;

				ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
				String inType = (String) in.readObject();
				Object inObj = in.readObject();
				boolean objectReceived = dealWithObjectReceived(inType, inObj);

				PrintWriter out =
						new PrintWriter(clientSocket.getOutputStream(), true); 
				if (objectReceived) {out.write("Server has received your object!");}
				out.flush();
				out.close();

				//				clientSocket.close();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@SuppressWarnings("rawtypes")
	private boolean dealWithObjectReceived (String objectType, Object obj) {
		Class c = null;
		try {
			c = Class.forName(objectType);
			System.out.println("Object received is: " + c.cast(obj));
			return true;
		}
		catch (ClassNotFoundException e) {
			System.err.println("Object received from client has an invalid class.");
			return false;
		}
	}

	private void sendObjectToClient (String type, Object obj) {

	}
}
//package main.java.engine.network;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.PrintWriter;
//import java.net.ServerSocket;
//import java.net.Socket;
//
//public class TDServer {
//
//	int portNumber;
//
//	public TDServer(int port) {
//		portNumber = port;	
//	}
//
//	public void readFromClient() {
//		try (
//				ServerSocket serverSocket = new ServerSocket(portNumber);
//				Socket clientSocket = serverSocket.accept();
//				PrintWriter out =
//						new PrintWriter(clientSocket.getOutputStream(), true); 
//				BufferedReader in = new BufferedReader(
//						new InputStreamReader(clientSocket.getInputStream()));
//				) {
//			String input;
//			while ((input = in.readLine())!= null) {
//				System.out.println(input);
//			}
//			clientSocket.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//}

package main.java.engine.network;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;


public class TDServer {

	private int portNumber;

	public TDServer(int port) {
		portNumber = port;
	}

	@SuppressWarnings("resource")
	public void runServer () {
		try {

			ServerSocket serverS = new ServerSocket(portNumber);
			//			Socket clientSocket = serverS.accept();

			while (true) {

				Socket clientSocket = serverS.accept();

				ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
				String inType = (String) in.readObject();
				Object inObj = in.readObject();
				boolean objectReceived = dealWithObjectReceived(inType, inObj);

				//				ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
				//				out.writeObject("Hi Client, this is server. Your information has been received");
				//				out.flush();
				//				out.close();

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
		}
		catch (ClassNotFoundException e) {
			System.out.println("Client's object type is not found...");
			return false;
		}
		System.out.println("I received object \"" + c.cast(obj) + "\" from the client!");
		return true;
	}

	private void sendObjectToClient (String type, Object obj) {

	}
}
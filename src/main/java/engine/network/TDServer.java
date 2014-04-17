package main.java.engine.network;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class TDServer extends TDNetwork{

	private int portNumber;
	private ServerSocket serverS;

	public TDServer(int port) {
		portNumber = port;
		try {
			serverS = new ServerSocket(portNumber);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		listening = true;
	}

	public void receiveObjectFromClient () {
		try {
			Socket clientSocket = serverS.accept();
			receive(clientSocket);
//			ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
////			String inType = (String) in.readObject();
//			Object inObj = in.readObject();
//			boolean objectReceived = dealWithObjectReceived(inObj);

//			PrintWriter out =
//					new PrintWriter(clientSocket.getOutputStream(), true); 
//			if (objectReceived) {out.write("Server has received your object!");}
//			out.flush();
//			out.close();

			//				clientSocket.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void sendObjectToClient (Object obj) throws IOException {
		Socket clientSocket = serverS.accept();
		write(clientSocket, obj);
		clientSocket.close();
	}
}
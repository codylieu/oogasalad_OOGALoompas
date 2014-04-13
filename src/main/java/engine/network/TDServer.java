package main.java.engine.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TDServer {

	int portNumber;
	
	public TDServer(int port) {
		portNumber = port;	
	}
	
	public void readFromClient() {
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(portNumber);
			Socket clientSocket = serverSocket.accept();
			PrintWriter out =
	                new PrintWriter(clientSocket.getOutputStream(), true);                   
	        BufferedReader in = new BufferedReader(
	                new InputStreamReader(clientSocket.getInputStream()));
	        
	        String input;
	        while ((input = in.readLine())!= null) {
	        	System.out.println(input);
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

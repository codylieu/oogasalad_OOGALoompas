package main.java.engine.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

public class TDClient {
	String serverIP;
	int portNumber;
	Socket socket;

	public TDClient(String IP, int port) {
		serverIP = IP;
		portNumber = port;
	}

	public void addStuffToBeTransferred(List<Object> objs) {

	}

	public void makeConnection(String s) {
		try {
			socket = new Socket(serverIP, portNumber);
//			OutputStream out = socket.getOutputStream();
			PrintWriter out =
					new PrintWriter(socket.getOutputStream(), true);   
			out.write(s);
			out.flush();
			BufferedReader in = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			print(in);
			socket.close();
		} catch (UnknownHostException e) {
			System.out.println("Unknown host!");
		} catch (IOException e) {
			System.out.println("IO exception");
			e.printStackTrace();
		}
	}
	
	private void print(BufferedReader reader) {
		while ((reader) != null) {
			String inputLine;
			try {
				inputLine = reader.readLine();
				System.out.println(inputLine);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        }
	}



}

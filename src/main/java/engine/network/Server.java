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


/**
 * A server thread that is created to listen for any incoming connection. 
 * Once an incoming connection is detected, a separate thread (ServerMultiThread) is created
 * to handle the connection while the server can continue to listen for
 * connections.
 * 
 * Each time the other computer sends something, it is treated as a new
 * connection and hence a new ServerMultiThread is created.
 * 
 * @author Lawrence Lin
 *
 */
public class Server extends Thread{

	private int portNumber;
	private ServerSocket serverSocket;

	public Server(int port) {
		portNumber = port;
	}
	
	/* 
	 * Listen for connections. 
	 * 
	 * (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		
		try {
			serverSocket = new ServerSocket(portNumber);
			while (true) {
				new ServerMultiThread(serverSocket.accept()).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
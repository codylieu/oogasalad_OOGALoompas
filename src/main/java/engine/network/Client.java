package main.java.engine.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import main.java.engine.Player;


/**
 * A client that is created whenever something needs to be sent to the other computer. 
 * 
 * @author Lawrence Lin
 *
 */
public class Client {

	private String serverIP;
    private int serverPort;
    private Socket socket;

    public Client (String serverIP, int portNum, Object obj) throws UnknownHostException, 
    																IOException {
    	this.serverIP = serverIP;
    	this.serverPort = portNum;
    	establishConnection();
    	try {
			sendObjectToServer(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Establish connection with the destination server. 
     * 
     * @throws UnknownHostException
     * @throws IOException
     */
    private void establishConnection() throws UnknownHostException, 
    										  IOException {
    	  socket = new Socket(serverIP, serverPort);
    }

    /**
     * Send the object over to the other computer.
     * 
     * @param obj: object to be sent
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void sendObjectToServer (Object obj) throws IOException,
                                                        ClassNotFoundException {

        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(obj);
        out.flush();
    }
    
}

package main.java.engine.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import main.java.engine.Player;


public class TDClient extends TDNetwork{

    private static final String CONNECTION_ESTABLISHED = "Connection established!";
	private String serverIP;
    private int serverPort;
    private Socket socket;
    private Player player;

    public TDClient (String hostIp, int portNum) {
    	serverIP = hostIp;
    	serverPort = portNum;
    	player = new Player();
    	listening = false;
    }
    
    public Player getPlayer() {
    	return player;
    }
    
    public void establishConnection() throws IOException {
    	try {
			socket = new Socket(serverIP, serverPort);
			write(socket, CONNECTION_ESTABLISHED);
			socket.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private void sendObjectToServer (Object obj) throws IOException,
                                                                    ClassNotFoundException {

        socket = new Socket(serverIP, serverPort);
//        if (firstTimeConnection) {
//        	System.out.println("Connection established!");
//        	firstTimeConnection = false;
//        }

//        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
//        out.writeObject(obj);
//        out.flush();
        write(socket, obj);
        socket.close();
    }
    
    public void receiveObjectFromServer() throws UnknownHostException, IOException, ClassNotFoundException {
    	socket = new Socket(serverIP, serverPort);
    	receive(socket);
		socket.close();
    }
}

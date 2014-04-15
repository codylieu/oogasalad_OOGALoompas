package main.java.engine.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

import main.java.engine.Player;


public class TDClient extends TDNetwork{

    private String serverIP;
    private int serverPort;
    private Socket socket;
    private boolean firstTimeConnection;
    private Player player;

    public TDClient (String hostIp, int portNum) {
    	serverIP = hostIp;
    	serverPort = portNum;
    	firstTimeConnection = true;
    	player = new Player();
    }
    
    public Player getPlayer() {
    	return player;
    }

    public void sendObjectToServer (String outType, Object outObj) {
        
        try {
            createSocketAndSend(outType, outObj);
        }
        catch (Exception e) {
            System.err.println("Object cannot be sent.");
            e.printStackTrace();
        }
    }

    private void createSocketAndSend (String outType, Object outObj) throws IOException,
                                                                    ClassNotFoundException {

        socket = new Socket(serverIP, serverPort);
        if (firstTimeConnection) {
        	System.out.println("Connection established!");
        	firstTimeConnection = false;
        }

        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(outType);
        out.writeObject(outObj);
        out.flush();

        BufferedReader in = new BufferedReader(
				new InputStreamReader(socket.getInputStream()));
        String inS;
        while ((inS = in.readLine())!=null) System.out.println(inS);

        socket.close();
    }
}

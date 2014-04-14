//package main.java.engine.network;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.io.PrintWriter;
//import java.net.Socket;
//import java.net.UnknownHostException;
//import java.util.List;
//
//public class TDClient {
//	String serverIP;
//	int portNumber;
//	Socket socket;
//
//	public TDClient(String IP, int port) {
//		serverIP = IP;
//		portNumber = port;
//	}
//
//	public void addStuffToBeTransferred(List<Object> objs) {
//
//	}
//
//	public void makeConnection(String s) {
//		try {
//			socket = new Socket(serverIP, portNumber);
////			OutputStream out = socket.getOutputStream();
//			PrintWriter out =
//					new PrintWriter(socket.getOutputStream(), true);   
////			out.write(s);
//			out.print(s);
//			out.flush();
//			BufferedReader in = new BufferedReader(
//					new InputStreamReader(socket.getInputStream()));
//			print(in);
//			socket.close();
//		} catch (UnknownHostException e) {
//			System.out.println("Unknown host!");
//		} catch (IOException e) {
//			System.out.println("IO exception");
//			e.printStackTrace();
//		}
//	}
//	
//	private void print(BufferedReader reader) {
//		while ((reader) != null) {
//			String inputLine;
//			try {
//				inputLine = reader.readLine();
////				System.out.println(inputLine);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//        }
//	}
//
//
//
//}

package main.java.engine.network;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class TDClient {

    private final String HOSTIP;
    private final int PORT;
    private final int TIMEOUT;
    private Socket s;

    public TDClient (String hostIp, int portNum, int timeOut) {
        HOSTIP = hostIp;
        PORT = portNum;
        TIMEOUT = timeOut;
    }

    public void sendObjectToServer (String outType, Object outObj) {
        
        try {
            createSocketAndSend(outType, outObj);
        }
        catch (Exception e) {
            System.out.println("Something went wrong trying to send the object...");
            e.printStackTrace();
        }
        
    }
    
    public void sendFileToServer (String filePath) {

        try {
            Scanner fin = new Scanner(new FileInputStream(filePath));
            List<String> fileText = new ArrayList<String>();
            while (fin.hasNext()) {
                fileText.add(fin.nextLine());
            }
            fin.close();
            
            createSocketAndSend("textfile", fileText);
        }
        catch (Exception e) {
            System.out.println("Something went wrong trying to send the file...");
            e.printStackTrace();
        }

    }

    private void createSocketAndSend (String outType, Object outObj) throws IOException,
                                                                    ClassNotFoundException {

//        s = new Socket();
//        s.connect(new InetSocketAddress(HOSTIP, PORT), TIMEOUT);
        s = new Socket(HOSTIP, PORT);

        ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
        out.writeObject(outType);
        out.writeObject(outObj);
        out.flush();

//        ObjectInputStream in = new ObjectInputStream(s.getInputStream());
//        String message = (String) in.readObject();
//        System.out.println(message);
        BufferedReader in = new BufferedReader(
				new InputStreamReader(s.getInputStream()));
        String inS;
        while ((inS = in.readLine())!=null) System.out.println(inS);

//        s.close();
    }
}

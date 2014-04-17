package main.java.engine.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public abstract class TDNetwork {
	
	protected boolean listening;
	
	public String getClassString(Object obj) {
		return obj.getClass().toString().split(" ")[1];
	}
	
	public String getMyIPAddress() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected boolean dealWithObjectReceived (Object obj) {
		Class c = null;
//		try {
//			c = Class.forName(objectType);
//			System.out.println(c.cast(obj));
			System.out.println(obj);
			return true;
//		}
//		catch (ClassNotFoundException e) {
//			System.err.println("Object received from client has an invalid class.");
//			return false;
//		}
	}
	
	public boolean isListening() {
		return listening;
	}
	
	protected void write(Socket s, Object obj) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
        out.writeObject(obj);
        out.flush();
	}
	
	protected void receive(Socket s) throws IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(s.getInputStream());
		Object inObj = in.readObject();
		dealWithObjectReceived(inObj);
	}
}

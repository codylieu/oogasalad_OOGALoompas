package main.java.engine.network;

import java.net.InetAddress;
import java.net.UnknownHostException;

public abstract class TDNetwork {
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
	
}

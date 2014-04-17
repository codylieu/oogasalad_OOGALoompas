package main.java.engine.network;

import java.io.IOException;
import java.net.UnknownHostException;

import main.java.engine.Player;

public class ClientMain {

	private static final String SERVER_IP = "10.190.50.248";

	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
		TDClient client = new TDClient(SERVER_IP, 12345);
		Player p = client.getPlayer();
		p.addMoney(200);
		p.addScore(300);
		Integer money = p.getMoney();
		Double score = p.getScore();
		//		 System.out.println(money.getClass().toString().split(" ")[1]);
//		client.sendObjectToServer(money.getClass().toString().split(" ")[1], money);
		//		 client.sendObjectToServer(score.getClass()+"", score);
		client.establishConnection();
//		while (true) {
//			if (client.isListening()) {
//				client.receiveObjectFromServer();
//			} else {
//				client.sendObjectToServer("Connection Established!");
//			}
//		}
	}

}

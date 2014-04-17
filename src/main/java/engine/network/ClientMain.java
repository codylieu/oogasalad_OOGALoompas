package main.java.engine.network;

import main.java.engine.Player;

public class ClientMain {

	private static final String SERVER_IP = "10.190.96.116";
	
	 public static void main(String[] args) {
		 TDClient client = new TDClient(SERVER_IP, 12345);
//		 String temp = "Hello I would like to connect!";
//		 client.sendObjectToServer("java.lang.String", temp);
//		 client.sendObjectToServer("java.lang.String", temp);
		 Player p = client.getPlayer();
		 p.changeMoney(200);
		 p.addScore(300);
		 Integer money = p.getMoney();
		 Double score = p.getScore();
//		 System.out.println(money.getClass().toString().split(" ")[1]);
		 client.sendObjectToServer(money.getClass().toString().split(" ")[1], money);
//		 client.sendObjectToServer(score.getClass()+"", score);
	 }

}

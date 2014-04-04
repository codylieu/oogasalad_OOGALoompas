package main.java.player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

public class WelcomeButtonPanelListener implements ActionListener{

	private ResourceBundle resources;
	private Player player;
	public WelcomeButtonPanelListener(ResourceBundle myResources, Player myPlayer){
		resources = myResources;
		player = myPlayer;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		player.showCard(resources.getString(e.getActionCommand()));
		
	}

}

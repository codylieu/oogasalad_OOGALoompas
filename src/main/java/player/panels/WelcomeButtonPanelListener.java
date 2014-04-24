package main.java.player.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import main.java.player.ViewController;

public class WelcomeButtonPanelListener implements ActionListener{

	private ResourceBundle resources;
	private ViewController player;
	public WelcomeButtonPanelListener(ResourceBundle myResources, ViewController myPlayer){
		resources = myResources;
		player = myPlayer;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		player.showCard(resources.getString(e.getActionCommand()));
		
	}

}

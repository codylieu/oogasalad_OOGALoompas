package main.java.player.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import main.java.player.ViewController;

public class WelcomeButtonPanelListener implements ActionListener{

	private ResourceBundle resources;
	private ViewController player;
	private Map<String,String> translations;
	public WelcomeButtonPanelListener(ResourceBundle myResources, ViewController myPlayer, ResourceBundle myLanguageResources){
		resources = myResources;
		player = myPlayer;
		translations = new HashMap<String, String>();
		for(String s: resources.keySet()){
			translations.put(myLanguageResources.getString(s), s);
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		//System.out.println(e.getActionCommand());
		//System.out.println((String)languageResources.getObject(e.getActionCommand()));
		player.showCard(resources.getString(translations.get(e.getActionCommand())));
		
	}

}

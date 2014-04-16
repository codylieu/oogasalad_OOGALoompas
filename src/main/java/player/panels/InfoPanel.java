package main.java.player.panels;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfoPanel extends JPanel {
	private String labelText;
	private JLabel infoLabel;

	public InfoPanel(String myLabelText){
		labelText = myLabelText;
		initInfoPanel();
	}

	private void initInfoPanel(){
		infoLabel = new JLabel(labelText);
		add(infoLabel);	
	}
}
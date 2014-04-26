package main.java.player.panels;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Panel to display Help Text
 * @author Michael Han
 *
 */
@SuppressWarnings("serial")
public class HelpTextPanel extends JPanel {
	private String HELP;
	private JTextArea helpText;
	
	public HelpTextPanel(String help){
		helpText = new JTextArea(10,40);
		HELP = help;
		initHelpInfoPanel();
	}
	
	private void initHelpInfoPanel(){
		helpText.setEditable(false);
		helpText.append(HELP);
		add(helpText, BorderLayout.CENTER);
	}
}



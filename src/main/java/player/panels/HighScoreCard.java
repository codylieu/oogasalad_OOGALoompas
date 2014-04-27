package main.java.player.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import main.java.player.ITDPlayerEngine;

/**
 * Card to keep track of, display, and save high scores
 * @author Michael Han
 *
 */
@SuppressWarnings("serial")
public class HighScoreCard extends JPanel implements ActionListener {

	public static final String INITIAL_PLAYER_NAME = "INITIAL_PLAYER_NAME";
	public static final String INITIAL_HIGH_SCORE_BOX = "INITIAL_HIGH_SCORE_BOX";
	public static final String SPACER_FOR_HIGH_SCORE = ": ";
	public static final String NEWLINE = "\n";
	private JTextField playerName;
	private ITDPlayerEngine engine;
	private JTextArea highScoreDisplay;
	private JLabel instructions;
	private double highScore;
	private JButton mainMenuButton;
	private ResourceBundle languageResources;
	public HighScoreCard(ITDPlayerEngine myEngine, JButton welcome, ResourceBundle languages){
		setLayout(new GridBagLayout());
		engine = myEngine;
		mainMenuButton = welcome;
		languageResources = languages;
		playerName = new JTextField(20);
		playerName.addActionListener(this);		
		highScoreDisplay = new JTextArea(20,50);
		highScoreDisplay.setEditable(false);
		instructions = new JLabel();
		updateLabels(languages);
		addComponents();
	}
	
	//separate method for when languages get changed since dont want to lose this card
	public void updateLabels(ResourceBundle languages){
		//playerName.setText(INITIAL_PLAYER_NAME);
		highScoreDisplay.setText(languages.getString(INITIAL_HIGH_SCORE_BOX));
		instructions.setText(languages.getString(INITIAL_PLAYER_NAME));
	}
	
	private void addComponents(){
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 0;
		add(instructions, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 1;
		add(playerName, constraints);

		constraints.gridx = 0;
		constraints.gridy = 2;
		add(highScoreDisplay, constraints);
				
		constraints.gridx = 0;
		constraints.gridy = 3;
		add(mainMenuButton, constraints);
		
	}

	public void setHighScore() {
		highScore = engine.getHighScore();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String name = playerName.getText();
		playerName.setText("");
		highScoreDisplay.append(name + SPACER_FOR_HIGH_SCORE + highScore +  NEWLINE);
	}


}

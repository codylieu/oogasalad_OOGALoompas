package main.java.player.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import main.java.player.TDPlayerEngine;
import main.java.player.util.Observing;
import main.java.player.util.Subject;

public class HighScoreCard extends JPanel implements Observing, ActionListener {

	public static final String INITIAL = "enter name after deleting this message";
	public static final String SPACER_FOR_HIGH_SCORE = ": ";
	public static final String NEWLINE = "\n";
	private JTextField playerName;
	private TDPlayerEngine engine;
	private JTextArea highScoreDisplay;
	private int highScore;

	public HighScoreCard(){
		setLayout(new GridBagLayout());
		playerName = new JTextField(20);
		playerName.addActionListener(this);
		playerName.setText(INITIAL);
		highScoreDisplay = new JTextArea(20,50);
		highScoreDisplay.setEditable(false);
		addComponents();
	}

	private void addComponents(){
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 0;
		add(playerName, constraints);

		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 1;
		add(highScoreDisplay, constraints);
	}

	@Override
	public void update() {
		// method to get high score or wave or whatever
	}


	@Override
	public void setSubject(Subject s) {
		engine =  (TDPlayerEngine) s;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		String name = playerName.getText();
		playerName.setText(INITIAL);
		highScoreDisplay.append(name + SPACER_FOR_HIGH_SCORE + highScore +  NEWLINE);
	}

}

package main.java.player.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import main.java.player.TDPlayerEngine;
import main.java.player.util.Observing;
import main.java.player.util.Subject;

/**
 * Card to keep track of, display, and save high scores
 * @author Michael Han
 *
 */
@SuppressWarnings("serial")
public class HighScoreCard extends JPanel implements Observing, ActionListener {

	public static final String INITIAL_PLAYER_NAME = "enter name after deleting this message";
	public static final String INITIAL_HIGH_SCORE_BOX = "HIGHSCORE\n";
	public static final String SPACER_FOR_HIGH_SCORE = ": ";
	public static final String NEWLINE = "\n";
	private JTextField playerName;
	@SuppressWarnings("unused")
	private TDPlayerEngine engine;
	private JTextArea highScoreDisplay;
	private int highScore;

	public HighScoreCard(){
		setLayout(new GridBagLayout());
		playerName = new JTextField(20);
		playerName.addActionListener(this);
		playerName.setText(INITIAL_PLAYER_NAME);
		highScoreDisplay = new JTextArea(20,50);
		highScoreDisplay.setEditable(false);
		highScoreDisplay.setText(INITIAL_HIGH_SCORE_BOX);
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
		playerName.setText(INITIAL_PLAYER_NAME);
		highScoreDisplay.append(name + SPACER_FOR_HIGH_SCORE + highScore +  NEWLINE);
	}

	@Override
	public void setSubject(List<Subject> s) {
		// TODO Auto-generated method stub
		
	}

}

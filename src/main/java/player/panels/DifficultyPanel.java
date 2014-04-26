package main.java.player.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import main.java.player.ITDPlayerEngine;

/**
 * Panel to allow user to choose difficulty
 * @author Michael Han
 *
 */
@SuppressWarnings("serial")
public class DifficultyPanel extends JPanel{
	public static final String EASY = "easy";
	public static final String MEDIUM = "medium";
	public static final String HARD = "hard";
	
	@SuppressWarnings("unused")
	private ITDPlayerEngine engine;
	private ButtonGroup difficultyRadioButtonGroup;
	
	public DifficultyPanel(ITDPlayerEngine engineInit){
		engine = engineInit;
		difficultyRadioButtonGroup = new ButtonGroup();
		addRadioButtons();
	}

	private void addRadioButtons(){
		//need gameengine to agree that default is easy mode
		JRadioButton easyButton = new JRadioButton(EASY);
		easyButton.setActionCommand(EASY);
		easyButton.setMnemonic(KeyEvent.VK_E);
		easyButton.setSelected(true);

		JRadioButton mediumButton = new JRadioButton(MEDIUM);
		mediumButton.setActionCommand(MEDIUM);
		mediumButton.setMnemonic(KeyEvent.VK_M);

		JRadioButton hardButton = new JRadioButton(HARD);
		hardButton.setActionCommand(HARD);
		hardButton.setMnemonic(KeyEvent.VK_H);

		difficultyRadioButtonGroup.add(easyButton);
		difficultyRadioButtonGroup.add(mediumButton);
		difficultyRadioButtonGroup.add(hardButton);

		easyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("easy");
				//frame.pack();
			}
		});

		mediumButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("medium");
				//frame.pack();
			}
		});

		hardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("hard");
				//frame.pack();
			}
		});
		
		add(easyButton);
		add(mediumButton);
		add(hardButton);
	}
}
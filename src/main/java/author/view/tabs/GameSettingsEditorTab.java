package main.java.author.view.tabs;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import main.java.author.controller.MainController;
import main.java.author.controller.TabController;

public class GameSettingsEditorTab extends EditorTab{

	private JPanel settingsPanel = new JPanel(new GridLayout(0, 1));
	private JComboBox gameModeList;
	private JComboBox gameDifficultyList;

	private JLabel levelsPerGameLabel;
	private JLabel livesLabel;
	private JLabel beginningMoneyLabel;

	private JTextField levelsPerGameField;
	private JTextField livesField;
	private JTextField beginningMoneyField;

	private static final String LEVELS_STRING = "Levels Per Game: ";
	private static final String LIVES_STRING = "Lives: ";
	private static final String WAVES_STRING = "Waves Per Level: ";
	private static final String ENEMIES_STRING = "Enemies Per Wave: ";
	private static final String MONEY_STRING = "Beginning Money: ";

	String[] GAME_MODE_STRINGS = {"Survival Mode", "Boss Mode"};
	String[] GAME_DIFFICULTY_STRINGS = {"Easy", "Medium", "Hard"};

	private NumberFormat numberFormat;

	private JButton submitButton;
	private JButton musicButton;

	private JFileChooser fileChooser;

	public GameSettingsEditorTab(TabController gameSettingsController){
		super(gameSettingsController);
		createSettingsPanel();
		add(settingsPanel, BorderLayout.CENTER);
	}

	private void createSettingsPanel() {

		settingsPanel.setLayout(new BorderLayout());

		settingsPanel.add(makeDropDownMenus(), BorderLayout.NORTH);
		settingsPanel.add(makeAttributesPane(), BorderLayout.SOUTH);

		settingsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
	}

	private JComponent makeDropDownMenus(){
		JPanel dropDownMenus = new JPanel(new GridLayout(0, 1));
		dropDownMenus.setLayout(new BorderLayout());

		gameModeList = new JComboBox(GAME_MODE_STRINGS); 
		gameModeList.setSelectedIndex(1);
		gameModeList.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				// Would probably switch between the specific attributes to display or just make unique panels for each as classes.
				// and then do some more logic outside of this action listener to decide what to display.
			}

		});

		gameDifficultyList = new JComboBox(GAME_DIFFICULTY_STRINGS);
		gameDifficultyList.setSelectedIndex(1);
		gameDifficultyList.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}

		});

		dropDownMenus.add(gameModeList, BorderLayout.NORTH);
		dropDownMenus.add(gameDifficultyList, BorderLayout.SOUTH);

		return dropDownMenus;
	}

	private JComponent makeAttributesPane(){
		JPanel attributes = new JPanel(new GridLayout(0, 1));

		attributes.setLayout(new BorderLayout());
		attributes.add(makeLabelPane(), BorderLayout.WEST);
		attributes.add(makeFieldPane(), BorderLayout.EAST);

		attributes.add(makeButtons(), BorderLayout.SOUTH);

		return attributes;
	}

	private JComponent makeButtons(){

		JPanel buttons = new JPanel(new GridLayout(0, 1));

		submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}

		});

		musicButton = new JButton("Choose Music");
		musicButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				// Get it to open up on the right file menu
				fileChooser = new JFileChooser("main/resources");
				FileNameExtensionFilter filter = new FileNameExtensionFilter("WAV files", "wav");
				fileChooser.setFileFilter(filter);
				int returnVal = fileChooser.showOpenDialog(GameSettingsEditorTab.this);
				
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					File chosenFile = fileChooser.getSelectedFile();
					String absolutePath = chosenFile.getAbsolutePath();
						try {
							
						} catch (Exception e1) {
							e1.printStackTrace();
						}
				}

			}

		});

		buttons.add(musicButton, BorderLayout.NORTH);
		buttons.add(submitButton, BorderLayout.SOUTH);

		return buttons;

	}

	private JComponent makeLabelPane(){
		levelsPerGameLabel = new JLabel(LEVELS_STRING);
		livesLabel = new JLabel(LIVES_STRING);
		beginningMoneyLabel = new JLabel(MONEY_STRING);

		JPanel labels = new JPanel(new GridLayout(0, 1));

		labels.add(levelsPerGameLabel);
		labels.add(livesLabel);
		labels.add(beginningMoneyLabel);

		return labels;
	}

	private JComponent makeFieldPane(){
		levelsPerGameField = new JFormattedTextField(numberFormat);
		livesField = new JFormattedTextField(numberFormat);
		beginningMoneyField = new JFormattedTextField(numberFormat);
		
		levelsPerGameField.setColumns(10);
		
		levelsPerGameField.setText("10");
		livesField.setText("5");
		beginningMoneyField.setText("5000");

		JPanel fields = new JPanel(new GridLayout(0, 1));

		fields.add(levelsPerGameField);
		fields.add(livesField);
		fields.add(beginningMoneyField);

		return fields;
	}


}

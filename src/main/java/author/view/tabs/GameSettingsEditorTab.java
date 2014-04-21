package main.java.author.view.tabs;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.NumberFormat;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.filechooser.FileNameExtensionFilter;

import main.java.author.controller.TabController;
import main.java.author.controller.tabbed_controllers.GameSettingsController;
import main.java.author.view.global_constants.FontConstants;
import main.java.schema.GameSchema;

/**
 * The tab that deals with the Game Settings attributes
 */
public class GameSettingsEditorTab extends EditorTab{

	private JPanel settingsPanel = new JPanel(new GridLayout(0, 1));
	
	private JComboBox gameModeList;
	private JComboBox gameDifficultyList;

	private JLabel livesLabel;
	private JLabel beginningMoneyLabel;

	private JSpinner livesSpinner;
	private JSpinner beginningMoneySpinner;

	private List<JSpinner> spinnerFields;

	private static final String LIVES_STRING = "Lives: ";
	private static final String MONEY_STRING = "Beginning Money: ";
	
	private static final int LIVES_DEFAULT = 5;
	private static final int MONEY_DEFAULT = 500;
	
	private static final int EASY_DIFFICULTY_VALUE = 1;
	private static final int MEDIUM_DIFFICULTY_VALUE = 2;
	private static final int HARD_DIFFICULTY_VALUE = 3;
	
	private static final int SURVIVAL_MODE_VALUE = 1;
	private static final int BOSS_MODE_VALUE = 0;

	String[] GAME_MODE_STRINGS = {"Survival Mode", "Boss Mode"};
	String[] GAME_DIFFICULTY_STRINGS = {"Easy", "Medium", "Hard"};

	private NumberFormat numberFormat;

	private JButton submitButton;
	private JButton musicButton;

	private JFileChooser fileChooser;

	private GameSchema gameSchema;

	private GameSettingsTabContentCreator contentCreator;

	/**
	 * @param gameSettingsController
	 * The constructor for the Game Settings Editor Tab
	 */
	public GameSettingsEditorTab(TabController gameSettingsController){
		super(gameSettingsController);
		contentCreator = new GameSettingsTabContentCreator();
		add(contentCreator.createSettingsPanel(), BorderLayout.CENTER);
	}

	@Override
	public void saveTabData() {

		GameSettingsController controller = (GameSettingsController) myController;

		gameSchema = new GameSchema();

		gameSchema.addAttribute(GameSchema.LIVES, (Integer) livesSpinner.getValue());
		gameSchema.addAttribute(GameSchema.MONEY, (Integer) beginningMoneySpinner.getValue());

		if(gameDifficultyList.getSelectedItem().equals("Easy")){
			gameSchema.addAttribute(GameSchema.LEVELDIFFICULTY, EASY_DIFFICULTY_VALUE);
		}
		else if(gameDifficultyList.getSelectedItem().equals("Medium")){
			gameSchema.addAttribute(GameSchema.LEVELDIFFICULTY, MEDIUM_DIFFICULTY_VALUE);
		}
		else{
			gameSchema.addAttribute(GameSchema.LEVELDIFFICULTY, HARD_DIFFICULTY_VALUE);
		}
		
		if(gameModeList.getSelectedItem().equals("Survival Mode")){
			gameSchema.addAttribute(GameSchema.ISSURVIVALMODE, SURVIVAL_MODE_VALUE);
		}
		else{
			gameSchema.addAttribute(GameSchema.ISSURVIVALMODE, BOSS_MODE_VALUE);
		}

		controller.addGameSettings(gameSchema);

	}

	/**
	 * Creates the contents of the Pane
	 */
	private class GameSettingsTabContentCreator{
		
		/**
		 * Creates the main panel where all of the JComponents that deal with Game Settings attributes go
		 * @return 
		 */
		private Component createSettingsPanel() {

			settingsPanel.setLayout(new BorderLayout());

			settingsPanel.add(makeDropDownMenus(), BorderLayout.NORTH);
			settingsPanel.add(makeAttributesPane(), BorderLayout.SOUTH);

			settingsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
			
			return settingsPanel;
			
		}

		/**
		 * @return
		 * Specifies the size and font of each JSpinner
		 */
		private JSpinner makeAttributeSpinner(){

			SpinnerModel model = new SpinnerNumberModel(1, 1, 1000, 1);
			JSpinner spinner = new JSpinner(model);

			spinner.setMaximumSize(new Dimension(200, spinner.getHeight()));

			Font bigFont = spinner.getFont().deriveFont(Font.PLAIN,
					FontConstants.X_LARGE_FONT_SIZE);
			spinner.setFont(bigFont);

			return spinner;

		}

		/**
		 * @return
		 * Makes the labels for the attributes that have corresponding JSpinners
		 */
		private JComponent makeLabelPane(){
			livesLabel = new JLabel(LIVES_STRING);
			beginningMoneyLabel = new JLabel(MONEY_STRING);

			JPanel labels = new JPanel(new GridLayout(0, 1));

			labels.add(livesLabel);
			labels.add(beginningMoneyLabel);

			return labels;
		}

		/**
		 * @return
		 * Makes the area where the user can edit attribute fields
		 */
		private JComponent makeFieldPane(){

			JPanel fields = new JPanel(new GridLayout(0, 1));

			livesSpinner = makeAttributeSpinner();
			beginningMoneySpinner = makeAttributeSpinner();

			livesSpinner.setValue(LIVES_DEFAULT);
			beginningMoneySpinner.setValue(MONEY_DEFAULT);

			fields.add(livesSpinner);
			fields.add(beginningMoneySpinner);

			return fields;

		}

		/**
		 * @return
		 * Makes the drop down menus for game mode and game difficulty
		 */
		private JComponent makeDropDownMenus(){
			JPanel dropDownMenus = new JPanel(new GridLayout(0, 1));
			dropDownMenus.setLayout(new BorderLayout());

			gameModeList = new JComboBox(GAME_MODE_STRINGS); 
			gameModeList.setSelectedIndex(1);

			gameDifficultyList = new JComboBox(GAME_DIFFICULTY_STRINGS);
			gameDifficultyList.setSelectedIndex(1);

			dropDownMenus.add(gameModeList, BorderLayout.NORTH);
			dropDownMenus.add(gameDifficultyList, BorderLayout.SOUTH);

			return dropDownMenus;
		}

		/**
		 * @return
		 * Makes the attributes pane, which holds the labels and fields panes
		 */
		private JComponent makeAttributesPane(){
			JPanel attributes = new JPanel(new GridLayout(0, 1));

			attributes.setLayout(new BorderLayout());
			attributes.add(makeLabelPane(), BorderLayout.WEST);
			attributes.add(makeFieldPane(), BorderLayout.EAST);

			attributes.add(makeButtons(), BorderLayout.SOUTH);

			return attributes;
		}

		/**
		 * @return
		 * Makes various JButtons
		 */
		private JComponent makeButtons(){

			JPanel buttons = new JPanel(new GridLayout(0, 1));

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

			buttons.add(musicButton, BorderLayout.CENTER);

			return buttons;

		}

	}

}

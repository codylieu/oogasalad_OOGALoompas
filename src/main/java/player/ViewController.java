package main.java.player;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ResourceBundle;
import java.util.Set;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import main.java.player.dlc.RepositoryViewer;
import main.java.player.panels.FileChooserActionListener;
import main.java.player.panels.GameInfoPanel;
import main.java.player.panels.HelpTextPanel;
import main.java.player.panels.HighScoreCard;
import main.java.player.panels.ObjectChooser;
import main.java.player.panels.ObservingPanel;
import main.java.player.panels.TowerDescriptionArea;
import main.java.player.panels.UnitInfoPanel;
import main.java.player.panels.WelcomeButtonPanelListener;
import main.java.player.util.MultipleMethodAction;
import main.java.player.util.Sound;
import main.java.player.util.Subject;
import main.java.reflection.MethodAction;
import net.lingala.zip4j.exception.ZipException;

/**
 * The Swing wrapper that contains all the buttons,
 * and the TDPlayerEngine
 * @author Kevin
 *
 */

@SuppressWarnings("serial")
public class ViewController implements Serializable {

	public static final String GUI_PROPERTY_FILEPATH = "GUI";
	public static final String SET_CURRENT_TOWER_TYPE_METHID_NAME = "setCurrentTowerType";
	public static final String LOAD_BLUEPRINT_FILE_METHOD_NAME = "loadBlueprintFile";
	public static final int WELCOME_LABEL_FONT = 32;
	public static final String SANS_SERIF_FONT = "SansSerif";
	public static final String SPEED_UP_METHOD_NAME = "speedUp";
	public static final String SLOW_DOWN_METHOD_NAME = "slowDown";
	public static final String TOGGLE_ADD_TOWER_METHOD_NAME = "toggleAddTower";
	public static final String TOGGLE_SOUND_METHOD_NAME = "toggleSound";
	public static final String TOGGLE_RUNNING_METHOD_NAME = "toggleRunning";
	public static final String SHOW_CARD_VARIABLE = "showCard";
	public static final String QUIT_METHOD_NAME = "quit";
	public static final String QUIT_TEXT = "QUIT_TEXT";
	public static final String MAIN_MENU_TEXT = "MAIN_MENU_TEXT";
	public static final String CREDITS = "CREDITS";
	public static final String HELP = "HELP";
	public static final String MUSIC_TEXT = "MUSIC_TEXT";
	public static final String SOUND = "SOUND";
	public static final String SOUND_ONOFF_TEXT = "SOUND_ONOFF_TEXT";
	public static final String ADD_TOWER_TEXT = "ADD_TOWER_TEXT";
	public static final String SLOW_DOWN_TEXT = "SLOW_DOWN_TEXT";
	public static final String SPEED_UP_TEXT = "SPEED_UP_TEXT";
	public static final String PLAY_PAUSE_TEXT = "PLAY_PAUSE_TEXT";
	public static final String WELCOME_LABEL_TEXT = "WELCOME_LABEL_TEXT";
	public static final String LOAD_GAME_TEXT = "LOAD_GAME_TEXT";
	public static final String FILE_LABEL = "FILE_LABEL";
	public static final String LANGUAGES_LIST = "LanguageList";
	public static final String DEFAULT_RESOURCE_PACKAGE = "main.resources.";
	public static final String ENGLISH = "English";
	public static final String LANGUAGES = "LANGUAGES";
	public static final int BUTTON_PADDING = 10;
	public static final String USER_DIR = "user.dir";
	public static final String DEFAULT_MUSIC_PATH = "src/main/resources/backgroundmusic.wav";
	public static final String WELCOME_CARD = "welcomeCard";
	public static final String GAME_CARD = "gameCard";
	public static final String OPTION_CARD = "optionCard";
	public static final String HELP_CARD = "helpCard";	
	public static final String CREDITS_CARD = "creditsCard";
	public static final String HIGH_SCORE_CARD = "highScoreCard";
	
	public static final String RESOURCE_PATH = "src/main/resources/";

	private JFrame frame;
	private JPanel cards;
	private CardLayout cardLayout;
	private static final JFileChooser fileChooser = new JFileChooser(System.getProperties().getProperty(USER_DIR));
	private ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + GUI_PROPERTY_FILEPATH);
	private ResourceBundle myLanguageResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + ENGLISH);
	private ResourceBundle myLanguagesList = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + LANGUAGES_LIST);
	private ITDPlayerEngine engine;
	private Sound song;
	private boolean soundOn;
	private ObjectChooser towerChooser;
	private HighScoreCard highScoreCard;
	private String chosenLanguage;
	private ObservingPanel towerDescriptionArea;

	/**
	 * initializeEngine() must be called first
	 * Many other modules require the engine reference to exist
	 */
	public ViewController(){
		initializeEngine(showBlueprintPrompt());
		initLanguage();
		//showLanguagePrompt();
		initSong();
		makeFrame();
		makeCards();
		addWelcomeCard();
		addGameCard();
		addHelpCard();
		addOptionsCard();
		addCreditsCard();
		addHighScoreCard();
		show();
	}

	private void makeAndAddCards(){
		makeCards();
		addWelcomeCard();
		addGameCard();
		addHelpCard();
		addOptionsCard();
		addCreditsCard();
		updateHighScore();
		//addHighScoreCard();
		//want to maintain highscore card but adding new one will change it, also need to fix with language
	}

	private void initLanguage(){
		chosenLanguage = ENGLISH;
		myLanguageResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + chosenLanguage);
	}

	private String showBlueprintPrompt() {
		int response0 = JOptionPane.showConfirmDialog(null, "Load local file? (No = Load Internet File, Cancel = Exit)");
		if (response0 == JOptionPane.YES_OPTION) {
			int response = fileChooser.showOpenDialog(null);
			if(response == JFileChooser.APPROVE_OPTION){
				File file = fileChooser.getSelectedFile();
				return file.getAbsolutePath();
			}
			else {
				System.exit(0);
				return "";
			}
		}
		else if (response0 == JOptionPane.NO_OPTION) {
			return new RepositoryViewer(myLanguageResources.getString("LOAD_LIBRARY_TEXT"), engine).getLocalURL();
		}
		else {
			System.exit(0);
			return "";
		}
	}

	private void initSong(){
		try {
			if (engine.getPathToMusic() != null) {
				song = new Sound(RESOURCE_PATH + engine.getPathToMusic());
			}
		} catch (LineUnavailableException | IOException
				| UnsupportedAudioFileException e) {
			JOptionPane.showMessageDialog(null, "Music file not found.");
		}
		soundOn = false;
	}

	public void showCard(String cardName){
		cardLayout.show(cards,  cardName);
	}

	private void makeFrame() {
		frame = new JFrame();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setJMenuBar(makeMenuBar());
	}

	private JMenu makeFileMenu(){
		JMenu files = new JMenu(myLanguageResources.getString(FILE_LABEL));
		files.add(new FileChooserActionListener(engine, LOAD_BLUEPRINT_FILE_METHOD_NAME, fileChooser, myLanguageResources.getString(LOAD_GAME_TEXT)));
		//files.add(new RepositoryViewer(myLanguageResources.getString("LOAD_LIBRARY_TEXT"), engine));
		return files;
	}

	private JMenu makeLanguagesMenu(){
		JMenu languagesMenu = new JMenu(myLanguageResources.getString(LANGUAGES));
		for(String s: myLanguagesList.keySet()){
			JMenuItem temp = new JMenuItem(s);
			temp.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					chosenLanguage = e.getActionCommand();
					myLanguageResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + chosenLanguage);
					frame.dispose();
					makeFrame();
					makeAndAddCards();
					show();			
				}				
			});
			languagesMenu.add(temp);
		}
		return languagesMenu;
	}

	private JMenuBar makeMenuBar(){
		JMenuBar menuBar = new JMenuBar();
		//menuBar.add(makeFileMenu());
		menuBar.add(makeLanguagesMenu());
		return menuBar;
	}

	private void makeCards() {
		cards = new JPanel(cardLayout = new CardLayout());
	}

	private void addWelcomeCard() {
		JPanel welcomeCard = new JPanel();
		welcomeCard.setLayout(new BoxLayout(welcomeCard, BoxLayout.Y_AXIS));
		welcomeCard.add(makeWelcomeLabel());
		welcomeCard.add(makeWelcomeButtonPanel());
		cards.add(welcomeCard, WELCOME_CARD);
	}

	private JLabel makeWelcomeLabel() {
		JLabel welcomeLabel = new JLabel(myLanguageResources.getString(WELCOME_LABEL_TEXT));
		welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		welcomeLabel.setFont(new Font(SANS_SERIF_FONT, Font.PLAIN, WELCOME_LABEL_FONT));
		return welcomeLabel;
	}

	private JPanel makeWelcomeButtonPanel() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));	

		WelcomeButtonPanelListener listener = new WelcomeButtonPanelListener(myResources, this, myLanguageResources);
		Set<String> keys = myResources.keySet();
		for(String s: keys){
			JButton temp = new JButton(myLanguageResources.getString(s));
			temp.setAlignmentX(Component.CENTER_ALIGNMENT);
			temp.addActionListener(listener);
			buttonPanel.add(temp);
			buttonPanel.add(Box.createRigidArea(new Dimension(0, BUTTON_PADDING)));
		}

		JButton exitButton = makeQuitButton();
		buttonPanel.add(exitButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(0, BUTTON_PADDING)));
		return buttonPanel;
	}

	private void addGameCard() {
		JPanel gameCard = new JPanel();
		gameCard.setLayout(new GridBagLayout());

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 0;
		gameCard.add((Component) engine, constraints);

		constraints.gridx = 1;
		constraints.gridy = 0;
		gameCard.add(makeGameActionPanel(), constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 1;
		gameCard.add(makeGameInfoPanel(), constraints);

		constraints.gridx = 1;
		constraints.gridy = 1;
		gameCard.add(makeUnitInfoPanel(), constraints);

		cards.add(gameCard, GAME_CARD);
	}

	private ITDPlayerEngine initializeEngine(String pathToBlueprint) {
		try {
			engine = new TDPlayerEngine(pathToBlueprint, this);
		} catch (ClassNotFoundException | IOException | ZipException e) {
			JOptionPane.showMessageDialog(frame, "Invalid file. Closing program.");
			System.exit(1);
		}
		engine.initModel();
		engine.stop();
		engine.toggleRunning();
		return engine;
	}

	private JPanel makeGameActionPanel() {
		JPanel gameButtonPanel = new JPanel();
		gameButtonPanel.setLayout(new GridLayout(10, 1));

		JButton mainMenuButton = makeMainMenuButton();

		JButton playResumeButton = new JButton(myLanguageResources.getString(PLAY_PAUSE_TEXT));
		playResumeButton.addActionListener(new MethodAction (engine, TOGGLE_RUNNING_METHOD_NAME));

		JButton speedUpButton = new JButton(myLanguageResources.getString(SPEED_UP_TEXT));
		speedUpButton.addActionListener(new MethodAction (engine, SPEED_UP_METHOD_NAME));

		JButton slowDownButton = new JButton(myLanguageResources.getString(SLOW_DOWN_TEXT));
		slowDownButton.addActionListener(new MethodAction (engine, SLOW_DOWN_METHOD_NAME));

		JButton quitButton = makeQuitButton();

		JButton addTowerButton = new JButton(myLanguageResources.getString(ADD_TOWER_TEXT));
		addTowerButton.addActionListener(new MethodAction (engine, TOGGLE_ADD_TOWER_METHOD_NAME));

		JButton soundButton = new JButton(myLanguageResources.getString(SOUND_ONOFF_TEXT));
		soundButton.addActionListener(new MethodAction (this, TOGGLE_SOUND_METHOD_NAME));

		towerChooser = new ObjectChooser(engine, "getPossibleTowers", SET_CURRENT_TOWER_TYPE_METHID_NAME);
		
		towerDescriptionArea = new TowerDescriptionArea(3, 20);
		towerDescriptionArea.addSubject((Subject) engine);
		engine.register(towerDescriptionArea);

		gameButtonPanel.add(mainMenuButton);
		gameButtonPanel.add(playResumeButton);
		gameButtonPanel.add(speedUpButton);
		gameButtonPanel.add(slowDownButton);
		gameButtonPanel.add(quitButton);
		gameButtonPanel.add(soundButton);
		gameButtonPanel.add(addTowerButton);
		gameButtonPanel.add(towerChooser);
		gameButtonPanel.add(towerDescriptionArea);
		return gameButtonPanel;
	}

	public void toggleSound(){
		soundOn = !soundOn;
		if(soundOn) {
			song.loop();
		}
		else {
			song.stop();
		}
	}

	private JPanel makeGameInfoPanel() {
		ObservingPanel gameInfoPanel = new GameInfoPanel();
		gameInfoPanel.addSubject((Subject) engine);
		engine.register(gameInfoPanel);
		return gameInfoPanel;
	}

	private JPanel makeUnitInfoPanel() {
		ObservingPanel unitInfoPanel = new UnitInfoPanel();
		unitInfoPanel.addSubject((Subject) engine);
		engine.register(unitInfoPanel);
		return unitInfoPanel;
	}

	//TODO: need to add when game ends to route to here, also need to work on saving the scores 
	private void addHighScoreCard(){
		highScoreCard = new HighScoreCard(engine, makeMainMenuButton(), myLanguageResources);
		cards.add(highScoreCard, HIGH_SCORE_CARD);
	}
	
	//call this method when route to high score card ==> so when thing pops up saying game won or lost 
	//when they click ok it should go to high score card and also reset engine
	private void updateHighScore(){
		cards.add(highScoreCard, HIGH_SCORE_CARD);
		highScoreCard.updateLabels(myLanguageResources);
		//engine.reset? or soemthing equivalent
	}

	public void handleEndGame(){
		highScoreCard.setHighScore();		
		engine.initModel();
		engine.stop();
		showCard(HIGH_SCORE_CARD);		
	}
	
	private void addOptionsCard() {
		JPanel optionCard = new JPanel();
		optionCard.setLayout(new GridBagLayout());

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 0;
		optionCard.add(makeMainMenuButton(), constraints);
		/*
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 1;
		optionCard.add(new InfoPanel(DIFFICULTY), constraints);

		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 2;
		optionCard.add(new DifficultyPanel(engine), constraints);
		 */
	//	constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 1;
		// need to make sound label be centered
		JLabel soundLabel = new JLabel(myLanguageResources.getString(SOUND));
		soundLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		optionCard.add(soundLabel, constraints);

		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 2;
		optionCard.add(makeSoundRadioButtonPanel(), constraints);

		cards.add(optionCard, OPTION_CARD);
	}


	private JPanel makeSoundRadioButtonPanel(){
		JPanel soundRadioButtonPanel = new JPanel();
		JCheckBox soundCheckBox = new JCheckBox(myLanguageResources.getString(MUSIC_TEXT));
		soundCheckBox.addActionListener(new MethodAction(this, TOGGLE_SOUND_METHOD_NAME));
		soundRadioButtonPanel.add(soundCheckBox);

		return soundRadioButtonPanel;
	}


	private void addHelpCard() {
		JPanel helpCard = new JPanel();
		helpCard.setLayout(new GridBagLayout());

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 1;
		helpCard.add(makeMainMenuButton(), constraints);

		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 0;
		helpCard.add(new HelpTextPanel(myLanguageResources.getString(HELP)), constraints);

		cards.add(helpCard, HELP_CARD);
	}

	private void addCreditsCard() {
		JTextArea creditsArea = new JTextArea(10,40);
		creditsArea.setEditable(false);
		creditsArea.append(myLanguageResources.getString(CREDITS));

		JPanel creditsCard = new JPanel();
		creditsCard.setLayout(new GridBagLayout());

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 1;
		creditsCard.add(makeMainMenuButton(), constraints);

		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 0;
		creditsCard.add(creditsArea, constraints);

		cards.add(creditsCard, CREDITS_CARD);
	}

	private JButton makeMainMenuButton() {
		JButton mainMenuButton = new JButton(myLanguageResources.getString(MAIN_MENU_TEXT));
		mainMenuButton.addActionListener(new MultipleMethodAction(new MethodAction(engine, TOGGLE_RUNNING_METHOD_NAME),new MethodAction(this, SHOW_CARD_VARIABLE, WELCOME_CARD)));
		//mainMenuButton.addActionListener(new MethodAction(engine, TOGGLE_RUNNING_METHOD_NAME));
		//mainMenuButton.addActionListener(new MethodAction(this, SHOW_CARD_VARIABLE, WELCOME_CARD));
		return mainMenuButton;
	}

	private JButton makeQuitButton(){
		JButton exitButton = new JButton(myLanguageResources.getString(QUIT_TEXT));
		exitButton.addActionListener(new MethodAction(this,QUIT_METHOD_NAME));
		exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		return exitButton;
	}

	public void quit(){
		System.exit(0);
	}

	private void show() {
		cardLayout.show(cards, WELCOME_CARD);
		frame.getContentPane().add(cards, BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
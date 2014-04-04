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
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

public class Player {

	public static final String LOAD_GAME_DATA = "Load Game Data";
	public static final String LOAD_LIBRARY = "Browse library";
	public static final String FILELABEL = "File";
	public static final String HELP = "Click on Play/Pause to begin game. Click to add towers. \nAdding towers uses up money. Right click on towers to sell. \nA proportion of the tower's original cost will be added to money";
	public static final String DIFFICULTY = "Difficulty";
	public static final String EASY = "Easy Mode";
	public static final String MEDIUM = "Medium Mode";
	public static final String HARD = "Hard Mode";
	public static final String SOUND = "Sound";
	public static final String ON = "On";
	public static final String OFF = "Off";
	public static final String CREDITS = "Game Authoring Environment\nGary Sheng, Cody Lieu, Stephen Hughes, Dennis Park\n\nGame Data\nIn-Young Jo, Jimmy Fang\n\nGame Engine\nDianwen Li, Austin Lu, Lawrence Lin, Jordan Ly\n\nGame Player\nMichael Han, Kevin Do";
	public static final int BUTTON_PADDING = 10;
	public static final String USER_DIR = "user.dir";	

	private JFrame frame;
	private JPanel cards;
	private CardLayout cardLayout;
	private static final JFileChooser fileChooser = new JFileChooser(System.getProperties().getProperty(USER_DIR));
	private ResourceBundle myResources = ResourceBundle.getBundle("main.resources.GUI");
	
	private TDPlayerEngine engine;
	
	public Player() {
		makeFrame();
		makeCards();
		addWelcomeCard();
		addGameCard();
		addHelpCard();
		addOptionsCard();
		addCreditsCard();
		show();
	}

	
	public void showCard(String cardName){
		cardLayout.show(cards,  cardName);
	}
	
	private void makeFrame() {
		frame = new JFrame();

		frame.setTitle("OOGA Loompas Tower Defense");
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setJMenuBar(makeMenuBar());
	}

	@SuppressWarnings("serial")
	private JMenu makeFileMenu(){
		JMenu files = new JMenu(FILELABEL);
		files.add(new AbstractAction(LOAD_GAME_DATA){
			public void actionPerformed(ActionEvent e){
				int response = fileChooser.showOpenDialog(null);
				if(response == JFileChooser.APPROVE_OPTION){
					File file = fileChooser.getSelectedFile();
					System.out.println("FILE CHOSEN: " + file.getName());
				}
			}
		});
		files.add(new RepositoryViewer(LOAD_LIBRARY));
		return files;
	}

	private JMenuBar makeMenuBar(){
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(makeFileMenu());
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
		cards.add(welcomeCard, "welcomeCard");
	}

	private JLabel makeWelcomeLabel() {
		JLabel welcomeLabel = new JLabel("Ooga Loompas Tower Defense");
		welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		welcomeLabel.setFont(new Font("SansSerif", Font.PLAIN, 32));
		return welcomeLabel;
	}

	private JPanel makeWelcomeButtonPanel() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));	

		WelcomeButtonPanelListener listener = new WelcomeButtonPanelListener(myResources, this);
		Set<String> keys = myResources.keySet();
		for(String s: keys){
			JButton temp = new JButton(s);
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
		gameCard.add(makeGamePanel(), constraints);

		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 1;
		constraints.gridy = 0;
		gameCard.add(makeGameButtonPanel(), constraints);

		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 1;
		gameCard.add(makeGameInfoPanel(), constraints);

		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 1;
		constraints.gridy = 1;
		gameCard.add(makeUnitInfoPanel(), constraints);

		cards.add(gameCard, "gameCard");
	}

	private TDPlayerEngine makeGamePanel() {
		/*JPanel gamePanel = new JPanel();
		gamePanel.setPreferredSize(new Dimension(600, 400));
		gamePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		return gamePanel;*/

		engine = new TDPlayerEngine();
		engine.stop();
		return engine;
	}

	private JPanel makeGameButtonPanel() {
		JPanel gameButtonPanel = new JPanel();
		gameButtonPanel.setLayout(new GridLayout(10, 1));

		JButton mainMenuButton = makeMainMenuButton();

		JButton playResumeButton = new JButton("Play/Pause");
		//playResumeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		playResumeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (engine.isRunning())
					engine.stop();
				else
					engine.start();
				frame.pack();
			}
		});
		/*JButton pauseButton = new JButton("Pause");
		//pauseButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		pauseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				engine.stop();
				frame.pack();
			}
		});*/
		JButton saveButton = new JButton("Save");
		//saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("save");
				frame.pack();
			}
		});
		JButton quitButton = makeQuitButton();
		JButton addTowerButton = new JButton("Add Tower");
		//addTowerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		addTowerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("add tower");
				frame.pack();
			}
		});
		gameButtonPanel.add(mainMenuButton);
		gameButtonPanel.add(playResumeButton);
		//gameButtonPanel.add(pauseButton);
		gameButtonPanel.add(saveButton);
		gameButtonPanel.add(quitButton);
		gameButtonPanel.add(addTowerButton);
		return gameButtonPanel;
	}

	private JPanel makeGameInfoPanel() {
		JPanel gamePanel = new JPanel();
		gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));
		JLabel pointsLabel = new JLabel("Points");
		JLabel resourcesLabel = new JLabel("Resources");
		JLabel levelLabel = new JLabel("Level/Wave");
		gamePanel.add(pointsLabel);
		gamePanel.add(resourcesLabel);
		gamePanel.add(levelLabel);
		return gamePanel;
	}

	private JPanel makeUnitInfoPanel() {
		JPanel unitInfoPanel = new JPanel();
		JLabel unitInfoLabel = new JLabel("this is some unit info");
		unitInfoPanel.add(unitInfoLabel);
		return unitInfoPanel;
	}

	private void addOptionsCard() {
		JPanel optionCard = new JPanel();
		optionCard.setLayout(new GridBagLayout());


		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 0;
		optionCard.add(makeMainMenuButton(), constraints);

		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 1;
		optionCard.add(makeDifficultyInfoPanel(), constraints);

		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 2;
		optionCard.add(makeDifficultyRadioButtonPanel(), constraints);

		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 3;
		optionCard.add(makeSoundInfoPanel(), constraints);

		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 4;
		optionCard.add(makeSoundRadioButtonPanel(), constraints);

		cards.add(optionCard, "optionCard");
	}

	private JPanel makeDifficultyInfoPanel(){
		JPanel difficultyInfoPanel = new JPanel();
		JLabel difficultyInfoLabel = new JLabel(DIFFICULTY);
		difficultyInfoPanel.add(difficultyInfoLabel);
		return difficultyInfoPanel;
	}

	private JPanel makeDifficultyRadioButtonPanel(){
		JPanel difficultyRadioButtonPanel = new JPanel();

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

		ButtonGroup difficultyRadioButtonGroup = new ButtonGroup();
		difficultyRadioButtonGroup.add(easyButton);
		difficultyRadioButtonGroup.add(mediumButton);
		difficultyRadioButtonGroup.add(hardButton);

		easyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("easy");
				frame.pack();
			}
		});

		mediumButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("medium");
				frame.pack();
			}
		});

		hardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("hard");
				frame.pack();
			}
		});

		difficultyRadioButtonPanel.add(easyButton);
		difficultyRadioButtonPanel.add(mediumButton);
		difficultyRadioButtonPanel.add(hardButton);
		return difficultyRadioButtonPanel;
	}

	private JPanel makeSoundInfoPanel(){
		JPanel soundInfoPanel = new JPanel();
		JLabel soundInfoLabel = new JLabel(SOUND);
		soundInfoPanel.add(soundInfoLabel);
		return soundInfoPanel;
	}

	private JPanel makeSoundRadioButtonPanel(){
		JPanel soundRadioButtonPanel = new JPanel();

		JRadioButton onButton = new JRadioButton(ON);
		onButton.setActionCommand(ON);
		onButton.setMnemonic(KeyEvent.VK_O);
		onButton.setSelected(true);

		JRadioButton offButton = new JRadioButton(OFF);
		offButton.setActionCommand(OFF);
		offButton.setMnemonic(KeyEvent.VK_F);

		ButtonGroup soundRadioButtonGroup = new ButtonGroup();
		soundRadioButtonGroup.add(onButton);
		soundRadioButtonGroup.add(offButton);

		onButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(ON);
				frame.pack();
			}
		});

		offButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(OFF);
				frame.pack();
			}
		});

		soundRadioButtonPanel.add(onButton);
		soundRadioButtonPanel.add(offButton);
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
		helpCard.add(makeHelpInfoPanel(), constraints);

		cards.add(helpCard, "helpCard");
	}

	private JPanel makeHelpInfoPanel(){
		JPanel helpInfoPanel = new JPanel();
		JTextArea helpArea = new JTextArea(10,40);
		helpArea.setEditable(false);
		helpArea.append(HELP);
		helpInfoPanel.add(helpArea, BorderLayout.CENTER);
		return helpInfoPanel;
	}
	private void addCreditsCard() {
		JTextArea creditsArea = new JTextArea(10,40);
		creditsArea.setEditable(false);
		creditsArea.append(CREDITS);

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

		cards.add(creditsCard, "creditsCard");
	}

	private JButton makeMainMenuButton() {
		JButton mainMenuButton = new JButton("Main Menu");
		//mainMenuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		mainMenuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cards, "welcomeCard");
				frame.pack();
			}
		});

		return mainMenuButton;
	}
	
	private JButton makeQuitButton(){
		JButton exitButton = new JButton("Quit");
		exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				frame.pack();
			}
		});
		return exitButton;
	}

	private void show() {
		cardLayout.show(cards, "welcomeCard");
		frame.getContentPane().add(cards, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		new Player();
	}
}

package main.java.player;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Player {
	
	private JFrame frame;
	private JPanel cards;
	private CardLayout cardLayout;
	
	public static String CREDITS = "Game Authoring Environment\nGary Sheng, Cody Lieu, Stephen Hughes, Dennis Park\n\nGame Data\nIn-Young Jo, Jimmy Fang\n\nGame Engine\nDianwen Li, Austin Lu, Lawrence Lin, Jordan Ly\n\nGame Player\nMichael Han, Kevin Do";
	public static int BUTTON_PADDING = 10;
	
	public Player() {
		makeFrame();
		makeCards();
		addWelcomeCard();
		addGameCard();
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
		
		JButton startGameButton = new JButton("Start Game");
		startGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		startGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cards, "gameCard");
				frame.pack();
			}
		});
		JButton continueButton = new JButton("Continue");
		continueButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		continueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("continue");
				frame.pack();
			}
		});
		JButton helpButton = new JButton("Help");
		helpButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		helpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("help");
				frame.pack();
			}
		});
		JButton optionsButton = new JButton("Options");
		optionsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		optionsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("options");
				frame.pack();
			}
		});
		JButton creditsButton = new JButton("Credits");
		creditsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		creditsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cards, "creditsCard");
				frame.pack();
			}
		});
		buttonPanel.add(startGameButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(0, BUTTON_PADDING)));
		buttonPanel.add(continueButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(0, BUTTON_PADDING)));
		buttonPanel.add(helpButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(0, BUTTON_PADDING)));
		buttonPanel.add(optionsButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(0, BUTTON_PADDING)));
		buttonPanel.add(creditsButton);
		
		return buttonPanel;
	}
	
	private void addGameCard() {
		JPanel gameCard = new JPanel();
		gameCard.add(makeGameButtonPanel());
		gameCard.add(makeGameInfoPanel());
		gameCard.add(makeUnitInfoPanel());
		cards.add(gameCard, "gameCard");
	}
	
	private JPanel makeGameButtonPanel() {
		JPanel gameButtonPanel = new JPanel();
		JButton mainMenuButton = new JButton("Main Menu");
		mainMenuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cards, "welcomeCard");
				frame.pack();
			}
		});
		JButton addTowerButton = new JButton("Add Tower");
		addTowerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("add tower");
				frame.pack();
			}
		});
		JButton playResumeButton = new JButton("Play/Resume");
		playResumeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("play resume");
				frame.pack();
			}
		});
		JButton pauseButton = new JButton("Pause");
		pauseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("pause");
				frame.pack();
			}
		});
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("save");
				frame.pack();
			}
		});
		JButton quitButton = new JButton("Quit");
		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("quit");
				frame.pack();
			}
		});
		gameButtonPanel.add(mainMenuButton);
		gameButtonPanel.add(addTowerButton);
		gameButtonPanel.add(playResumeButton);
		gameButtonPanel.add(pauseButton);
		gameButtonPanel.add(saveButton);
		gameButtonPanel.add(quitButton);
		return gameButtonPanel;
	}
	
	private JPanel makeGameInfoPanel() {
		JPanel gamePanel = new JPanel();
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
	
	private void addCreditsCard() {
		JPanel creditsCard = new JPanel();
		JTextArea creditsArea = new JTextArea(10,40);
		creditsArea.setEditable(false);
		creditsArea.append(CREDITS);
		creditsCard.add(creditsArea, BorderLayout.CENTER);
		cards.add(creditsCard, "creditsCard");
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

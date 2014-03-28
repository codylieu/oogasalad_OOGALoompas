package main.player;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Player {
	
	private JFrame frame;
	private JPanel cards;
	private CardLayout cardLayout;
	
	public static String CREDITS = "Game Authoring Environment\nGary Sheng, Cody Lieu, Stephen Hughes, Dennis Park\n\nGame Data\nIn-Young Jo, Jimmy Fang\n\nGame Engine\nDianwen Li, Austin Lu, Lawrence Lin, Jordan Ly\n\nGame Player\nMichael Han, Kevin Do";
	
	public Player() {
		makeFrame();
		makecards();
		addWelcomeCard();
		addGameCard();
		addCreditsCard();
		show();
	}
	
	private void makeFrame() {
		frame = new JFrame();
		
		frame.setTitle("OOGA Loompas Tower Defense");
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void makecards() {
		cards = new JPanel(cardLayout = new CardLayout());
	}
	
	private void addWelcomeCard() {
		JPanel welcomeCard = new JPanel();
		JButton startGameButton = new JButton("Start Game");
		startGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cards, "gameCard");
				frame.pack();
			}
		});
		JButton continueButton = new JButton("Continue");
		continueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("continue");
				frame.pack();
			}
		});
		JButton helpButton = new JButton("Help");
		helpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("help");
				frame.pack();
			}
		});
		JButton optionsButton = new JButton("Options");
		optionsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("options");
				frame.pack();
			}
		});
		JButton creditsButton = new JButton("Credits");
		creditsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cards, "creditsCard");
				frame.pack();
			}
		});
		welcomeCard.add(startGameButton);
		welcomeCard.add(continueButton);
		welcomeCard.add(helpButton);
		welcomeCard.add(optionsButton);
		welcomeCard.add(creditsButton);
		cards.add(welcomeCard, "welcomeCard");
	}
	
	private void addGameCard() {
		JPanel gameCard = new JPanel();
		JButton addTowerButton = new JButton("Add Tower");
		gameCard.add(addTowerButton);
		cards.add(gameCard, "gameCard");
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

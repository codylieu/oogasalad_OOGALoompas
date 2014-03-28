package main.player;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Player {
	
	private JFrame frame;
	private JPanel cards;
	private CardLayout cardLayout;
	
	public Player() {
		makeFrame();
		makecards();
		addWelcomeCard();
		//addGameCard();
		show();
	}
	
	private void makeFrame() {
		frame = new JFrame();
		
		frame.setTitle("OOGA Loompas Tower Defense");
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
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
				cardLayout.show(frame, "welcome");
			}
		});
		welcomeCard.add(startGameButton);
		cardLayout.addLayoutComponent(welcomeCard, "welcomeCard");
	}
	
	private void addGameCard() {
		JPanel gameCard = new JPanel();
		cardLayout.addLayoutComponent(gameCard, "gameCard");
	}
	
	private void show() {
		cardLayout.show(cards, "welcomeCard");
		frame.getContentPane().add(cards, BorderLayout.CENTER);
		frame.pack();
	}
	
	public static void main(String[] args) {
		new Player();
	}
}

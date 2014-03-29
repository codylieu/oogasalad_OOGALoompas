package main.java.author.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class AuthoringView extends JFrame {

	public AuthoringView() {
		getContentPane().add(new Canvas(), BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		pack();
		setVisible(true);
	}
	
	public static void main (String [] args) {
		new AuthoringView();
	}
	
}



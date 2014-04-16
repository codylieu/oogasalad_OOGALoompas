package main.java.author.view.tabs.wave;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Main extends JFrame {
	final JButton b = new JButton("Add");
	int size = 10;
	public Main() {
		setSize(300, 150);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		add(b);
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				b.setFont(new Font("Dialog", Font.PLAIN, ++size));
				b.revalidate();
			}
		});
		setVisible(true);
	}

	public static void main(String[] args) {
		new Main();    
	}
}
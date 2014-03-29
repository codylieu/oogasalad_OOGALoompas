package main.java.author.view.menubar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;


public class LevelMenuItem extends JMenuItem {
	/**
	 * Creates a new tab with its own world when pressed
	 */
	LevelMenuItem(int index){
		super("Level " + index);
		addActionListener(new LaunchLevelActionListener());
	}
	class LaunchLevelActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
		}
	}
}
package main.java.author.view.menubar;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;


public class NewLevelMenuItem extends JMenuItem {
	
	public int index = 1;
	/**
	 * Creates a new tab with its own world when pressed
	 */
	NewLevelMenuItem(){
		super("New Level");
		addActionListener(new LaunchNewLevelActionListener());
	}
	class LaunchNewLevelActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			add(new LevelMenuItem(index));
			index++;
		}

	}
}
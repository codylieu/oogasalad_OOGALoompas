package main.java.author.view.menubar;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JMenuItem;

public class SaveMenuItem extends JMenuItem {

	SaveMenuItem(){
		super("Save");
		addActionListener(new LaunchSaveActionListener());
	}
	class LaunchSaveActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
}
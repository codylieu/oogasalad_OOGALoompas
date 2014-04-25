package main.java.author.view.menubar;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JMenuItem;

public class LoadMenuItem extends JMenuItem {

	LoadMenuItem(){
		super("Load");
		addActionListener(new LaunchLoadActionListener());
	}
	class LaunchLoadActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
}

package main.java.author.view.menubar;

import javax.swing.JMenuBar;

public class BasicMenuBar extends JMenuBar {
	/**
	 * The MenuBar contains all the super menus
	 */
	public BasicMenuBar(){
		add(new FileMenu());
		add(new HelpMenu());
	}
}

package main.java.author.view.menubar;

import javax.swing.JMenuBar;

public class MenuBar extends JMenuBar {
	/**
	 * The MenuBar contains all the super menus
	 */
	public MenuBar(){
		add(new FileMenu());
		add(new HelpMenu());
	}
}

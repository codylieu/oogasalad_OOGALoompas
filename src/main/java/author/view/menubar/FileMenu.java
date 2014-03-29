package main.java.author.view.menubar;

import javax.swing.JMenu;


public class FileMenu extends JMenu {
	/**
	 * Adds the file menu specific items to the FileMenu
	 */
	public FileMenu(){
		super("File");
		add(new NewLevelMenuItem());
	}
}
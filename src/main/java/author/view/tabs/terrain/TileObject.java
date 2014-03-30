package main.java.author.view.tabs.terrain;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public abstract class TileObject extends JButton{
	
	public TileObject() {
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Canvas.setSelectedTileObj(TileObject.this);
			}
		});
	}
	
	abstract boolean canWalkOver();
	abstract Color getBGColor();
	abstract String getImage();

}

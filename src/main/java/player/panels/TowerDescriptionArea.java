package main.java.player.panels;

import javax.swing.JTextArea;

public class TowerDescriptionArea extends ObservingPanel{
	private static final long serialVersionUID = 1L;
	
	private JTextArea textArea;
	
	public TowerDescriptionArea(int rows, int columns) {
		textArea = new JTextArea(rows, columns);
		add(textArea);
	}

	@Override
	public void update() {
		textArea.setText(engine.getCurrentTowerDescription());
	}
}

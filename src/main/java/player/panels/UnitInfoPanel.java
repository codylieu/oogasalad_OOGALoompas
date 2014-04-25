package main.java.player.panels;

import java.util.List;

import javax.swing.JTextArea;

import main.java.player.TDPlayerEngine;

@SuppressWarnings("serial")
public class UnitInfoPanel extends ObservingPanel{
	
	public static final String TIME = "Time";
	
	//private TDPlayerEngine engine;
	private JTextArea unitInfoArea;
	//private JScrollPane scrollPane;
	public UnitInfoPanel() {

		unitInfoArea = new JTextArea(5, 5);
		unitInfoArea.setEditable(false);
		unitInfoArea.setLineWrap(true);
		unitInfoArea.setWrapStyleWord(true);
		add(unitInfoArea);
		//scrollPane = new JScrollPane(unitInfoArea);
		//add(scrollPane);
	}

	@Override
	public void update() {		
	List<String> unitInfoList = engine.getCurrentDescription();
		String unitInfo = "";
		for(String s: unitInfoList){
			unitInfo += s + "\n";
		}
		unitInfoArea.setText(unitInfo);
	}


}
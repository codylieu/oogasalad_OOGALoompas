package main.java.player.panels;

import java.util.List;

import main.java.player.TDPlayerEngine;

public class TowerChooser extends ObjectChooser{

	public TowerChooser(TDPlayerEngine myEngine){
		super(myEngine);
	}
	
	@Override
	public void getObjectNames() {
		List<String> towerNameList = engine.getPossibleTowers();
		System.out.println(engine.getPossibleTowers().size());
		comboBoxModel.removeAllElements();
		for (String s: towerNameList) {
			comboBoxModel.addElement(s);
		}		
		objectComboBox.setSelectedIndex(0);
	}

}
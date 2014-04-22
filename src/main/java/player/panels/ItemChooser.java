package main.java.player.panels;

import java.util.List;

import main.java.player.TDPlayerEngine;

@SuppressWarnings("serial")
public class ItemChooser extends ObjectChooser{

	public ItemChooser(TDPlayerEngine myEngine){
		super(myEngine);
		//register(engine);
	}
	
	@Override
	public void getObjectNames() {
		List<String> itemNameList = engine.getPossibleTowers();
		//List<String> itemNameList = new ArrayList<String>(); // need list of items from engine
		//System.out.println(engine.getPossibleTowers().size());
		//itemNameList.add("temp");
		comboBoxModel.removeAllElements();
		for (String s: itemNameList) {
			comboBoxModel.addElement(s);
		}
		
		objectComboBox.setSelectedIndex(0);
	}
	

}

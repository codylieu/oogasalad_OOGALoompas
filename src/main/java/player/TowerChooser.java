package main.java.player;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JPanel;

public class TowerChooser extends JPanel implements ActionListener, Subject{
	private TDPlayerEngine engine;
	private String[] towerNameArray;
	private JComboBox towerComboBox;
	private String currentTowerName;
	private List<Observing> observers;
	private boolean hasChanged;
	
	public TowerChooser(TDPlayerEngine myEngine){
		super(new BorderLayout());
		observers = new ArrayList<Observing>();
		hasChanged = false;
		engine = myEngine;
		currentTowerName = "";
		initTowerList();
		initComboBox();
	}

	private void initComboBox(){
		towerComboBox = new JComboBox(towerNameArray);
		towerComboBox.setSelectedIndex(0);
		towerComboBox.addActionListener(this);
		add(towerComboBox);
	}

	private void initTowerList(){
		//engine call to get list of towers, also need size of list
		//might end up just making into a list, then using separate method to convert to array
		//temporary putting in random words to test 
		//List<String> towerNameList = engine.getListofTowers();
		List<String> towerNameList = new ArrayList<String>();
		//towerNameArray = new String[towerNameList.size()];
		//towerNameArray[0] = "Add Tower";
		towerNameArray = towerNameList.toArray(new String[1]);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox myBox = (JComboBox) e.getSource();
		String towerName = (String) myBox.getSelectedItem();
		update(towerName);
	}

	private void update(String towerName){
		currentTowerName = towerName;
		hasChanged = true;
		notifyObservers();
	}

	@Override
	public void register(Observing o) {
		if(!observers.contains(o)) observers.add(o);
		
	}

	@Override
	public void unregister(Observing o) {
		if(observers.contains(o)) observers.remove(o);
	}

	@Override
	public void notifyObservers() {
		List<Observing> localObservers = null;
		if(!hasChanged) return;
		localObservers = new ArrayList<Observing>(observers);
		hasChanged = false;
		for(Observing o: localObservers){
			o.update();
		}
	}
	
	public String getTowerName(){
		return currentTowerName;
	}
}

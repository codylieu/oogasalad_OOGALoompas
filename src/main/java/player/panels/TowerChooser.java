package main.java.player.panels;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import main.java.player.TDPlayerEngine;
import main.java.player.util.Observing;
import main.java.player.util.Subject;

public class TowerChooser extends JPanel implements ActionListener, Subject{
	private JComboBox<String> towerComboBox;
	private TDPlayerEngine engine;
	private String currentTowerName;
	private List<Observing> observers;
	private boolean hasChanged;
	private Vector<String> comboBoxItems;
	private DefaultComboBoxModel<String> comboBoxModel;
	
	public TowerChooser(TDPlayerEngine myEngine){
		super(new BorderLayout());
		observers = new ArrayList<Observing>();
		hasChanged = false;
		engine = myEngine;
		register(engine);
		currentTowerName = "";
		//getTowerNames();
		initComboBox();
	}

	private void initComboBox(){
		comboBoxItems = new Vector<String>();
		comboBoxModel = new DefaultComboBoxModel<String>(comboBoxItems);
		
		towerComboBox = new JComboBox<String>(comboBoxModel);
		//towerComboBox.setSelectedIndex(0);
		towerComboBox.addActionListener(this);

		add(towerComboBox);
	}

	public void getTowerNames(){
		List<String> towerNameList = engine.getPossibleTowers();
		System.out.println(engine.getPossibleTowers().size());
		comboBoxModel.removeAllElements();
		for (String s: towerNameList) {
			comboBoxModel.addElement(s);
		}
		
		towerComboBox.setSelectedIndex(0);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox<String> myBox = (JComboBox<String>) e.getSource();
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

package main.java.player.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import main.java.player.TDPlayerEngine;
import main.java.player.util.Observing;
/**
 * A JComboBox that is also a Subject(observable).
 * Notifies observers if user clicks on a different item in drop down box
 *  
 * @author Michael Han
 */
@SuppressWarnings("serial")
public class ObjectChooser extends SubjectPanel implements ActionListener{
	protected JComboBox<String> objectComboBox;
	private List<String> objectNames;
	protected String currentObjectName;
	protected Vector<String> comboBoxItems;
	protected DefaultComboBoxModel<String> comboBoxModel;

	public ObjectChooser(List<String> objectNamesList){
		super();
		observers = new ArrayList<Observing>();
		hasChanged = false;
		currentObjectName = "";
		objectNames = objectNamesList;
		initComboBox();
	}

	/**
	 * initializes components relevant to JComboBox
	 */
	private void initComboBox(){
		comboBoxItems = new Vector<String>();
		comboBoxModel = new DefaultComboBoxModel<String>(comboBoxItems);		
		objectComboBox = new JComboBox<String>(comboBoxModel);
		objectComboBox.addActionListener(this);
		populateComboBox();
		add(objectComboBox);
	}

	/**
	 * Populate the JComboBox using list from parameter
	 */
	public void populateComboBox(){
		comboBoxModel.removeAllElements();
		for (String s: objectNames) {
			comboBoxModel.addElement(s);
		}		
		objectComboBox.setSelectedIndex(0);
	}

	/**
	 * pulls the String that was clicked on in the combo box
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox<String> myBox = (JComboBox<String>) e.getSource();
		String objectName = (String) myBox.getSelectedItem();
		update(objectName);
	}

	/**
	 * Changes currentObjectName to new name that user has just clicked on.
	 * Notifies observers
	 */
	@Override
	protected void update(String objectName){
		currentObjectName = objectName;
		hasChanged = true;
		notifyObservers();
	}

	/**
	 * @return currentObjectName
	 */
	public String getObjectName(){
		return currentObjectName;
	}
}
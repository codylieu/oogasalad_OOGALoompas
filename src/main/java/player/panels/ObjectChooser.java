package main.java.player.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import main.java.player.ITDPlayerEngine;
import main.java.reflection.Reflection;
/**
 * A JComboBox that is also a Subject(observable).
 * Notifies observers if user clicks on a different item in drop down box
 *  
 * @author Michael Han
 */
@SuppressWarnings("serial")
public class ObjectChooser extends JPanel implements ActionListener{
	protected JComboBox<String> objectComboBox;
	private List<String> objectNames;
	protected String currentObjectName;
	protected Vector<String> comboBoxItems;
	protected DefaultComboBoxModel<String> comboBoxModel;
	protected Object engine;
	private String methodName;
	
	public ObjectChooser(List<String> objectNamesList, Object myEngine, String myMethodName){
		//super();
		//observers = new ArrayList<Observing>();
		//hasChanged = false;
		engine = (ITDPlayerEngine) myEngine;
		currentObjectName = "";
		objectNames = objectNamesList;
		methodName = myMethodName;
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
	@Override
	public void actionPerformed(ActionEvent e) {
		@SuppressWarnings("unchecked")
		JComboBox<String> myBox = (JComboBox<String>) e.getSource();
		Object objectName = (String) myBox.getSelectedItem();
		Object[] args = new Object[1];
		args[0] = objectName;
		try {
			Method m = engine.getClass().getDeclaredMethod(methodName, Reflection.toClasses(args));
			try {
				m.invoke(engine, args);
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e1) {
				e1.printStackTrace();
			}
		} catch (NoSuchMethodException | SecurityException e1) {
			e1.printStackTrace();
		}
	}
}
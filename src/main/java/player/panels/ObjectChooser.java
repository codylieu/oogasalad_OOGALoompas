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
 * A JComboBox that tells engine of chosen object change 
 *  
 * @author Michael Han
 */
@SuppressWarnings("serial")
public class ObjectChooser extends JPanel implements ActionListener{
	private JComboBox<String> objectComboBox;
	private List<String> objectNames;
	private Vector<String> comboBoxItems;
	private DefaultComboBoxModel<String> comboBoxModel;
	private Object engine;
	private String methodName;
	private String getListMethodName;

	public ObjectChooser(Object myEngine, String myGetListMethodName, String myMethodName){
		engine = (ITDPlayerEngine) myEngine;
		getListMethodName = myGetListMethodName;
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
	@SuppressWarnings("unchecked")
	public void populateComboBox(){
		try {
			Method m = engine.getClass().getDeclaredMethod(getListMethodName);
			try {
				objectNames = (List<String>) m.invoke(engine);
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e1) {
				e1.printStackTrace();
			}
		} catch (NoSuchMethodException | SecurityException e1) {
			e1.printStackTrace();
		}
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
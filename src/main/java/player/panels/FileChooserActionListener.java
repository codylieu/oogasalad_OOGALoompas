package main.java.player.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import main.java.player.ITDPlayerEngine;
import main.java.reflection.Reflection;

public class FileChooserActionListener extends AbstractAction implements ActionListener{

	private static final long serialVersionUID = 1L;
	private String methodName;
	private JFileChooser fileChooser;
	private Object target;
	private Object[] args;
	public FileChooserActionListener(ITDPlayerEngine myEngine, String myMethodName, JFileChooser myFileChooser, String dropDownName){
		super(dropDownName);
		methodName = myMethodName;
		fileChooser = myFileChooser;
		target = myEngine;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		int response = fileChooser.showOpenDialog(null);
		if(response == JFileChooser.APPROVE_OPTION){
			File file = fileChooser.getSelectedFile();
			args = new Object[1];
			System.out.println(file.getAbsolutePath());
			args[0] = file.getAbsolutePath();
			try {
				Method m = target.getClass().getDeclaredMethod(methodName, Reflection.toClasses(args));
				try {
					m.invoke(target, args);
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} catch (NoSuchMethodException | SecurityException e1) {
				e1.printStackTrace();
			}
		}
	}

}

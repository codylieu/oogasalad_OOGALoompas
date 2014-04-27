package main.java.player.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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
			args[0] = file.getAbsolutePath();
			try {
				target.getClass().getDeclaredMethod(methodName, Reflection.toClasses(args));
			} catch (NoSuchMethodException | SecurityException e1) {
				e1.printStackTrace();
			}
		}
	}

}

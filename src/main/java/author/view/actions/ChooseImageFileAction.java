package main.java.author.view.actions;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

public class ChooseImageFileAction extends AbstractAction{
	private JFileChooser fc;
	
	public ChooseImageFileAction(String id) {
		fc = new JFileChooser();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Action [" + e.getActionCommand()
				+ "] performed!");
		fc.setDialogTitle("Choose your file");
		int returnVal = fc.showDialog(null, "Open file");
		fc.showOpenDialog(null);
		File file = fc.getSelectedFile();
		
	}
	
	
}

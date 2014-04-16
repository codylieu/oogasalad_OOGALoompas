package main.java.player.panels;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import main.java.player.dlc.RepositoryViewer;

public class ListListener implements ListSelectionListener {
	
	private RepositoryViewer repositoryViewer;
	
	public ListListener(RepositoryViewer repositoryViewerInit) {
		repositoryViewer = repositoryViewerInit;
	}
	
	public void valueChanged(ListSelectionEvent e) {
		//repositoryViewer.setDescriptionText(repositoryViewer.getDescription(e.getFirstIndex()));
		System.out.println(e.toString());
	}

}

package main.java.player;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

public class RepositoryViewer extends AbstractAction {

	public final static String LIST_URL = "http://people.duke.edu/~kkd10/td/list.txt";
	private JList<String> list;
	
	
	public RepositoryViewer(String s) {
		super(s);
	}

	@Override
	public void actionPerformed(ActionEvent e){
		makeFrame();
	}
	
	private void makeFrame() {
		JFrame frame = new JFrame();
		frame.setTitle("Repository Viewer");
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(makeMainPanel());
		frame.pack();
		frame.setVisible(true);
	}
	
	private JPanel makeMainPanel() {
		JPanel mainPanel = new JPanel();
		mainPanel.add(new JLabel("Please pick a game to download and play"));
		mainPanel.add(makeList());
		mainPanel.add(makeSubmitButton());
		return mainPanel;
	}
	
	
	private JButton makeSubmitButton() {
		JButton submit = new JButton("Play");
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(list.getSelectedValue());
			}
		});
		return submit;
	}
	
	private JList<String> makeList() {
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		for (String title : getTitles())
			listModel.addElement(title);
		list = new JList<String>(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		return list;
	}

	private List<String> getTitles() {
		InputStream inputStream = null;
		List<String> titles = new LinkedList<String>();

		try {
			URL url = new URL(LIST_URL);
			inputStream = url.openStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

			String line;
			while ((line = bufferedReader.readLine()) != null) {
				titles.add(line);
			}
		} catch (Exception e) {}
		finally {
			try { if (inputStream != null) inputStream.close();} catch (IOException ioe) {}
		}
		
		return titles;
	}
}


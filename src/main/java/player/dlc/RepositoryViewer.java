package main.java.player.dlc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import main.java.player.ITDPlayerEngine;
import net.lingala.zip4j.exception.ZipException;

/**
 * AbstractAction that can create a panel to
 * view and download pre-created games from a
 * pre-determined repository
 * @author Kevin
 *
 */

public class RepositoryViewer extends AbstractAction {

	private static final long serialVersionUID = 1L;
	public final static String BASE_URL = "http://people.duke.edu/~kkd10/td/";
	public final static String LIST_URL = BASE_URL + "list.txt";
	public final static String DOWNLOADS_PATH = "downloads/";
	public final static String DELIMITER = "\\|";
	
	private JPanel mainPanel;
	private JList<String> list;
	private Map<String, DLCData> dlc;
	private JTextArea descriptionArea;
	private ITDPlayerEngine engine;
	
	public RepositoryViewer(String s, ITDPlayerEngine engineInit) {
		super(s);
		engine = engineInit;
		dlc = new HashMap<String, DLCData>();
	}

	@Override
	public void actionPerformed(ActionEvent e){
		makeFrame();
	}
	
	private void makeFrame() {
		JFrame frame = new JFrame();
		frame.setTitle("Repository Viewer");
		frame.setLocationRelativeTo(null);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(makeMainPanel());
		frame.pack();
		frame.setVisible(true);
	}
	
	private JPanel makeMainPanel() {
		mainPanel = new JPanel();
		mainPanel.add(new JLabel("Please pick a game to download and play"));
		mainPanel.add(makeList());
		mainPanel.add(makeSubmitButton());
		mainPanel.add(makeDescriptionArea());
		return mainPanel;
	}
	
	private JTextArea makeDescriptionArea() {
		descriptionArea = new JTextArea(10, 20);
		descriptionArea.setEditable(false);
		return descriptionArea;
	}

	private JButton makeSubmitButton() {
		JButton submit = new JButton("Play");
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fileName = dlc.get(list.getSelectedValue()).getFileName();
				try {
					if (JOptionPane.showConfirmDialog(null, "Click OK to start download (may take a while).", "Download", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						downloadFromUrl(new URL(BASE_URL + fileName), DOWNLOADS_PATH + fileName);
						engine.loadBlueprintFile(DOWNLOADS_PATH + fileName);
						JOptionPane.showMessageDialog(null, "Game loaded. Go play it now!");
						mainPanel.setVisible(false);
					}					
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (ZipException e1) {
					e1.printStackTrace();
				}
			}
		});
		return submit;
	}
	
	private JList<String> makeList() {
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		downloadDLC();
		for (String title : dlc.keySet())
			listModel.addElement(title);
		list = new JList<String>(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				descriptionArea.setText(dlc.get(list.getSelectedValue()).getDescription());
			}
		}
		);
		return list;
	}

	private Map<String, DLCData> downloadDLC() {
		InputStream inputStream = null;

		try {
			URL url = new URL(LIST_URL);
			inputStream = url.openStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

			String line;
			while ((line = bufferedReader.readLine()) != null) {
				String[] tokenizedString = line.split(DELIMITER, 2);
				dlc.put(tokenizedString[0], new DLCData(tokenizedString[1]));
			}
		} catch (Exception e) {}
		finally {
			try { if (inputStream != null) inputStream.close();} catch (IOException ioe) {}
		}
		
		return dlc;
	}
	
	private void downloadFromUrl(URL url, String localFilename) throws IOException {
		ReadableByteChannel rbc = Channels.newChannel(url.openStream());
		FileOutputStream fos = new FileOutputStream(localFilename);
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		fos.close();
	}
}


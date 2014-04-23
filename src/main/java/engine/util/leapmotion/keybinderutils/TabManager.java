package main.java.engine.util.leapmotion.keybinderutils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import main.java.engine.util.leapmotion.gamecontroller.LeapGameController;

/**
 * 
 * Credited to Dennis and Sam's keybinder utility.
 *
 */
@SuppressWarnings("serial")
public class TabManager extends JFrame{

	private List<String> gestureList;
	private List<String> fingerCountList;
	private JTabbedPane tabs = new JTabbedPane();
	protected Map<String, Integer> keyBindings = new HashMap<String, Integer>();
	
	/**
	 * The constructor for the TabManager window, which is the shell window for all of the 
	 * object tabs that will require key bindings.  Serves as the interface between
	 * individual object control and the game playing environment.
	 * @param gestureList A list of Gestures enum value names to bind.
	 * @param fingerCountList A list of FingerCounts enum value names to bind.
	 */
	public TabManager(List<String> gestureList, List<String> fingerCountList) {
		this.gestureList = gestureList;
		this.fingerCountList = fingerCountList;
		initialize();
	}

	/**
	 * Creates the GUI that the user will use to set and clear bindings for all of the objects
	 * that will be created by the interface.
	 */
	private void initialize() {
		setTitle("Define User Input");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setFocusable(true);
		JPanel shell = new JPanel();
		shell.setLayout(new BoxLayout(shell, BoxLayout.Y_AXIS));
		tabs.addTab(LeapGameController.GESTURES_TITLE, new KeybindUtility(LeapGameController.GESTURES_TITLE, gestureList, this));
		tabs.addTab(LeapGameController.FINGERCOUNT_TITLE, new KeybindUtility(LeapGameController.FINGERCOUNT_TITLE, fingerCountList, this));
		shell.add(tabs);
		shell.add(createButtonPanel());
		add(shell);
		setVisible(true);
		pack();
		setLocationRelativeTo(null);
	}

	/**
	 * Creates the button panel that will store the save and add buttons, which will
	 * either save the bindings to a text file, or add a new object to the tabbed environment
	 * that will allow the user to bind commands to a new object.
	 * @return The created button panel
	 */
	private JPanel createButtonPanel() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(createSaveButton());
		return buttonPanel;
	}
	
	/**
	 * Takes in user input and adds a new tabbed pane to the TabManager
	 * that is attached to the object represented by that input.
	 * @param text The name of the object that is being created
	 */
	public void addObjectTab(String text) {
		tabs.addTab(text, new KeybindUtility(text, fingerCountList, this));
		pack();
	}

	/**
	 * Creates the save button, the user interface object that is used to signal
	 * the completion of binding and prepares the program to save the entered information
	 * @return The created save button
	 */
	private JButton createSaveButton() {
		JButton saveButton = new JButton("Save");
		saveButton.setAlignmentX(LEFT_ALIGNMENT);
		createSaveListener(saveButton);
		return saveButton;
		
	}
	
	/**
	 * Creates the listener for the save button, which triggers the saveMap method to
	 * write the keyBindings map to a text file
	 * @param saveButton The button the listener is being attached to
	 */
	private void createSaveListener(JButton saveButton) {
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveMap();
				
			}
			
		});
	}
	
	/**
	 * This method allows the back-end to access the binding map created by the
	 * user
	 * 
	 * @return The keybinding map
	 */
	private void saveMap() {
		System.out.println("saving");
		
		Properties properties = new Properties();
		FileOutputStream outstream;
		try {
			outstream = new FileOutputStream(LeapGameController.DEFAULT_ACTIONMAP_FILEPATH
			                                 + LeapGameController.DEFAULT_ACTIONMAP_FILENAME);
			                                 
			for (Map.Entry<String, Integer> entry : keyBindings.entrySet()) {
				properties.put(entry.getKey(), entry.getValue().toString());
			}
		
			properties.store(outstream, "Leap Motion Action Bindings");
		} catch (IOException e) {
			System.out.println("IOException");
		}

		
	}
}

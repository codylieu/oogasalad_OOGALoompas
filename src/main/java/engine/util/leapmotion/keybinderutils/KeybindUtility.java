package main.java.engine.util.leapmotion.keybinderutils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import main.java.engine.util.leapmotion.gamecontroller.LeapGameController;

/**
 * 
 * Credited to Dennis and Sam's keybinder utility.
 *
 */
@SuppressWarnings("serial")
public class KeybindUtility extends JPanel {
	private static final String WAIT_STATUS = "    Click Set to Bind, Click Clear to Delete Binding   ";
	private static final String CHANGE_STATUS = "    Press the Key or Double Click to Bind an Action    ";
	private static final String BOUND_STATUS = "   Key Already Bound.  Please Press Another Key  ";

	private JLabel statusLabel = new JLabel(WAIT_STATUS);
	private List<String> commandList;
	private TabManager parentFrame;
	private String gameObject;

	//protected Map<Integer, String> keyBindings = new HashMap<Integer, String>();

	/**
	 * A utility class to easily swap meaningful key inputs for the editor as
	 * well as for the game being created.
	 */
	public KeybindUtility(String object, List<String> commands, TabManager manager) {
		gameObject = object;
		commandList = commands;
		parentFrame = manager;
		initialize();
	}

	/**
	 * Creates the GUI for the Keybind Utility to let the user easily map the
	 * keyboard inputs
	 */
	private void initialize() {
		JComponent shell = new JPanel();
		shell.setLayout(new BoxLayout(shell, BoxLayout.Y_AXIS));
		createBindingPanels(shell);
		add(shell);
		setVisible(true);
	}

	/**
	 * Generates a constantly present key listener that will update a KeyEvent
	 * buffer variable with the most recently released key.
	 */
	private void generateButtonListener(final JLabel label,
			final String command, final JButton button) {
		button.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent e) {
				// Do nothing - must be present for interface
			}

			public void keyReleased(KeyEvent e) {
				boolean alreadyBound = false;
				
				
				for (String event : parentFrame.keyBindings.keySet()) {
					if (event.equals(e.getKeyCode())) {
						alreadyBound = true;
					}
				}
				if (alreadyBound) {
					statusLabel.setText(BOUND_STATUS);
				} else {
					parentFrame.keyBindings.put(command, e.getKeyCode());
					label.setText(KeyEvent.getKeyText(e.getKeyCode())
							+ "        ");
					statusLabel.setText(WAIT_STATUS);
					alreadyBound = false;
				}
			}

			public void keyTyped(KeyEvent e) {
				// Do nothing - must be present for interface
			}
		});
		
		button.addMouseListener( new MouseListener() {

                    @Override
                    public void mouseReleased (MouseEvent e) {  
                    }
                    
                    @Override
                    public void mousePressed (MouseEvent e) {  
                    }
                    
                    @Override
                    public void mouseExited (MouseEvent e) {
                    }
                    
                    @Override
                    public void mouseEntered (MouseEvent e) {
                    }
                    
                    @Override
                    public void mouseClicked (MouseEvent e) {
                        if(e.getClickCount() < 2) {
                            //disallow single click to register mouse binding
                            return;
                        }
                        if (SwingUtilities.isLeftMouseButton(e)){
                            parentFrame.keyBindings.put(command, KeyEvent.BUTTON1_DOWN_MASK);
                            label.setText(LeapGameController.LEFT_CLICK);
                        } else {
                            parentFrame.keyBindings.put(command, KeyEvent.BUTTON2_DOWN_MASK);
                            label.setText(LeapGameController.RIGHT_CLICK);
                        }
                        statusLabel.setText(WAIT_STATUS);
                    }
                });

	}

	/**
	 * Generates the GUI panels for the key-binding interface. This GUI is
	 * composed of individual panels that all hold the labels for the associated
	 * command, keybinding, and set and clear buttons to set a new binding or
	 * clear an existing one.
	 * 
	 * @param framePanel
	 *            The panel that will hold all of the individual component
	 *            panels
	 */
	private void createBindingPanels(final JComponent framePanel) {
		for (final String command : commandList) {
			JPanel componentPanel = new JPanel();
			componentPanel.setLayout(new BoxLayout(componentPanel,
					BoxLayout.X_AXIS));
			JLabel commandLabel = new JLabel(command + "    ");
			JLabel binding = new JLabel("<>      ");
			JButton setButton = new JButton("Set");
			createSetListener(setButton, binding, command);
			JButton clearButton = new JButton("Clear");
			createClearListener(clearButton, binding, command);
			componentPanel.add(commandLabel);
			componentPanel.add(binding);
			componentPanel.add(setButton);
			componentPanel.add(clearButton);
			framePanel.add(componentPanel);
		}
		statusLabel.setAlignmentX(CENTER_ALIGNMENT);
		framePanel.add(statusLabel);
		
	}

	/**
	 * Creates a listener for a particular panel's CLEAR button
	 * 
	 * @param clearButton
	 * @param binding
	 *            The label that describes the bound key event
	 * @param command
	 *            The label that describes the method the panel controls
	 */
	private void createClearListener(JButton clearButton, final JLabel binding,
			final String command) {
		clearButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String keyToRemove = "";
				binding.setText("<>      ");
				for(String key : parentFrame.keyBindings.keySet()) {
					if(key.startsWith(command)) {
						keyToRemove = key;
					}
				}
				if(!keyToRemove.equals("")) {
					parentFrame.keyBindings.remove(keyToRemove);
				}	
				statusLabel.setText(WAIT_STATUS);
			}

		});

	}

	/**
	 * Creates a listener for a particular panel's CLEAR button
	 * 
	 * @param setButton
	 * @param binding
	 *            The label that describes the bound key event
	 * @param command
	 *            The label that describes the method the panel controls
	 */
	private void createSetListener(final JButton setButton,
			final JLabel binding, final String command) {
		generateButtonListener(binding, command, setButton);
		setButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				statusLabel.setText(CHANGE_STATUS);
			}

		});
	}
}

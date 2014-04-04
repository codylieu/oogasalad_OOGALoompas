package main.java.author.view.tabs;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import main.java.author.controller.MainController;
import main.java.author.util.UtilFunctions;
import main.java.schema.EnemySchema;

//SplitPaneDemo itself is not a visible component.
public class EnemyEditorTab extends EditorTab implements ListSelectionListener {
	private JLabel picture;
	private DefaultListModel listModel;
	private JList list;
	private JSplitPane splitPane;
	private JPanel designEnemyPane;
	private JLabel healthLabel;
	private JLabel speedLabel;
	private JLabel damageLabel;

	private JTextField healthField;
	private JTextField speedField;
	private JTextField damageField;

	private static String healthString = "Health: ";
	private static String speedString = "Speed: ";
	private static String damageString = "Damage: ";

	private NumberFormat numberFormat;

	private JButton createEnemyButton;
	private JTextField createEnemyField;

	private HashMap<String, EnemySchema> enemyMap;

	public EnemyEditorTab(MainController c) {
		super(c);
		this.setLayout(new BorderLayout());
		this.add(makeDesignEnemyPane(), BorderLayout.NORTH);
		this.add(makeOverallTabContent(), BorderLayout.SOUTH);
		initDataFields();
		addActionListeners();
	}

	private void initDataFields() {
		enemyMap = new HashMap<String, EnemySchema>();
	}

	private Component makeDesignEnemyPane() {
		JPanel result = new JPanel();
		result.setLayout(new BorderLayout());
		result.add(new JLabel("Design New Enemy"), BorderLayout.WEST);

		createEnemyField = new JTextField();
		createEnemyButton = new JButton("Begin");
		result.add(createEnemyField, BorderLayout.CENTER);
		result.add(createEnemyButton, BorderLayout.EAST);

		return result;
	}

	public Component makeOverallTabContent() {
		listModel = new DefaultListModel();
		listModel.addElement("Default Enemy Name");
		list = new JList(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedIndex(0);
		list.addListSelectionListener(this);

		JScrollPane listScrollPane = new JScrollPane(list);

		JComponent editorPane = makeEditorPane();

		// Create a split pane with the two scroll panes in it.
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listScrollPane,
				editorPane);

		splitPane.setDividerLocation(150);

		Dimension minimumSize = new Dimension(100, 50);
		listScrollPane.setMinimumSize(minimumSize);
		editorPane.setMinimumSize(minimumSize);

		splitPane.setPreferredSize(new Dimension(this.getWidth(), 550));
		// updateLabel(enemyNames[list.getSelectedIndex()]);
		return splitPane;
	}

	private JComponent makeEditorPane() {
		JPanel result = new JPanel();
		result.setLayout(new BorderLayout());
		result.add(makeAttributesPane(), BorderLayout.CENTER);
		result.add(makeImagesPane(), BorderLayout.EAST);
		result.add(makeDeleteEnemyButton(), BorderLayout.SOUTH);

		return result;
	}

	private Component makeDeleteEnemyButton() {
		JButton result = new JButton("Delete Enemy");
		return result;
	}

	private JComponent makeImagesPane() {
		JPanel result = new JPanel();
		result.setLayout(new BorderLayout());
		result.add(makeEnemyGraphicPane(), BorderLayout.NORTH);
		result.add(makeCollisionGraphicPane(), BorderLayout.SOUTH);
		return result;
	}

	private JComponent makeEnemyGraphicPane() {
		JPanel result = new JPanel();
		result.setLayout(new BorderLayout());
		Canvas canvas = new Canvas();
		canvas.setSize(200, 200);
		canvas.setBackground(Color.BLACK);
		result.add(canvas, BorderLayout.NORTH);
		result.add(makeChooseGraphicsButton(), BorderLayout.SOUTH);
		return result;
	}

	private JComponent makeCollisionGraphicPane() {
		JPanel result = new JPanel();
		result.setLayout(new BorderLayout());
		Canvas canvas = new Canvas();
		canvas.setSize(200, 200);
		canvas.setBackground(Color.BLACK);
		result.add(canvas, BorderLayout.NORTH);
		result.add(makeChooseGraphicsButton(), BorderLayout.SOUTH);
		return result;
	}

	private JButton makeChooseGraphicsButton() {
		JButton result = new JButton("Choose graphics");
		return result;
	}

	// Renders the selected enemy's data
	protected void updateDataDisplayed(String name) {

	}

	/** Returns an ImageIcon, or null if the path was invalid. */
	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = EnemyEditorTab.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	private JComponent makeLabelPane() {
		healthLabel = new JLabel(healthString);
		healthLabel.setLabelFor(healthField);
		speedLabel = new JLabel(speedString);
		speedLabel.setLabelFor(speedField);

		damageLabel = new JLabel(damageString);
		damageLabel.setLabelFor(damageField);
		JPanel labels = new JPanel(new GridLayout(0, 1));
		labels.add(healthLabel);
		labels.add(speedLabel);
		labels.add(damageLabel);
		return labels;
	}

	private JComponent makeFieldPane() {
		healthField = new JFormattedTextField(numberFormat);
		healthField.setColumns(5);
		speedField = new JFormattedTextField(numberFormat);
		damageField = new JFormattedTextField(numberFormat);
		JPanel result = new JPanel(new GridLayout(0, 1));
		result.add(healthField);
		result.add(speedField);
		result.add(damageField);
		return result;
	}

	private JComponent makeAttributesPane() {
		JPanel result = new JPanel();
		result.setLayout(new BorderLayout());
		result.add(makeLabelPane(), BorderLayout.WEST);
		result.add(makeFieldPane(), BorderLayout.CENTER);
		return result;
	}

	private void addActionListeners() {
		createEnemyButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String createEnemyText = createEnemyField.getText().trim();
				if (newEnemyNameIsValid(createEnemyText)) {
					createNewEnemy(createEnemyText);
				}

			}

		});
	}

	private boolean newEnemyNameIsValid(String enemyName) {
		if (!enemyName.equals("")) {
			if (!enemyMap.containsKey(enemyName)) {
				if (UtilFunctions.isAlphaNumeric(enemyName)) {
					if (enemyName.length() <= 20) {
						if (enemyName.length() >= 2) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	private void createNewEnemy(String enemyName) {
		listModel.addElement(enemyName);
		EnemySchema newEmeny = new EnemySchema();
		enemyMap.put(enemyName, newEmeny);
		updateDataDisplayed(enemyName);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub

	}
}
package main.java.author.view.tabs.enemy;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import main.java.author.controller.MainController;
import main.java.author.util.UtilFunctions;
import main.java.author.view.components.ImageCanvas;
import main.java.author.view.tabs.EditorTab;
import main.java.schema.SimpleMonsterSchema;

//SplitPaneDemo itself is not a visible component.
public class EnemyEditorTab extends EditorTab implements ListSelectionListener {
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

	private SimpleMonsterSchema myCurrentEnemy;
	private JFileChooser fc;
	private ImageCanvas collisionImageCanvas;
	private ImageCanvas enemyImageCanvas;

	private JLabel picture;
	private DefaultListModel listModel;
	private JList list;
	private JSplitPane splitPane;

	private JPanel designEnemyPane;
	private JSpinner healthField;
	private JSpinner speedField;
	private JSpinner damageField;

	private JSpinner rewardField;
	private static final int IMAGE_CANVAS_SIZE = 235;
	private static final int DIVIDER_LOCATION = 300;
	private static final float MEDIUM_FONT_SIZE = 25f;
	private static final float LARGE_FONT_SIZE = 50f;

	private static final float X_LARGE_FONT_SIZE = 100f;
	private static final String HEALTH_STRING = "Health";
	private static final String SPEED_STRING = "Speed";
	private static final String DAMAGE_STRING = "Damage";
	private static final String REWARD_STRING = "Bounty";
	private static final String TYPE_STRING = "Unit Type";

	private static final String TILE_SIZE_STRING = "Tile Size";

	private NumberFormat numberFormat;
	private JButton createEnemyButton;

	private JTextField createEnemyField;
	private JButton collisionImageButton;
	private JButton enemyImageButton;

	private HashMap<String, SimpleMonsterSchema> enemyMap;

	public EnemyEditorTab(MainController c) {
		super(c);
		this.setLayout(new BorderLayout());
		this.add(makeDesignEnemyPane(), BorderLayout.NORTH);
		this.add(makeOverallTabContent(), BorderLayout.SOUTH);
		initDataFields();
		addActionListeners();
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
		collisionImageButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(EnemyEditorTab.this);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					try {
						System.out.println("Opening: " + file.getName() + ".\n");
						BufferedImage img = ImageIO.read(file);
						ImageIcon icon = new ImageIcon(img); // ADDED
						collisionImageCanvas.setImage(img);
						collisionImageCanvas.repaint();
					} catch (IOException e1) {
					}

				} else {
					System.out.println("Cancelled");
				}
			}
		});
		enemyImageButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(EnemyEditorTab.this);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					try {
						System.out.println("Opening: " + file.getName() + ".\n");
						BufferedImage img = ImageIO.read(file);
						ImageIcon icon = new ImageIcon(img); // ADDED
						enemyImageCanvas.setImage(img);
						enemyImageCanvas.repaint();
					} catch (IOException e1) {
					}

				} else {
					System.out.println("Cancelled");
				}
			}
		});

	}

	private void createNewEnemy(String enemyName) {
		listModel.addElement(enemyName);
		SimpleMonsterSchema newEnemy = new SimpleMonsterSchema();
		enemyMap.put(enemyName, newEnemy);
		updateDataDisplayed(enemyName);
	}

	private void initDataFields() {
		enemyMap = new HashMap<String, SimpleMonsterSchema>();
	}

	private JComponent makeAttributesPane() {
		JPanel result = new JPanel();

		result.setLayout(new BorderLayout());

		result.add(makeLabelPane(), BorderLayout.WEST);
		result.add(makeFieldPane(), BorderLayout.CENTER);
		return result;
	}

	private JSpinner makeAttributeSpinner() {

		SpinnerModel model = new SpinnerNumberModel(20, 1, 1000, 1);
		JSpinner spinner = new JSpinner(model);
		spinner.setMaximumSize(new Dimension(200, spinner.getHeight()));
		Font bigFont = spinner.getFont().deriveFont(Font.PLAIN,
				X_LARGE_FONT_SIZE);
		spinner.setFont(bigFont);
		return spinner;
	}

	private JButton makeChooseGraphicsButton(String buttonString) {
		JButton result = new JButton(buttonString);
		return result;
	}

	private JComponent makeCollisionGraphicPane() {
		JPanel result = new JPanel();
		result.setLayout(new BorderLayout());
		collisionImageCanvas = new ImageCanvas();
		collisionImageCanvas.setSize(IMAGE_CANVAS_SIZE, IMAGE_CANVAS_SIZE);
		collisionImageCanvas.setBackground(Color.BLACK);
		result.add(collisionImageCanvas, BorderLayout.NORTH);
		collisionImageButton = makeChooseGraphicsButton("Set Collision Image");
		result.add(collisionImageButton, BorderLayout.SOUTH);
		return result;
	}

	private JComponent makeEnemyGraphicPane() {
		JPanel result = new JPanel();
		result.setLayout(new BorderLayout());
		enemyImageCanvas = new ImageCanvas();
		enemyImageCanvas.setSize(IMAGE_CANVAS_SIZE, IMAGE_CANVAS_SIZE);
		enemyImageCanvas.setBackground(Color.BLACK);
		result.add(enemyImageCanvas, BorderLayout.NORTH);
		enemyImageButton = makeChooseGraphicsButton("Set Enemy Image");
		result.add(enemyImageButton, BorderLayout.SOUTH);
		return result;
	}

	private Component makeDeleteEnemyButton() {
		JButton result = new JButton("Delete Enemy");
		return result;
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

	private Component makeDimensionPane() {
		JPanel result = new JPanel();
		result.setLayout(new GridLayout(1, 0));

		result.setBorder(BorderFactory.createEmptyBorder(0, // top
				20, // left
				0, // bottom
				0)); // right

		JRadioButton smallButton;
		smallButton = new JRadioButton("Small");
		JRadioButton mediumButton;
		mediumButton = new JRadioButton("Medium");
		JRadioButton largeButton;
		largeButton = new JRadioButton("Large");
		ButtonGroup sizeButtonGroup = new ButtonGroup();
		sizeButtonGroup.add(smallButton);
		sizeButtonGroup.add(mediumButton);
		sizeButtonGroup.add(largeButton);
		result.add(smallButton);
		result.add(mediumButton);
		result.add(largeButton);
		return result;
	}

	private JComponent makeEditorPane() {
		JPanel result = new JPanel();
		result.setLayout(new BorderLayout());
		result.add(makeAttributesPane(), BorderLayout.CENTER);
		result.add(makeImagesPane(), BorderLayout.EAST);
		result.add(makeDeleteEnemyButton(), BorderLayout.SOUTH);

		return result;
	}

	private JComponent makeFieldPane() {

		JPanel result = new JPanel(new GridLayout(0, 1));

		healthField = makeAttributeSpinner();
		speedField = makeAttributeSpinner();
		damageField = makeAttributeSpinner();
		rewardField = makeAttributeSpinner();
		result.add(healthField);
		result.add(speedField);
		result.add(damageField);
		result.add(rewardField);
		result.add(makeTypePane());
		result.add(makeDimensionPane());
		return result;
	}

	private JComponent makeImagesPane() {
		JPanel result = new JPanel();
		result.setLayout(new BorderLayout());
		result.add(makeEnemyGraphicPane(), BorderLayout.NORTH);
		result.add(makeCollisionGraphicPane(), BorderLayout.SOUTH);
		return result;
	}

	private JComponent makeLabelPane() {

		JPanel labels = new JPanel(new GridLayout(0, 1));
		labels.add(new JLabel(HEALTH_STRING));
		labels.add(new JLabel(SPEED_STRING));
		labels.add(new JLabel(DAMAGE_STRING));
		labels.add(new JLabel(REWARD_STRING));
		labels.add(new JLabel(TYPE_STRING));
		labels.add(new JLabel(TILE_SIZE_STRING));
		return labels;
	}

	public Component makeOverallTabContent() {
		listModel = new DefaultListModel();
		listModel.addElement("Default Enemy Name");
		list = new JList(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedIndex(0);
		list.addListSelectionListener(this);
		list.setFont(list.getFont().deriveFont(Font.PLAIN, MEDIUM_FONT_SIZE));

		JScrollPane listScrollPane = new JScrollPane(list);

		JComponent editorPane = makeEditorPane();

		// Create a split pane with the two scroll panes in it.
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listScrollPane,
				editorPane);

		splitPane.setDividerLocation(DIVIDER_LOCATION);

		Dimension minimumSize = new Dimension(100, 50);
		listScrollPane.setMinimumSize(minimumSize);
		editorPane.setMinimumSize(minimumSize);

		splitPane.setPreferredSize(new Dimension(this.getWidth(), 550));
		// updateLabel(enemyNames[list.getSelectedIndex()]);
		return splitPane;
	}

	private JComponent makeTypePane() {
		JPanel result = new JPanel();
		result.setLayout(new GridLayout(1, 0));

		result.setBorder(BorderFactory.createEmptyBorder(0, // top
				20, // left
				0, // bottom
				0)); // right

		JRadioButton groundButton;
		groundButton = new JRadioButton("Ground");
		JRadioButton flyingButton;
		flyingButton = new JRadioButton("Flying");
		ButtonGroup sizeButtonGroup = new ButtonGroup();
		sizeButtonGroup.add(groundButton);
		sizeButtonGroup.add(flyingButton);
		result.add(groundButton);
		result.add(flyingButton);
		return result;
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

	// Renders the selected enemy's data
	protected void updateDataDisplayed(String name) {
		SimpleMonsterSchema myCurrentEnemy = enemyMap.get(name);
		//fill out
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {

		String currentEnemyName = (String) list.getSelectedValue();

		updateDataDisplayed(currentEnemyName);


	}
}
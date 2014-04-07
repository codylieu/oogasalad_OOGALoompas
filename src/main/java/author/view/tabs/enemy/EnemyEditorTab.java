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
import java.util.Date;
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
import main.java.author.util.EnemyUtilFunctions;
import main.java.author.view.components.ImageCanvas;
import main.java.author.view.tabs.EditorTab;
import main.java.schema.SimpleMonsterSchema;

//SplitPaneDemo itself is not a visible component.
public class EnemyEditorTab extends EditorTab implements ListSelectionListener {

	private SimpleMonsterSchema myCurrentEnemy;
	private JFileChooser fc;
	private ImageCanvas collisionImageCanvas;
	private ImageCanvas enemyImageCanvas;

	private JLabel picture;
	private DefaultListModel<String> listModel;
	private JList<String> list;
	private JSplitPane splitPane;

	private JPanel designEnemyPane;
	private JSpinner healthField;
	private JSpinner speedField;
	private JSpinner damageField;

	private JSpinner rewardField;

	private NumberFormat numberFormat;
	private JButton createEnemyButton;

	private JRadioButton smallButton;
	private JRadioButton mediumButton;
	private JRadioButton largeButton;

	private JTextField createEnemyField;
	private JButton collisionImageButton;
	private JButton enemyImageButton;

	private EnemyTabViewBuilder myBuilder;

	private HashMap<String, SimpleMonsterSchema> enemyMap;

	public EnemyEditorTab(MainController c) {
		super(c);
		myBuilder = new EnemyTabViewBuilder(this);
		this.setLayout(new BorderLayout());
		this.add(myBuilder.makeDesignEnemyPane(), BorderLayout.NORTH);
		this.add(myBuilder.makeOverallContent(), BorderLayout.SOUTH);
		initDataFields();

		addActionListeners();
	}

	private void addActionListeners() {

		list.addListSelectionListener(this);
		
		createEnemyButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String enemyName = createEnemyField.getText();
				if (EnemyUtilFunctions.newEnemyNameIsValid(enemyName, enemyMap)) {
					createNewEnemy(enemyName);
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

	

	// Renders the selected enemy's data
	protected void updateDataDisplayed(String name) {
		SimpleMonsterSchema myCurrentEnemy = enemyMap.get(name);
		// fill out
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {

		String currentEnemyName = list.getSelectedValue();

		updateDataDisplayed(currentEnemyName);

	}

	private class EnemyTabViewBuilder {
		EnemyEditorTab myTab;

		public EnemyTabViewBuilder(EnemyEditorTab enemyEditorTab) {
			myTab = enemyEditorTab;
		}

		public Component makeOverallContent() {
			list = myBuilder.makeList();

			// Create a split pane with the two scroll panes in it.
			splitPane = myBuilder.makeSplitPane(new JScrollPane(list),
					makeEditorPane());
			// updateLabel(enemyNames[list.getSelectedIndex()]);
			return splitPane;
		}

		private JComponent makeAttributesPane() {
			JPanel result = new JPanel();

			result.setLayout(new BorderLayout());

			result.add(myBuilder.makeLabelPane(), BorderLayout.WEST);
			result.add(myBuilder.makeFieldPane(), BorderLayout.CENTER);
			return result;
		}

		private Component makeDeleteEnemyButton() {
			JButton result = new JButton("Delete Enemy");
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

		private JComponent makeImagesPane() {
			JPanel result = new JPanel();
			result.setLayout(new BorderLayout());
			result.add(myBuilder.makeEnemyGraphicPane(), BorderLayout.NORTH);
			result.add(myBuilder.makeCollisionGraphicPane(), BorderLayout.SOUTH);
			return result;
		}
		
		public Component makeDesignEnemyPane() {
			JPanel result = new JPanel();
			result.setLayout(new BorderLayout());
			result.add(new JLabel("Design New Enemy"), BorderLayout.WEST);

			createEnemyField = new JTextField();
			createEnemyButton = new JButton("Begin");
			result.add(createEnemyField, BorderLayout.CENTER);
			result.add(createEnemyButton, BorderLayout.EAST);

			return result;
		}

		public JComponent makeTypePane() {
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

		public JComponent makeLabelPane() {

			JPanel labels = new JPanel(new GridLayout(0, 1));
			labels.add(new JLabel(EnemyViewConstants.HEALTH_STRING));
			labels.add(new JLabel(EnemyViewConstants.SPEED_STRING));
			labels.add(new JLabel(EnemyViewConstants.DAMAGE_STRING));
			labels.add(new JLabel(EnemyViewConstants.REWARD_STRING));
			labels.add(new JLabel(EnemyViewConstants.TYPE_STRING));
			labels.add(new JLabel(EnemyViewConstants.TILE_SIZE_STRING));
			return labels;
		}

		public JSpinner makeAttributeSpinner() {

			SpinnerModel model = new SpinnerNumberModel(20, 1, 1000, 1);
			JSpinner spinner = new JSpinner(model);
			spinner.setMaximumSize(new Dimension(200, spinner.getHeight()));
			Font bigFont = spinner.getFont().deriveFont(Font.PLAIN,
					EnemyViewConstants.X_LARGE_FONT_SIZE);
			spinner.setFont(bigFont);
			return spinner;
		}

		public JComponent makeFieldPane() {

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

		public Component makeDimensionPane() {
			JPanel result = new JPanel();
			result.setLayout(new GridLayout(1, 0));

			result.setBorder(BorderFactory.createEmptyBorder(0, // top
					20, // left
					0, // bottom
					0)); // right

			smallButton = new JRadioButton("Small");
			mediumButton = new JRadioButton("Medium");
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

		public JComponent makeCollisionGraphicPane() {
			JPanel result = new JPanel();
			result.setLayout(new BorderLayout());
			collisionImageCanvas = new ImageCanvas();
			collisionImageCanvas.setSize(EnemyViewConstants.IMAGE_CANVAS_SIZE,
					EnemyViewConstants.IMAGE_CANVAS_SIZE);
			collisionImageCanvas.setBackground(Color.BLACK);
			result.add(collisionImageCanvas, BorderLayout.NORTH);
			collisionImageButton = makeChooseGraphicsButton("Set Collision Image");
			result.add(collisionImageButton, BorderLayout.SOUTH);
			return result;
		}

		public JComponent makeEnemyGraphicPane() {
			JPanel result = new JPanel();
			result.setLayout(new BorderLayout());
			enemyImageCanvas = new ImageCanvas();
			enemyImageCanvas.setSize(EnemyViewConstants.IMAGE_CANVAS_SIZE,
					EnemyViewConstants.IMAGE_CANVAS_SIZE);
			enemyImageCanvas.setBackground(Color.BLACK);
			result.add(enemyImageCanvas, BorderLayout.NORTH);
			enemyImageButton = makeChooseGraphicsButton("Set Enemy Image");
			result.add(enemyImageButton, BorderLayout.SOUTH);
			return result;
		}

		private JButton makeChooseGraphicsButton(String buttonString) {
			JButton result = new JButton(buttonString);
			return result;
		}

		public JList makeList() {
			listModel = new DefaultListModel<String>();
			listModel.addElement("Default Enemy Name");
			JList list = new JList<String>(listModel);
			list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			list.setSelectedIndex(0);

			list.setFont(list.getFont().deriveFont(Font.PLAIN,
					EnemyViewConstants.MEDIUM_FONT_SIZE));
			return list;
		}

		public JSplitPane makeSplitPane(JScrollPane listScrollPane,
				JComponent editorPane) {

			JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
					listScrollPane, editorPane);

			splitPane.setDividerLocation(EnemyViewConstants.DIVIDER_LOCATION);

			Dimension minimumSize = new Dimension(100, 50);
			listScrollPane.setMinimumSize(minimumSize);
			editorPane.setMinimumSize(minimumSize);

			splitPane.setPreferredSize(new Dimension(myTab.getWidth(), 550));
			return splitPane;
		}
	}
}
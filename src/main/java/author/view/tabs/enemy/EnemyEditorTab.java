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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import main.java.author.controller.MainController;
import main.java.author.util.EnemyUtilFunctions;
import main.java.author.util.GroupButtonUtil;
import main.java.author.view.components.ImageCanvas;
import main.java.author.view.tabs.EditorTab;
import main.java.schema.MonsterSchema;
import main.java.schema.SimpleMonsterSchema;

//SplitPaneDemo itself is not a visible component.
public class EnemyEditorTab extends EditorTab {

	private JFileChooser fc;

	private BufferedImage collisionImage;
	private BufferedImage enemyImage;

	private ImageCanvas collisionImageCanvas;
	private ImageCanvas enemyImageCanvas;

	private DefaultTableModel listModel;
	private JTable list;
	private JSplitPane splitPane;

	private JSpinner healthField;
	private JSpinner speedField;
	private JSpinner damageField;
	private JSpinner rewardField;
	private List<JSpinner> spinnerFields;

	private JButton createEnemyButton;

	private JRadioButton smallButton;
	private JRadioButton mediumButton;
	private JRadioButton largeButton;
	private JRadioButton flyingButton;
	private JRadioButton groundButton;

	private ButtonGroup sizeButtonGroup;
	private ButtonGroup flyingButtonGroup;

	private List<JRadioButton> radioButtons;

	private JTextField createEnemyField;
	private JButton collisionImageButton;
	private JButton deleteEnemyButton;
	private JButton enemyImageButton;

	private Border originalCreateEnemyFieldBorder;
	private EnemyTabViewBuilder myBuilder;

	private HashMap<String, SimpleMonsterSchema> enemyMap;

	public EnemyEditorTab(MainController c) {
		super(c);
		myBuilder = new EnemyTabViewBuilder(this);
		this.setLayout(new BorderLayout());
		this.add(myBuilder.makeDesignEnemyPane(), BorderLayout.NORTH);
		this.add(myBuilder.makeOverallContent(), BorderLayout.SOUTH);
		initDataFields();
		addEnemyNameToList("Monster A");
		updateFieldDataUponNewSelection();
		addListeners();
	}

	private void addListeners() {

		deleteEnemyButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (list.getRowCount() == 1) {
					JOptionPane.showMessageDialog(null,
							"Please design at least one enemy.", "Alert!",
							JOptionPane.ERROR_MESSAGE);

					return;
				}
				String enemyNameToDelete = getSelectedEnemyName();
				enemyMap.remove(enemyNameToDelete);
				int rowToDelete = list.getSelectedRow();
				listModel.removeRow(rowToDelete);
				int newNumOfRow = listModel.getRowCount();
				if (newNumOfRow > 0) {
					list.setRowSelectionInterval(0, 0);
				}

			}
		});

		list.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {

					@Override
					public void valueChanged(ListSelectionEvent e) {
						if (list.getSelectedRow() != -1) {
							updateFieldDataUponNewSelection();
						}

					}
				});

		list.setDefaultEditor(Object.class, new EnemyCellEditor());
		for (JSpinner field : spinnerFields) {
			field.addChangeListener(new ChangeListener() {

				@Override
				public void stateChanged(ChangeEvent e) {

					updateSchemaDataFromView();
				}
			});
		}

		for (JRadioButton button : radioButtons) {
			button.addItemListener(new ItemListener() {

				@Override
				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {

						updateSchemaDataFromView();
					}
				}

			});
		}

		createEnemyButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String enemyName = createEnemyField.getText();

				enemyName = enemyName.trim().replaceAll(" +", " ");
				System.out.println(enemyName);
				if (EnemyUtilFunctions.newEnemyNameIsValid(enemyName, enemyMap)) {

					addEnemyNameToList(enemyName);
					createEnemyField.setText("");
					createEnemyField.setBorder(originalCreateEnemyFieldBorder);
				} else {
					createEnemyField.setBorder(new LineBorder(Color.red));
					createEnemyField.selectAll();
					createEnemyField.requestFocusInWindow();
					showInvalidEnemyNameDialog();
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
						collisionImage = ImageIO.read(file);

						collisionImageCanvas.setImage(collisionImage);
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
						enemyImage = ImageIO.read(file);
						enemyImageCanvas.setImage(enemyImage);
						enemyImageCanvas.repaint();
					} catch (IOException e1) {
					}

				} else {
					System.out.println("Cancelled");
				}
			}
		});

	}

	private void addEnemyNameToList(String enemyName) {
		int indexToPlace = listModel.getRowCount();
		System.out.println(indexToPlace);
		listModel.addRow(new String[] { enemyName });
		list.setRowSelectionInterval(indexToPlace, indexToPlace);

	}

	private void initDataFields() {
		enemyMap = new HashMap<String, SimpleMonsterSchema>();
		spinnerFields = new ArrayList<JSpinner>();
		spinnerFields.add(healthField);
		spinnerFields.add(speedField);
		spinnerFields.add(damageField);
		spinnerFields.add(rewardField);
		radioButtons = new ArrayList<JRadioButton>();
		radioButtons.add(smallButton);
		radioButtons.add(mediumButton);
		radioButtons.add(largeButton);
		radioButtons.add(flyingButton);
		radioButtons.add(groundButton);

	}

	private String getSelectedEnemyName() {
		return (String) listModel.getValueAt(list.getSelectedRow(), 0);
	}

	private void updateFieldDataUponNewSelection() {
		String name = getSelectedEnemyName();
		System.out.println(listModel.getValueAt(0, 0));
		SimpleMonsterSchema myCurrentEnemy;
		if (enemyMap.get(name) == null) {
			myCurrentEnemy = new SimpleMonsterSchema(name);
			enemyMap.put(name, myCurrentEnemy);

		} else {
			myCurrentEnemy = enemyMap.get(name);
			myCurrentEnemy.getAttributesMap().get(MonsterSchema.HEALTH);

		}

		updateViewWithSchemaData(myCurrentEnemy.getAttributesMap());
	}

	private void replaceKeysInEnemyMap(String originalKey, String newKey) {
		SimpleMonsterSchema monsterSchema = enemyMap.get(originalKey);
		enemyMap.remove(originalKey);
		enemyMap.put(newKey, monsterSchema);
	}

	/**
	 * 
	 * puts the schema data into the view field
	 * 
	 * @param map
	 *            the monster's schema attributes
	 * 
	 */
	private void updateViewWithSchemaData(Map<String, String> map) {
		// fields (spinners)
		healthField.setValue(Integer.parseInt(map.get(MonsterSchema.HEALTH)));
		speedField.setValue(Integer.parseInt(map.get(MonsterSchema.SPEED)));
		damageField.setValue(Integer.parseInt(map.get(MonsterSchema.DAMAGE)));
		rewardField.setValue(Integer.parseInt(map.get(MonsterSchema.REWARD)));
		// buttons
		ButtonModel selectedFlyButton = map.get(MonsterSchema.FLYING_OR_GROUND)
				.equals(MonsterSchema.FLYING_OR_GROUND_GROUND) ? groundButton
				.getModel() : flyingButton.getModel();
		ButtonModel selectedSizeButton;

		if (map.get(MonsterSchema.TILE_SIZE).equals(
				MonsterSchema.TILE_SIZE_SMALL))
			selectedSizeButton = smallButton.getModel();
		else if (map.get(MonsterSchema.TILE_SIZE).equals(
				MonsterSchema.TILE_SIZE_MEDIUM))
			selectedSizeButton = mediumButton.getModel();
		else
			selectedSizeButton = largeButton.getModel();
		flyingButtonGroup.setSelected(selectedFlyButton, true);
		sizeButtonGroup.setSelected(selectedSizeButton, true);
		//images
		

	}

	/**
	 * puts the view fields' data into the schema data
	 */
	private void updateSchemaDataFromView() {
		// update schema with fields
		String name = getSelectedEnemyName();
		SimpleMonsterSchema myCurrentEnemy = enemyMap.get(name);
		Integer health = (Integer) healthField.getValue();
		myCurrentEnemy.addAttribute(MonsterSchema.HEALTH, health.toString());
		Integer speed = (Integer) speedField.getValue();
		myCurrentEnemy.addAttribute(MonsterSchema.SPEED, speed.toString());
		Integer damage = (Integer) damageField.getValue();
		myCurrentEnemy.addAttribute(MonsterSchema.DAMAGE, damage.toString());
		Integer reward = (Integer) rewardField.getValue();
		myCurrentEnemy.addAttribute(MonsterSchema.REWARD, reward.toString());
		// update schema with buttons
		myCurrentEnemy.addAttribute(MonsterSchema.FLYING_OR_GROUND,
				GroupButtonUtil.getSelectedButtonText(flyingButtonGroup));
		myCurrentEnemy.addAttribute(MonsterSchema.TILE_SIZE,
				GroupButtonUtil.getSelectedButtonText(sizeButtonGroup));
		// update schema with images
	}

	private void showInvalidEnemyNameDialog() {
		JOptionPane
				.showMessageDialog(
						null,
						"Please enter a unique alphanumeric name starting with letters and greater than two characters.",
						"Alert!", JOptionPane.ERROR_MESSAGE);
	}

	private class EnemyCellEditor extends DefaultCellEditor {
		EnemyCellEditor() {
			super(new JTextField());
		}

		public boolean stopCellEditing() {
			JTable table = (JTable) getComponent().getParent();
			String originalKey = getSelectedEnemyName();

			try {
				String editedKey = (String) getCellEditorValue();

				if (!originalKey.equals(editedKey)
						&& !EnemyUtilFunctions.newEnemyNameIsValid(editedKey,
								enemyMap)) {
					JTextField textField = (JTextField) getComponent();
					textField.setBorder(new LineBorder(Color.red));
					textField.selectAll();
					textField.requestFocusInWindow();

					showInvalidEnemyNameDialog();
					return false;

				} else {
					replaceKeysInEnemyMap(originalKey, editedKey);
				}

			} catch (ClassCastException exception) {
				return false;
			}

			return super.stopCellEditing();
		}

		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			Component c = super.getTableCellEditorComponent(table, value,
					isSelected, row, column);
			((JComponent) c).setBorder(new LineBorder(Color.black));

			return c;
		}

	}

	private class EnemyTabViewBuilder {
		EnemyEditorTab myTab;

		public EnemyTabViewBuilder(EnemyEditorTab enemyEditorTab) {
			myTab = enemyEditorTab;
		}

		public JSpinner makeAttributeSpinner() {

			SpinnerModel model = new SpinnerNumberModel(1, 1, 1000, 1);
			JSpinner spinner = new JSpinner(model);
			spinner.setMaximumSize(new Dimension(200, spinner.getHeight()));
			Font bigFont = spinner.getFont().deriveFont(Font.PLAIN,
					EnemyViewConstants.X_LARGE_FONT_SIZE);
			spinner.setFont(bigFont);
			return spinner;
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

		public Component makeDesignEnemyPane() {
			JPanel result = new JPanel();
			result.setLayout(new BorderLayout());
			result.add(new JLabel("Design New Enemy"), BorderLayout.WEST);

			createEnemyField = new JTextField();
			originalCreateEnemyFieldBorder = createEnemyField.getBorder();
			createEnemyButton = new JButton("Begin");
			result.add(createEnemyField, BorderLayout.CENTER);
			result.add(createEnemyButton, BorderLayout.EAST);

			return result;
		}

		public Component makeDimensionPane() {
			JPanel result = new JPanel();
			result.setLayout(new GridLayout(1, 0));

			result.setBorder(BorderFactory.createEmptyBorder(0, // top
					20, // left
					0, // bottom
					0)); // right

			smallButton = new JRadioButton(MonsterSchema.TILE_SIZE_SMALL);
			mediumButton = new JRadioButton(MonsterSchema.TILE_SIZE_MEDIUM);
			largeButton = new JRadioButton(MonsterSchema.TILE_SIZE_LARGE);
			sizeButtonGroup = new ButtonGroup();
			sizeButtonGroup.add(smallButton);
			sizeButtonGroup.add(mediumButton);
			sizeButtonGroup.add(largeButton);
			result.add(smallButton);
			result.add(mediumButton);
			result.add(largeButton);
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

		public Component makeOverallContent() {
			list = myBuilder.makeTable();

			// Create a split pane with the two scroll panes in it.
			splitPane = myBuilder.makeSplitPane(new JScrollPane(list),
					makeEditorPane());
			return splitPane;
		}

		private JTable makeTable() {
			listModel = new DefaultTableModel(new Object[] { "Enemy Name" }, 0);
			JTable table = new JTable(listModel);
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			Font tableFont = table.getFont().deriveFont(
					EnemyViewConstants.MEDIUM_FONT_SIZE);
			table.setFont(tableFont);
			table.setRowHeight((int) EnemyViewConstants.MEDIUM_FONT_SIZE + 12);
			return table;
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

		public JComponent makeTypePane() {
			JPanel result = new JPanel();
			result.setLayout(new GridLayout(1, 0));

			result.setBorder(BorderFactory.createEmptyBorder(0, // top
					20, // left
					0, // bottom
					0)); // right

			groundButton = new JRadioButton(
					MonsterSchema.FLYING_OR_GROUND_GROUND);
			flyingButton = new JRadioButton(
					MonsterSchema.FLYING_OR_GROUND_FLYING);
			flyingButtonGroup = new ButtonGroup();
			flyingButtonGroup.add(groundButton);
			flyingButtonGroup.add(flyingButton);
			result.add(groundButton);
			result.add(flyingButton);
			return result;
		}

		private JComponent makeAttributesPane() {
			JPanel result = new JPanel();

			result.setLayout(new BorderLayout());

			result.add(myBuilder.makeLabelPane(), BorderLayout.WEST);
			result.add(myBuilder.makeFieldPane(), BorderLayout.CENTER);
			return result;
		}

		private JButton makeChooseGraphicsButton(String buttonString) {
			JButton result = new JButton(buttonString);
			return result;
		}

		private Component makeDeleteEnemyButton() {
			deleteEnemyButton = new JButton("Delete Enemy");
			return deleteEnemyButton;
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
	}
}
package main.java.author.view.tabs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import main.java.author.controller.MainController;
import main.java.author.util.EnemyUtilFunctions;
import main.java.author.view.components.ImageCanvas;
import main.java.author.view.global_constants.FontConstants;
import main.java.author.view.global_constants.ObjectEditorConstants;
import main.java.schema.TDObjectSchema;

public abstract class ObjectEditorTab extends EditorTab {

	protected JFileChooser fileChooser;
	protected BufferedImage collisionImage;
	protected BufferedImage objectImage;
	protected ImageCanvas collisionImageCanvas;
	protected ImageCanvas objectImageCanvas;
	protected DefaultTableModel listModel;
	protected JTable list;
	protected JSplitPane splitPane;
	protected List<JSpinner> spinnerFields;
	protected JButton createObjectButton;
	protected List<JRadioButton> radioButtons;
	protected JTextField createObjectField;
	protected JButton collisionImageButton;
	protected JButton deleteObjectButton;
	protected JButton objectImageButton;
	protected Border originalCreateObjectFieldBorder;
	protected TabViewBuilder myBuilder;
	protected HashMap<String, TDObjectSchema> objectMap;
	protected String defaultObjectName = "Default Object";

	public ObjectEditorTab(MainController controller) {
		super(controller);
		init();
	}

	protected void init() {
		setLayout(new BorderLayout());
		setDefaultObjectName();
		objectMap = new HashMap<String, TDObjectSchema>();
		myBuilder = createSpecificTabViewBuilder();
		this.add(myBuilder.makeDesignEnemyPane(), BorderLayout.NORTH);
		this.add(myBuilder.makeOverallContent(), BorderLayout.SOUTH);
		initDataFields();
		addObjectNameToList(defaultObjectName);
		updateFieldDataUponNewSelection();
		addListeners();
	}

	protected abstract void setDefaultObjectName();


	protected void addListeners() {

		deleteObjectButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (list.getRowCount() == 1) {
					JOptionPane.showMessageDialog(null,
							"Please design at least one enemy.", "Alert!",
							JOptionPane.ERROR_MESSAGE);

					return;
				}
				String objectNameToDelete = getSelectedObjectName();
				objectMap.remove(objectNameToDelete);
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

		list.setDefaultEditor(Object.class, new TDObjectCellEditor());
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

		createObjectButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String objectName = createObjectField.getText();

				objectName = objectName.trim().replaceAll(" +", " ");
				System.out.println(objectName);
				if (EnemyUtilFunctions.newObjectNameIsValid(objectName,
						objectMap)) {

					addObjectNameToList(objectName);
					createObjectField.setText("");
					createObjectField
							.setBorder(originalCreateObjectFieldBorder);
				} else {
					createObjectField.setBorder(new LineBorder(Color.red));
					createObjectField.selectAll();
					createObjectField.requestFocusInWindow();
					showInvalidObjectNameDialog();
				}
			}
		});

		collisionImageButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				fileChooser = new JFileChooser();
				int returnVal = fileChooser.showOpenDialog(ObjectEditorTab.this);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
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
		objectImageButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				fileChooser = new JFileChooser();
				int returnVal = fileChooser.showOpenDialog(ObjectEditorTab.this);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					try {
						System.out.println("Opening: " + file.getName() + ".\n");
						objectImage = ImageIO.read(file);
						objectImageCanvas.setImage(objectImage);
						objectImageCanvas.repaint();
					} catch (IOException e1) {
					}

				} else {
					System.out.println("Cancelled");
				}
			}
		});

	}

	protected abstract void initDataFields();

	protected abstract void updateViewWithSchemaData(Map<String, String> map);

	protected abstract void updateSchemaDataFromView();

	protected abstract TabViewBuilder createSpecificTabViewBuilder();

	private void replaceKeysInEnemyMap(String originalKey, String newKey) {
		TDObjectSchema objectSchema = objectMap.get(originalKey);
		objectMap.remove(originalKey);
		objectMap.put(newKey, objectSchema);
	}

	protected void addObjectNameToList(String objectName) {
		int indexToPlace = listModel.getRowCount();
		System.out.println(indexToPlace);
		listModel.addRow(new String[] { objectName });
		list.setRowSelectionInterval(indexToPlace, indexToPlace);

	}

	protected abstract TDObjectSchema createSpecificNewObject(String name);

	protected void showInvalidObjectNameDialog() {
		JOptionPane
				.showMessageDialog(
						null,
						"Please enter a unique alphanumeric name starting with letters and greater than two characters.",
						"Alert!", JOptionPane.ERROR_MESSAGE);
	}

	protected void updateFieldDataUponNewSelection() {
		String name = getSelectedObjectName();
		System.out.println(listModel.getValueAt(0, 0));
		TDObjectSchema myCurrentObject;
		if (objectMap.get(name) == null) {
			myCurrentObject = createSpecificNewObject(name);
			objectMap.put(name, myCurrentObject);

		} else {
			myCurrentObject = objectMap.get(name);
		}

		updateViewWithSchemaData(myCurrentObject.getAttributesMap());
	}

	protected String getSelectedObjectName() {
		return (String) listModel.getValueAt(list.getSelectedRow(), 0);
	}

	protected class TDObjectCellEditor extends DefaultCellEditor {
		public TDObjectCellEditor() {
			super(new JTextField());
		}

		@Override
		public boolean stopCellEditing() {
			JTable table = (JTable) getComponent().getParent();
			String originalKey = getSelectedObjectName();

			try {
				String editedKey = (String) getCellEditorValue();

				if (!originalKey.equals(editedKey)
						&& !EnemyUtilFunctions.newObjectNameIsValid(editedKey,
								objectMap)) {
					JTextField textField = (JTextField) getComponent();
					textField.setBorder(new LineBorder(Color.red));
					textField.selectAll();
					textField.requestFocusInWindow();

					showInvalidObjectNameDialog();
					return false;

				} else {
					replaceKeysInEnemyMap(originalKey, editedKey);
				}

			} catch (ClassCastException exception) {
				return false;
			}

			return super.stopCellEditing();
		}

		@Override
		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			Component c = super.getTableCellEditorComponent(table, value,
					isSelected, row, column);
			((JComponent) c).setBorder(new LineBorder(Color.black));

			return c;
		}

	}

	protected abstract class TabViewBuilder {
		EditorTab myTab;

		public TabViewBuilder(EditorTab editorTab) {
			myTab = editorTab;
		}

		public JSpinner makeAttributeSpinner() {

			SpinnerModel model = new SpinnerNumberModel(1, 1, 1000, 1);
			JSpinner spinner = new JSpinner(model);
			spinner.setMaximumSize(new Dimension(200, spinner.getHeight()));
			Font bigFont = spinner.getFont().deriveFont(Font.PLAIN,
					FontConstants.X_LARGE_FONT_SIZE);
			spinner.setFont(bigFont);
			return spinner;
		}

		public Component makeDesignEnemyPane() {
			JPanel result = new JPanel();
			result.setLayout(new BorderLayout());
			result.add(new JLabel("Design New Enemy"), BorderLayout.WEST);

			createObjectField = new JTextField();
			originalCreateObjectFieldBorder = createObjectField.getBorder();
			createObjectButton = new JButton("Begin");
			result.add(createObjectField, BorderLayout.CENTER);
			result.add(createObjectButton, BorderLayout.EAST);

			return result;
		}

		public Component makeOverallContent() {
			list = myBuilder.makeTable();

			// Create a split pane with the two scroll panes in it.
			splitPane = myBuilder.makeSplitPane(new JScrollPane(list),
					makeEditorPane());
			return splitPane;
		}

		private JButton makeChooseGraphicsButton(String buttonString) {
			JButton result = new JButton(buttonString);
			return result;
		}

		private JComponent makeCollisionGraphicPane() {
			JPanel result = new JPanel();
			result.setLayout(new BorderLayout());
			collisionImageCanvas = new ImageCanvas();
			collisionImageCanvas.setSize(ObjectEditorConstants.IMAGE_CANVAS_SIZE,
					ObjectEditorConstants.IMAGE_CANVAS_SIZE);
			collisionImageCanvas.setBackground(Color.BLACK);
			result.add(collisionImageCanvas, BorderLayout.NORTH);
			collisionImageButton = makeChooseGraphicsButton("Set Collision Image");
			result.add(collisionImageButton, BorderLayout.SOUTH);
			return result;
		}

		private Component makeDeleteEnemyButton() {
			deleteObjectButton = new JButton("Delete Enemy");
			return deleteObjectButton;
		}

		private JComponent makeEditorPane() {
			JPanel result = new JPanel();
			result.setLayout(new BorderLayout());
			result.add(makeAttributesPane(), BorderLayout.CENTER);
			result.add(makeImagesPane(), BorderLayout.EAST);
			result.add(makeDeleteEnemyButton(), BorderLayout.SOUTH);

			return result;
		}

		private JComponent makeEnemyGraphicPane() {
			JPanel result = new JPanel();
			result.setLayout(new BorderLayout());
			objectImageCanvas = new ImageCanvas();
			objectImageCanvas.setSize(ObjectEditorConstants.IMAGE_CANVAS_SIZE,
					ObjectEditorConstants.IMAGE_CANVAS_SIZE);
			objectImageCanvas.setBackground(Color.BLACK);
			result.add(objectImageCanvas, BorderLayout.NORTH);
			objectImageButton = makeChooseGraphicsButton("Set Enemy Image");
			result.add(objectImageButton, BorderLayout.SOUTH);
			return result;
		}

		private JComponent makeImagesPane() {
			JPanel result = new JPanel();
			result.setLayout(new BorderLayout());
			result.add(myBuilder.makeEnemyGraphicPane(), BorderLayout.NORTH);
			result.add(myBuilder.makeCollisionGraphicPane(), BorderLayout.SOUTH);
			return result;
		}

		private JSplitPane makeSplitPane(JScrollPane listScrollPane,
				JComponent editorPane) {

			JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
					listScrollPane, editorPane);

			splitPane.setDividerLocation(ObjectEditorConstants.DIVIDER_LOCATION);

			Dimension minimumSize = new Dimension(100, 50);
			listScrollPane.setMinimumSize(minimumSize);
			editorPane.setMinimumSize(minimumSize);

			splitPane.setPreferredSize(new Dimension(myTab.getWidth(), 550));
			return splitPane;
		}

		private JTable makeTable() {
			listModel = new DefaultTableModel(new Object[] { "Enemy Name" }, 0);
			JTable table = new JTable(listModel);
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			Font tableFont = table.getFont().deriveFont(
					FontConstants.MEDIUM_FONT_SIZE);
			table.setFont(tableFont);
			table.setRowHeight((int) FontConstants.MEDIUM_FONT_SIZE + 12);
			return table;
		}

		protected JComponent makeAttributesPane() {
			JPanel result = new JPanel();

			result.setLayout(new BorderLayout());

			result.add(makeLabelPane(), BorderLayout.WEST);
			result.add(makeFieldPane(), BorderLayout.CENTER);
			return result;
		}
		
		protected abstract JComponent makeFieldPane();

		protected abstract JComponent makeLabelPane();
	}

}

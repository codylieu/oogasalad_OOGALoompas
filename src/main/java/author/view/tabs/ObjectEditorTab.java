package main.java.author.view.tabs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.SpinnerUI;
import javax.swing.table.DefaultTableModel;

import main.java.author.controller.TabController;
import main.java.author.util.ObjectUtilFunctions;
import main.java.author.view.components.ImageCanvas;
import main.java.author.view.components.BehaviorTogglingRadioButton;
import main.java.author.view.global_constants.FontConstants;
import main.java.author.view.global_constants.ObjectEditorConstants;
import main.java.engine.objects.tower.TowerBehaviors;
import main.java.schema.tdobjects.TDObjectSchema;

public abstract class ObjectEditorTab extends EditorTab {

	protected DefaultTableModel listModel;
	protected JTable list;
	protected JSplitPane splitPane;
	protected List<JSpinner> spinnerFields;
	protected JButton createObjectButton;
	protected List<BehaviorTogglingRadioButton> behaviorTogglingButtons;
	protected List<ImageCanvas> imageCanvases;
	protected JTextField createObjectField;

	protected JButton deleteObjectButton;
	protected JButton towerImageButton;
	protected Border originalCreateObjectFieldBorder;
	protected ObjectTabViewBuilder myBuilder;
	protected HashMap<String, TDObjectSchema> objectMap;
	protected String objectName = "Default Object Name";

	public ObjectEditorTab(TabController towerController, String objectName) {
		super(towerController);
		this.objectName = objectName;
		init();
	}

	private void replaceKeysInObjectMap(String originalKey, String newKey) {
		TDObjectSchema objectSchema = objectMap.get(originalKey);
		objectMap.remove(originalKey);
		objectMap.put(newKey, objectSchema);
	}

	private void createObject() {
		String objectName = createObjectField.getText();

		objectName = objectName.trim().replaceAll(" +", " ");
		if (ObjectUtilFunctions.newObjectNameIsValid(objectName, objectMap)) {

			addObjectNameToList(objectName);
			createObjectField.setText("");
			createObjectField.setBorder(originalCreateObjectFieldBorder);
		} else {
			createObjectField.setBorder(new LineBorder(Color.red));
			createObjectField.selectAll();
			createObjectField.requestFocusInWindow();
			showInvalidObjectNameDialog();
		}
	}
	
	protected Serializable addCastToAttribute(Serializable attribute) {
		boolean shouldCast = false;
		if (attribute instanceof Integer) {
			shouldCast = true;
		}
		
		Double doubleAttr = null;
		if (shouldCast) {
			doubleAttr = Double.valueOf(((Integer) attribute).intValue());
		}
		return shouldCast ? doubleAttr : attribute;	
	}

	protected void addListeners() {

		createObjectField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					createObject();
				}
			}
		});

		deleteObjectButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (list.getRowCount() == 1) {
					JOptionPane.showMessageDialog(null,
							"Please design at least one " + objectName,
							"Alert!", JOptionPane.ERROR_MESSAGE);

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
		if (behaviorTogglingButtons != null) {
			for (BehaviorTogglingRadioButton button : behaviorTogglingButtons) {
				button.addItemListener(new ItemListener() {

					@Override
					public void itemStateChanged(ItemEvent e) {
						BehaviorTogglingRadioButton button = (BehaviorTogglingRadioButton) e
								.getSource();
						button.toggle();
						updateSchemaDataFromView();

					}

				});
			}
		}

		createObjectButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				createObject();
			}

		});

	}

	protected void addObjectNameToList(String objectName) {
		int indexToPlace = listModel.getRowCount();
		listModel.addRow(new String[] { objectName });
		list.setRowSelectionInterval(indexToPlace, indexToPlace);

	}

	protected abstract TDObjectSchema createSpecificNewObject(String name);

	protected abstract ObjectTabViewBuilder createSpecificTabViewBuilder();

	protected String getSelectedObjectName() {
		return (String) listModel.getValueAt(list.getSelectedRow(), 0);
	}

	protected TDObjectSchema getSelectedObject() {
		return objectMap.get(getSelectedObjectName());
	}

	protected void init() {
		setLayout(new BorderLayout());
		objectMap = new HashMap<String, TDObjectSchema>();
		myBuilder = createSpecificTabViewBuilder();
		myBuilder.instantiateAndClumpFields();
		this.add(myBuilder.makeDesignObjectPane(), BorderLayout.NORTH);
		this.add(myBuilder.makeOverallContent(), BorderLayout.SOUTH);
		addObjectNameToList(objectName);
		addListeners();
		updateFieldDataUponNewSelection();

	}

	protected void showInvalidObjectNameDialog() {
		JOptionPane
				.showMessageDialog(
						null,
						"Please enter a unique alphanumeric name starting with letters and greater than two characters.",
						"Alert!", JOptionPane.ERROR_MESSAGE);
	}

	protected void updateFieldDataUponNewSelection() {
		String name = getSelectedObjectName();
		TDObjectSchema myCurrentObject;
		if (objectMap.get(name) == null) {
			myCurrentObject = createSpecificNewObject(name);
			objectMap.put(name, myCurrentObject);

		} else {
			myCurrentObject = objectMap.get(name);
		}
		updateViewWithSchemaData(myCurrentObject.getAttributesMap());
	}

	/**
	 * puts the view fields' data into the schema data
	 */
	protected void updateSchemaDataFromView() {
		// update schema with fields
		TDObjectSchema myCurrentObject = getSelectedObject();

		for (JSpinner spinner : spinnerFields) {

			myCurrentObject.addAttribute(spinner.getName(),
					(Integer) spinner.getValue());
		}

		for (ImageCanvas canvas : imageCanvases) {
			
			String relativePath = new File((String) canvas.getImagePath()).getName();
			myCurrentObject.addAttribute(canvas.getName(),
					relativePath);
			
		}
		

	}

	protected void updateViewWithSchemaData(Map<String, Serializable> map) {
		// fields (spinners)

		for (JSpinner spinner : spinnerFields) {
			spinner.setValue(map.get(spinner.getName()));
		}

		for (ImageCanvas canvas : imageCanvases) {
			canvas.clearImagePath();
			if (map.get(canvas.getName()) != "") {
				try {
					canvas.setImageFromPath((String) map.get(canvas.getName()));
					canvas.repaint();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				canvas.repaint();
			}
		}
	}

	protected class FileChooserListener implements ActionListener {

		private ImageCanvas myCanvas;

		public FileChooserListener(ImageCanvas objectImageCanvas) {
			myCanvas = objectImageCanvas;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
			int returnVal = fileChooser.showOpenDialog(ObjectEditorTab.this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				try {

					myCanvas.setImageFromPath(file.getAbsolutePath());
					updateSchemaDataFromView();
					myCanvas.repaint();
				} catch (IOException e1) {
				}

			} else {
				System.out.println("Cancelled");
			}
		}
	}

	protected abstract class ObjectTabViewBuilder {

		EditorTab myTab;

		public ObjectTabViewBuilder(EditorTab editorTab) {
			myTab = editorTab;
		}

		private Component makeAttributesPane() {
			JPanel result = new JPanel();
			result.setLayout(new BorderLayout());
			result.add(makeFieldPane(), BorderLayout.CENTER);
			if (behaviorTogglingButtons != null) {
				result.add(makeTypeTogglePane(), BorderLayout.NORTH);
			}
			return result;
		}

		private JSpinner makeAttributeSpinner(String labelString, int max) {

			SpinnerModel model = new SpinnerNumberModel(1, 1, 1000, 1);
			JSpinner spinner = new JSpinner(model);
			spinner.setMaximumSize(new Dimension(200, spinner.getHeight()));
			Font bigFont = spinner.getFont().deriveFont(Font.PLAIN,
					FontConstants.LARGE_FONT_SIZE);
			spinner.setFont(bigFont);
			spinner.setName(labelString);
			return spinner;
		}

		private Component makeDeleteObjectButton() {
			deleteObjectButton = new JButton("Delete " + objectName);
			return deleteObjectButton;
		}

		private JComponent makeEditorPane() {
			JPanel result = new JPanel();
			result.setLayout(new BorderLayout());
			result.add(makeAttributesPane(), BorderLayout.CENTER);
			result.add(makeImagesPane(), BorderLayout.EAST);
			result.add(makeDeleteObjectButton(), BorderLayout.SOUTH);

			return result;
		}

		protected abstract JComponent makePrimaryObjectGraphicPane();

		private JComponent makeImagesPane() {
			JPanel result = new JPanel();
			result.setLayout(new BorderLayout());
			result.add(myBuilder.makePrimaryObjectGraphicPane(),
					BorderLayout.CENTER);
			result.add(myBuilder.makeSecondaryImagesGraphicPane(),
					BorderLayout.SOUTH);
			return result;
		}

		private JSplitPane makeSplitPane(JScrollPane listScrollPane,
				JComponent editorPane) {

			JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
					listScrollPane, editorPane);

			splitPane
					.setDividerLocation(ObjectEditorConstants.DIVIDER_LOCATION);

			Dimension minimumSize = new Dimension(100, 50);
			listScrollPane.setMinimumSize(minimumSize);
			editorPane.setMinimumSize(minimumSize);

			splitPane.setPreferredSize(new Dimension(myTab.getWidth(), 550));
			return splitPane;
		}

		private JTable makeTable() {
			listModel = new DefaultTableModel(new Object[] { objectName
					+ " Name" }, 0);
			JTable table = new JTable(listModel);
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			Font tableFont = table.getFont().deriveFont(
					FontConstants.MEDIUM_FONT_SIZE);
			table.setFont(tableFont);
			table.setRowHeight((int) FontConstants.MEDIUM_FONT_SIZE + 12);
			return table;
		}

		protected abstract void instantiateAndClumpFields();

		protected JSpinner makeAttributeSpinner(String labelString) {
			return makeAttributeSpinner(labelString, 1000);
		}

		protected JSpinner makeAttributeSpinner(String labelString,
				boolean percentBased) {

			return percentBased ? makeAttributeSpinner(labelString, 100)
					: makeAttributeSpinner(labelString);
		}

		protected JButton makeChooseGraphicsButton(String buttonString) {
			JButton result = new JButton(buttonString);
			return result;
		}

		protected Component makeDesignObjectPane() {
			JPanel result = new JPanel();
			result.setLayout(new BorderLayout());
			result.add(new JLabel("Design New " + objectName),
					BorderLayout.WEST);

			createObjectField = new JTextField();
			originalCreateObjectFieldBorder = createObjectField.getBorder();
			createObjectButton = new JButton("Begin");
			result.add(createObjectField, BorderLayout.CENTER);
			result.add(createObjectButton, BorderLayout.EAST);

			return result;
		}

		protected abstract JComponent makeFieldPane();

		protected Component makeFieldTile(JComponent component) {
			JPanel result = new JPanel();
			result.setLayout(new BorderLayout());
			JLabel label = new JLabel(component.getName(),
					SwingConstants.CENTER);
			result.add(label, BorderLayout.NORTH);
			result.add(component, BorderLayout.CENTER);
			return result;

		}

		protected Component makeOverallContent() {
			list = myBuilder.makeTable();

			// Create a split pane with the two scroll panes in it.
			splitPane = myBuilder.makeSplitPane(new JScrollPane(list),
					makeEditorPane());
			return splitPane;
		}

		protected abstract JComponent makeSecondaryImagesGraphicPane();

		protected Component makeTypeTogglePane() {
			JPanel result = new JPanel();
			result.setLayout(new GridLayout(1, 0));
			for (BehaviorTogglingRadioButton button : behaviorTogglingButtons) {
				result.add(button);
			}

			TitledBorder title;
			Border loweredbevel = BorderFactory.createLoweredBevelBorder();
			title = BorderFactory.createTitledBorder(loweredbevel, objectName
					+ " Type");
			title.setTitleJustification(TitledBorder.CENTER);
			result.setBorder(title);
			return result;
		}

	}

	protected class TDObjectCellEditor extends DefaultCellEditor {
		public TDObjectCellEditor() {
			super(new JTextField());
		}

		@Override
		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			Component c = super.getTableCellEditorComponent(table, value,
					isSelected, row, column);
			((JComponent) c).setBorder(new LineBorder(Color.black));

			return c;
		}

		@Override
		public boolean stopCellEditing() {
			JTable table = (JTable) getComponent().getParent();
			String originalKey = getSelectedObjectName();

			try {
				String editedKey = (String) getCellEditorValue();

				if (!originalKey.equals(editedKey)
						&& !ObjectUtilFunctions.newObjectNameIsValid(editedKey,
								objectMap)) {
					JTextField textField = (JTextField) getComponent();
					textField.setBorder(new LineBorder(Color.red));
					textField.selectAll();
					textField.requestFocusInWindow();

					showInvalidObjectNameDialog();
					return false;

				} else {
					replaceKeysInObjectMap(originalKey, editedKey);
				}

			} catch (ClassCastException exception) {
				return false;
			}

			return super.stopCellEditing();
		}

	}

}
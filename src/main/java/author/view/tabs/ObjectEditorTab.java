package main.java.author.view.tabs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
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

import com.sun.xml.internal.ws.util.StringUtils;

import main.java.author.controller.TabController;
import main.java.author.util.ObjectUtilFunctions;
import main.java.author.view.AuthoringView;
import main.java.author.view.components.ImageCanvas;
import main.java.author.view.components.TowerBehaviorTogglingRadioButton;
import main.java.author.view.global_constants.FontConstants;
import main.java.author.view.global_constants.ObjectEditorConstants;
import main.java.engine.objects.powerup.TDPowerupPowerup;
import main.java.engine.objects.tower.TowerBehaviors;
import main.java.schema.tdobjects.TDObjectSchema;

/**
 * @author garysheng An abstract editor tab that allows for easy creation of
 *         concrete object editor tabs. Allows for a dynamic range of properties
 *         to edit, and provides a framework for updating information to
 *         AbstractSchemas and updating the view with information retrieved from
 *         these AbstractSchemas
 */
public abstract class ObjectEditorTab extends EditorTab {

	protected DefaultTableModel listModel;
	protected JTable list;
	protected JSplitPane splitPane;
	protected List<JSpinner> spinnerFields;
	protected JButton createObjectButton;
	protected List<TowerBehaviorTogglingRadioButton> behaviorTogglingButtons;
	protected List<ImageCanvas> imageCanvases;
	protected JTextField createObjectField;

	protected JButton deleteObjectButton;
	protected JButton towerImageButton;
	protected Border originalCreateObjectFieldBorder;
	protected ObjectTabViewBuilder myBuilder;
	protected HashMap<String, TDObjectSchema> objectMap;
	protected String objectName = "Default Object Name";

	public ObjectEditorTab(TabController controller, String objectName) {
		super(controller);
		this.objectName = objectName;
		init();
	}

	/**
	 * 
	 * Called when you want to keep an object value in an object map but simply
	 * replace the keys.
	 * 
	 * @param originalKey
	 * @param newKey
	 */
	private void replaceKeysInObjectMap(String originalKey, String newKey) {
		TDObjectSchema objectSchema = objectMap.get(originalKey);
		objectMap.remove(originalKey);
		objectMap.put(newKey, objectSchema);
	}

	/**
	 * Everything that needs to happen to add an object and start editing it
	 */
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

	/**
	 * Casts a serializable attribute into a Serializable object
	 * 
	 * @param attribute
	 * @return Serializable version of the attributes
	 */
	protected Serializable addCastToAttribute(Serializable attribute) {
		boolean shouldCastToDouble = false;
		if (attribute instanceof Integer) {
			shouldCastToDouble = true;
		}

		Double doubleAttr = null;
		if (shouldCastToDouble) {
			doubleAttr = Double.valueOf(((Integer) attribute).intValue());
		}
		return shouldCastToDouble ? doubleAttr : attribute;
	}

	/**
	 * Adds listeners to view components. Allows users to create and delete
	 * objects, and adds important list selection functionality.
	 */
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
			for (TowerBehaviorTogglingRadioButton button : behaviorTogglingButtons) {
				button.addItemListener(new ItemListener() {

					@Override
					public void itemStateChanged(ItemEvent e) {
						TowerBehaviorTogglingRadioButton button = (TowerBehaviorTogglingRadioButton) e
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

	/**
	 * Adds an object name to the listModel
	 * 
	 * @param objectName
	 */
	protected void addObjectNameToList(String objectName) {
		int indexToPlace = listModel.getRowCount();
		listModel.addRow(new String[] { objectName });
		list.setRowSelectionInterval(indexToPlace, indexToPlace);

	}

	/**
	 * @param name
	 * @return a new Schema with a specific name
	 */
	protected abstract TDObjectSchema createSpecificNewObject(String name);

	/**
	 * @return a specific tab view builder
	 */
	protected abstract ObjectTabViewBuilder createSpecificTabViewBuilder();

	/**
	 * @return the object name selected in the list
	 */
	protected String getSelectedObjectName() {
		return (String) listModel.getValueAt(list.getSelectedRow(), 0);
	}

	/**
	 * @return the schema corresponding to the selected list item
	 */
	protected TDObjectSchema getSelectedObject() {
		return objectMap.get(getSelectedObjectName());
	}

	/**
	 * creates all view components, creates a default object, and adds listeners
	 */
	protected void init() {
		setLayout(new BorderLayout());
		imageCanvases = new ArrayList<ImageCanvas>();
		objectMap = new HashMap<String, TDObjectSchema>();
		myBuilder = createSpecificTabViewBuilder();
		myBuilder.instantiateAndClumpFields();
		this.add(myBuilder.makeDesignObjectPane(), BorderLayout.NORTH);
		this.add(myBuilder.makeOverallContent(), BorderLayout.SOUTH);
		addObjectNameToList(objectName + " One");
		addListeners();
		updateFieldDataUponNewSelection();

	}

	/**
	 * Error message to display if you enter a bad object name
	 */
	protected void showInvalidObjectNameDialog() {
		JOptionPane
				.showMessageDialog(
						null,
						"Please enter a unique alphanumeric name starting with letters and greater than two characters.",
						"Alert!", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * updates the view field data based on a new list selection
	 */
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
	 * puts the view fields' data into the selected schema
	 */
	protected void updateSchemaDataFromView() {
		// update schema with fields
		TDObjectSchema myCurrentObject = getSelectedObject();

		for (JSpinner spinner : spinnerFields) {

			myCurrentObject.addAttribute(spinner.getName(),
					(Serializable) spinner.getValue());
		}

		for (ImageCanvas canvas : imageCanvases) {

			String relativePath = new File((String) canvas.getImagePath())
					.getAbsolutePath();

			if (relativePath != null && !relativePath.isEmpty()) {
				myCurrentObject.addAttribute(canvas.getName(), relativePath);
			}

		}

	}

	/**
	 * Updates the view with schema attribute values from the current selected
	 * object
	 * 
	 * @param map
	 *            of attributes to populate the view with
	 */
	protected void updateViewWithSchemaData(Map<String, Serializable> map) {
		// fields (spinners)

		for (JSpinner spinner : spinnerFields) {
			if (map.get(spinner.getName()) != null) {
				spinner.setValue((Integer) map.get(spinner.getName()));
			}
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

	/**
	 * @author garysheng Listener that allows you to add an image through a file
	 *         chooser
	 */
	protected class FileChooserListener implements ActionListener {

		private ImageCanvas myCanvas;

		public FileChooserListener(ImageCanvas objectImageCanvas) {
			myCanvas = objectImageCanvas;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			final JFileChooser fileChooser = new JFileChooser(new File(
					AuthoringView.DEFAULT_RESOURCES_DIR));
			fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {

				@Override
				public String getDescription() {
					return "*.png,*.jpg,*.gif";
				}

				@Override
				public boolean accept(File f) {
					if (f.isDirectory()) {
						return true;
					}
					final String name = f.getName();
					return name.endsWith(".png") || name.endsWith(".jpg")
							|| name.endsWith(".jpeg") || name.endsWith(".gif");
				}
			});

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
				// System.out.println("Cancelled");
			}
		}
	}

	/**
	 * @author garysheng Abstracts the view creation from the business logic
	 */
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

		/**
		 * @return the object graphic pane that is top right of the object
		 *         editor tab
		 */
		protected abstract JComponent makePrimaryObjectGraphicPane();

		private JComponent makeImagesPane() {
			JPanel result = new JPanel();
			result.setLayout(new BorderLayout());
			result.add(myBuilder.makePrimaryObjectGraphicPane(),
					BorderLayout.CENTER);
			JComponent secondaryImagesGraphicsPane = myBuilder
					.makeSecondaryImagesGraphicPane();
			if (secondaryImagesGraphicsPane != null) {
				result.add(myBuilder.makeSecondaryImagesGraphicPane(),
						BorderLayout.SOUTH);
			}
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

		/**
		 * @return the main attributes pane comprised of tiles, where each tile
		 *         corresponds to an attribute
		 */
		protected abstract JComponent makeFieldPane();

		protected Component makeFieldTile(JComponent component) {
			JPanel result = new JPanel();
			result.setLayout(new BorderLayout());
			JLabel label = new JLabel(component.getName(),
					SwingConstants.CENTER);
			result.add(label, BorderLayout.NORTH);
			result.add(component, BorderLayout.CENTER);
			result.setBorder(BorderFactory.createEtchedBorder());
			return result;

		}

		protected Component makeOverallContent() {
			list = myBuilder.makeTable();

			// Create a split pane with the two scroll panes in it.
			splitPane = myBuilder.makeSplitPane(new JScrollPane(list),
					makeEditorPane());
			return splitPane;
		}

		/**
		 * @return the image graphic pane that is displayed on the bottom right
		 *         of the object editor tab
		 */
		protected abstract JComponent makeSecondaryImagesGraphicPane();

		protected Component makeTypeTogglePane() {
			JPanel result = new JPanel();
			result.setLayout(new GridLayout(1, 0));
			for (TowerBehaviorTogglingRadioButton button : behaviorTogglingButtons) {
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

	/**
	 * @author garysheng The subclass of DefaultCellEditor that allows for error
	 *         checking of name input
	 */
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
package main.java.author.view.tabs.item;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import main.java.author.controller.TabController;
import main.java.author.controller.tabbed_controllers.ItemController;
import main.java.author.view.components.ImageCanvas;
import main.java.author.view.global_constants.ObjectEditorConstants;
import main.java.author.view.tabs.EditorTab;
import main.java.author.view.tabs.ObjectEditorTab;
import main.java.schema.tdobjects.ItemSchema;
import main.java.schema.tdobjects.TDObjectSchema;
import main.java.schema.tdobjects.TowerSchema;
import main.java.schema.tdobjects.items.AnnihilatorItemSchema;
import main.java.schema.tdobjects.items.AreaBombItemSchema;
import main.java.schema.tdobjects.items.InstantFreezeItemSchema;
import main.java.schema.tdobjects.items.LifeSaverItemSchema;
import main.java.schema.tdobjects.items.RowBombItemSchema;

/**
 * @author dennis park
 * allows for easier creation of item editor sub tabs
 */
public abstract class AbstractItemEditorSubTab extends ObjectEditorTab {
	protected JSpinner costSpinner, buildUpSpinner, flashSpinner, damageSpinner,
			rangeSpinner, freezeSpinner;
	protected List<ItemSchema> itemSchemas;
	protected List<JRadioButton> allButtons;
	protected JButton itemImageButton;
	protected ImageCanvas itemImageCanvas;
	protected JTextArea descriptionTextArea; 

	public AbstractItemEditorSubTab(TabController itemController, String objectName) {
		super(itemController, objectName);
	}

	@Override
	public void saveTabData() {}

	public List<ItemSchema> getItemSchemas() {
		itemSchemas = new ArrayList<ItemSchema>();

		for (TDObjectSchema item : objectMap.values()) {
			ItemSchema itemSchema = (ItemSchema) item;
			itemSchemas.add(itemSchema);
		}
		return itemSchemas;
	}

	@Override
	protected void updateSchemaDataFromView() {
		super.updateSchemaDataFromView();
		TDObjectSchema myCurrentObject = getSelectedObject();
		myCurrentObject.addAttribute(ItemSchema.DESCRIPTION, descriptionTextArea.getText());

	}

	@Override
	protected void updateViewWithSchemaData(Map<String, Serializable> map) {
		super.updateViewWithSchemaData(map);
		descriptionTextArea.setText((String) map.get(TowerSchema.DESCRIPTION));
		
	}

	@Override
	protected void addListeners() {
		super.addListeners();
		itemImageButton.addActionListener(new FileChooserListener(
				itemImageCanvas));
		if (allButtons != null) {
			for (JRadioButton button : allButtons) {
				button.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						updateSchemaDataFromView();
					}
				});
			}
		}
		descriptionTextArea.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				updateSchemaDataFromView();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				updateSchemaDataFromView();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				updateSchemaDataFromView();
			}
		});
	}

	public abstract class AbstractItemTabViewBuilder extends
			ObjectTabViewBuilder {

		public AbstractItemTabViewBuilder(EditorTab editorTab) {
			super(editorTab);
		}
		
		@Override
		protected void instantiateAndClumpFields() {
			//description text
			descriptionTextArea = makeDescriptionArea();
			itemImageCanvas = new ImageCanvas(true, ItemSchema.IMAGE_NAME);
			imageCanvases.add(itemImageCanvas);
		}

		private JTextArea makeDescriptionArea() {
			JTextArea result = new JTextArea();
			result.setLineWrap(true);
			result.setName(ItemSchema.DESCRIPTION);
			return result;
		}

		@Override
		protected JComponent makePrimaryObjectGraphicPane() {
			JPanel result = new JPanel();
			result.setLayout(new BorderLayout());

			itemImageCanvas.setSize(new Dimension(
					ObjectEditorConstants.IMAGE_CANVAS_SIZE,
					ObjectEditorConstants.IMAGE_CANVAS_SIZE));
			itemImageCanvas.setBackground(Color.BLACK);
			result.add(itemImageCanvas, BorderLayout.CENTER);
			itemImageButton = makeChooseGraphicsButton("Set " + objectName
					+ " Image");
			result.add(itemImageButton, BorderLayout.SOUTH);
			return result;
		}

		@Override
		protected JComponent makeSecondaryImagesGraphicPane() {
			return null;
		}

		@Override
		protected JComponent makeFieldPane() {
			JPanel result = new JPanel(new GridLayout(0, 2));
			for (JSpinner spinner : spinnerFields) {
				result.add(makeFieldTile(spinner));
			}
			result.add(makeFieldTile(descriptionTextArea));
			return result;
		}
	}
}

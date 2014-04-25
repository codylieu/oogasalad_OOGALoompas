package main.java.author.view.tabs.item;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import main.java.author.controller.TabController;
import main.java.author.view.components.ImageCanvas;
import main.java.author.view.global_constants.ObjectEditorConstants;
import main.java.author.view.tabs.EditorTab;
import main.java.schema.tdobjects.ItemSchema;
import main.java.schema.tdobjects.TDObjectSchema;

public class AnnihilatorItemEditorTab extends AbstractItemEditorTab{

	public AnnihilatorItemEditorTab(TabController itemController,
			String objectName) {
		super(itemController, objectName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected ObjectTabViewBuilder createSpecificTabViewBuilder() {
		return new AnnihilatorItemTabViewBuilder(this);
	}
	
	@Override
	protected TDObjectSchema createSpecificNewObject(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private class AnnihilatorItemTabViewBuilder extends AbstractItemTabViewBuilder {

		public AnnihilatorItemTabViewBuilder(EditorTab editorTab) {
			super(editorTab);
			// TODO Auto-generated constructor stub
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
		protected void instantiateAndClumpFields() {
			costSpinner = makeAttributeSpinner(ItemSchema.COST);
			timeSpinner = makeAttributeSpinner(ItemSchema.BUILDUP_TIME);
			damageSpinner = makeAttributeSpinner(ItemSchema.DAMAGE);
			flashSpinner = makeAttributeSpinner(ItemSchema.FLASH_INTERVAL);
			
			JSpinner[] spinners = {costSpinner, timeSpinner, damageSpinner, flashSpinner};
			spinnerFields = new ArrayList<JSpinner>(Arrays.asList(spinners));
			itemImageCanvas = new ImageCanvas(true, ItemSchema.IMAGE_NAME);
		}

		@Override
		protected JComponent makeFieldPane() {
			JPanel result = new JPanel(new GridLayout(0, 2));
			for (JSpinner spinner : spinnerFields) {
				result.add(makeFieldTile(spinner));
			}
			return result;
		}
	}
}

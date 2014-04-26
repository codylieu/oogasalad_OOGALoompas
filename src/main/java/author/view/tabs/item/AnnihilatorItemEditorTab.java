package main.java.author.view.tabs.item;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JSpinner;

import main.java.author.controller.TabController;
import main.java.author.view.components.ImageCanvas;
import main.java.author.view.tabs.EditorTab;
import main.java.schema.tdobjects.ItemSchema;
import main.java.schema.tdobjects.TDObjectSchema;
import main.java.schema.tdobjects.items.AnnihilatorItemSchema;
import main.java.schema.tdobjects.items.AreaBombItemSchema;

public class AnnihilatorItemEditorTab extends AbstractItemEditorTab{
	
	public AnnihilatorItemEditorTab(TabController itemController,
			String objectName) {
		super(itemController, objectName);
	}

	@Override
	protected ObjectTabViewBuilder createSpecificTabViewBuilder() {
		return new AnnihilatorItemTabViewBuilder(this);
	}
	
	@Override
	protected TDObjectSchema createSpecificNewObject(String name) {
		return new AnnihilatorItemSchema(name);
	}
	
	private class AnnihilatorItemTabViewBuilder extends AbstractItemTabViewBuilder {

		public AnnihilatorItemTabViewBuilder(EditorTab editorTab) {
			super(editorTab);
		}

		@Override
		protected void instantiateAndClumpFields() {
			costSpinner = makeAttributeSpinner(ItemSchema.COST);
			timeSpinner = makeAttributeSpinner(ItemSchema.BUILDUP_TIME);
			damageSpinner = makeAttributeSpinner(ItemSchema.DAMAGE);
			flashSpinner = makeAttributeSpinner(ItemSchema.FLASH_INTERVAL);
			rangeSpinner = makeAttributeSpinner(AreaBombItemSchema.RANGE);
			
			JSpinner[] spinners = {costSpinner, timeSpinner, damageSpinner, flashSpinner, rangeSpinner};
			spinnerFields = new ArrayList<JSpinner>(Arrays.asList(spinners));
			itemImageCanvas = new ImageCanvas(true, ItemSchema.IMAGE_NAME);
		}
	}
}

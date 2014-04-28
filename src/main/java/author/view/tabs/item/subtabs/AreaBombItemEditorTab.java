package main.java.author.view.tabs.item.subtabs;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JSpinner;

import main.java.author.controller.TabController;
import main.java.author.view.components.ImageCanvas;
import main.java.author.view.tabs.EditorTab;
import main.java.author.view.tabs.item.AbstractItemEditorSubTab;
import main.java.schema.tdobjects.ItemSchema;
import main.java.schema.tdobjects.TDObjectSchema;
import main.java.schema.tdobjects.items.AreaBombItemSchema;

/**
 * @author dennispark
 * makes areabomb sub-tab within item editor tab
 */
public class AreaBombItemEditorTab extends AbstractItemEditorSubTab{

	public AreaBombItemEditorTab(TabController itemController, String objectName) {
		super(itemController, objectName);
	}

	@Override
	protected TDObjectSchema createSpecificNewObject(String name) {
		return new AreaBombItemSchema(name);
	}

	@Override
	protected ObjectTabViewBuilder createSpecificTabViewBuilder() {
		return new AreaBombItemTabViewBuilder(this);
	}

	private class AreaBombItemTabViewBuilder extends AbstractItemTabViewBuilder {

		public AreaBombItemTabViewBuilder(EditorTab editorTab) {
			super(editorTab);
		}

		@Override
		protected void instantiateAndClumpFields() {
			super.instantiateAndClumpFields();
			costSpinner = makeAttributeSpinner(ItemSchema.COST);
			damageSpinner = makeAttributeSpinner(ItemSchema.DAMAGE);
			buildUpSpinner = makeAttributeSpinner(ItemSchema.BUILDUP_TIME);
			flashSpinner = makeAttributeSpinner(ItemSchema.FLASH_INTERVAL);
			rangeSpinner = makeAttributeSpinner(AreaBombItemSchema.RANGE);
			
			JSpinner[] spinners = {costSpinner, buildUpSpinner, flashSpinner, rangeSpinner, damageSpinner};
			spinnerFields = new ArrayList<JSpinner>(Arrays.asList(spinners));
			itemImageCanvas = new ImageCanvas(true, ItemSchema.IMAGE_NAME);
		}
	}
}

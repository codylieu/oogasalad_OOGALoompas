package main.java.schema.tdobjects.items;

import main.java.author.view.tabs.item.ItemViewConstants;
import main.java.engine.objects.powerup.AnnihilatorPowerup;
import main.java.engine.objects.powerup.AreaBombPowerup;
import main.java.schema.tdobjects.ItemSchema;

import java.util.Set;

/**
 * 
 * This is a settings object for a specific type of AreaBomb.
 */
public class AreaBombItemSchema extends ItemSchema {
	public static final Class<AreaBombPowerup> MY_CONCRETE_TYPE = AreaBombPowerup.class;
	public static final String RANGE = "Range";

	public AreaBombItemSchema() {
		super(MY_CONCRETE_TYPE);
	}

	/**
	 * @param name name of monster
	 */
	public AreaBombItemSchema(String name) {
		this();
		populateDefaultAttributes(name);
	}

	public void populateDefaultAttributes(String name) {
		addAttribute(ItemSchema.NAME, name);
		addAttribute(ItemSchema.BUILDUP_TIME, ItemViewConstants.BUILDUP_DEFAULT);
		addAttribute(ItemSchema.COST, ItemViewConstants.COST_DEFAULT);
		addAttribute(ItemSchema.DAMAGE, ItemViewConstants.DAMAGE_DEFAULT);
		addAttribute(ItemSchema.FLASH_INTERVAL, ItemViewConstants.FLASH_INTERVAL_DEFAULT);
		addAttribute(ItemSchema.IMAGE_NAME, ItemViewConstants.IMAGE_DEFAULT);
		addAttribute(AreaBombItemSchema.RANGE, ItemViewConstants.RANGE_DEFAULT);
	}

	@Override
	protected Set<String> populateAdditionalAttributes() {
		myAttributeSet.add(AreaBombItemSchema.RANGE);
		return myAttributeSet;
	}
}
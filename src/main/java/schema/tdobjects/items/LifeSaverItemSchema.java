package main.java.schema.tdobjects.items;

import main.java.author.view.tabs.enemy.EnemyViewDefaults;
import main.java.author.view.tabs.item.ItemViewConstants;
import main.java.engine.objects.item.Annihilator;
import main.java.engine.objects.item.LifeSaver;
import main.java.engine.objects.monster.SimpleMonster;
import main.java.schema.tdobjects.ItemSchema;
import main.java.schema.tdobjects.MonsterSchema;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * This is a settings object for a specific type of LifeSaver.
 */
public class LifeSaverItemSchema extends ItemSchema {
	public static final Class<LifeSaver> MY_CONCRETE_TYPE = LifeSaver.class;

	public LifeSaverItemSchema() {
		super(MY_CONCRETE_TYPE);
	}

	/**
	 * @param name name of monster
	 */
	public LifeSaverItemSchema(String name) {
		this();
		populateDefaultAttributes(name);
	}

	public void populateDefaultAttributes(String name) {
		addAttribute(ItemSchema.NAME, name);
		addAttribute(ItemSchema.BUILDUP_TIME, ItemViewConstants.BUILDUP_DEFAULT);
		addAttribute(ItemSchema.COST, ItemViewConstants.COST_DEFAULT);
		addAttribute(ItemSchema.FLASH_INTERVAL, ItemViewConstants.FLASH_INTERVAL_DEFAULT);
		addAttribute(ItemSchema.IMAGE_NAME, ItemViewConstants.IMAGE_DEFAULT);
	}

	@Override
	protected Set<String> populateAdditionalAttributes() {
		return new HashSet<String>(); // empty set, no new attributes
	}
}
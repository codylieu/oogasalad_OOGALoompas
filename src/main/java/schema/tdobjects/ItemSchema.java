package main.java.schema.tdobjects;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import main.java.author.view.tabs.item.ItemViewConstants;
import main.java.author.view.tabs.tower.TowerViewConstants;
import main.java.engine.objects.powerup.SimplePowerup;
import main.java.engine.objects.powerup.TDItem;
import main.java.engine.objects.powerup.PowerupBehaviors;
import main.java.engine.objects.tower.TowerBehaviors;

public class ItemSchema extends TDObjectSchema{
	public static final String COST = "Cost";
	public static final String BUILDUP_TIME = "Build Up Time (sec)";
	public static final String DAMAGE = "Damage";
	public static final String LOCATION = "Location";
	public static final String LIVES_TO_GRANT = "Lives to Grant";
	public static final String RANGE = "Damage range";
	public static final String FREEZE_DURATION = "Freeze Slowdown duration";
	public static final String FREEZE_SLOWDOWN_PROPORTION = "Freeze Slowdown (%)";
	public static final String ITEM_BEHAVIORS = "Item Behaviors";

	public ItemSchema() {
		super(SimplePowerup.class);
		myAttributeSet.add(ItemSchema.BUILDUP_TIME);
		myAttributeSet.add(ItemSchema.COST);
		myAttributeSet.add(ItemSchema.DAMAGE);
		myAttributeSet.add(ItemSchema.IMAGE_NAME);
		myAttributeSet.add(ItemSchema.NAME);
		myAttributeSet.add(ItemSchema.LOCATION);
		myAttributeSet.add(ItemSchema.LIVES_TO_GRANT);
		myAttributeSet.add(ItemSchema.RANGE);
		myAttributeSet.add(ItemSchema.FREEZE_DURATION);
		myAttributeSet.add(ItemSchema.FREEZE_SLOWDOWN_PROPORTION);
		myAttributeSet.add(ItemSchema.ITEM_BEHAVIORS);
	}
	
	public ItemSchema(String name) {
		this();
		populateDefaultAttributes(name);
	}

	@Override
	public void populateDefaultAttributes(String name) {
		addAttribute(ItemSchema.NAME, name);
		addAttribute(ItemSchema.COST, ItemViewConstants.COST_DEFAULT);
		addAttribute(ItemSchema.DAMAGE, ItemViewConstants.DAMAGE_DEFAULT);
		addAttribute(ItemSchema.RANGE, ItemViewConstants.RANGE_DEFAULT);
		addAttribute(ItemSchema.BUILDUP_TIME, ItemViewConstants.BUILDUP_DEFAULT);
//		addAttribute(ItemSchema.FREEZE_SLOWDOWN_PROPORTION,
//				ItemViewConstants.FREEZE_SLOWDOWN_DEFAULT);
		addAttribute(ItemSchema.FREEZE_DURATION, ItemViewConstants.FREEZE_DURATION_DEFAULT);
		addAttribute(TDObjectSchema.IMAGE_NAME, "tower.gif");
		addAttribute(ItemSchema.ITEM_BEHAVIORS, (Serializable) Arrays.asList(PowerupBehaviors.values()));
		
	}

	@Override
	protected Set<String> populateAdditionalAttributes() {
		return new HashSet<>(); // No additional attributes
	}

}

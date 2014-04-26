package main.java.schema.tdobjects;

import java.awt.geom.Point2D;

import main.java.engine.objects.powerup.TDItem;

public abstract class ItemSchema extends TDObjectSchema{
	public static final String COST = "Cost";
	public static final String BUILDUP_TIME = "Build Up Time (sec)";
	public static final String DAMAGE = "Damage";
	public static final String FLASH_INTERVAL = "Flash Interval";
	public static final String LOCATION = "Location";
	public static final String LIVES_TO_GRANT = "Lives to Grant";
	public static final String RANGE = "Damage range";
	public static final String FREEZE_DURATION = "Freeze Slowdown duration";
	public static final String FREEZE_SLOWDOWN_PROPORTION = "Freeze Slowdown (%)";
	public static final String ITEM_BEHAVIORS = "Item Behaviors";

	public ItemSchema(Class<? extends TDItem> myConcreteType) {
		super(myConcreteType);
		myAttributeSet.add(ItemSchema.BUILDUP_TIME);
		myAttributeSet.add(ItemSchema.COST);
		myAttributeSet.add(ItemSchema.DAMAGE);
		myAttributeSet.add(ItemSchema.FLASH_INTERVAL);
		myAttributeSet.add(ItemSchema.IMAGE_NAME);
		myAttributeSet.add(ItemSchema.NAME);
		myAttributeSet.add(ItemSchema.LOCATION);
		myAttributeSet.add(ItemSchema.LIVES_TO_GRANT);
		myAttributeSet.add(ItemSchema.RANGE);
		myAttributeSet.add(ItemSchema.FREEZE_DURATION);
		myAttributeSet.add(ItemSchema.FREEZE_SLOWDOWN_PROPORTION);
	}

}

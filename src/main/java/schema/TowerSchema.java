package main.java.schema;

import main.java.engine.objects.tower.Tower;

public abstract class TowerSchema extends TDObjectSchema {
    public static final String LOCATION = "Location";
    public static final String HEALTH = "Health";
    public static final String DAMAGE = "Damage";
    public static final String RANGE = "Range";
    public static final String COST = "Cost";
    public static final String TILE_SIZE = "Size";
    public static final String BUILDUP = "BuildUp";
    public static final String TOWER_IMAGE_NAME = "Tower Image Name";
    public static final String BULLET_IMAGE_NAME = "Bullet Image Name";
    
    public static final String TILE_SIZE_SMALL = "Small Tile";
    public static final String TILE_SIZE_LARGE = "Large Tile";
    public static final String RANGE_SMALL = "Small";
    public static final String RANGE_MEDIUM = "Medium";
    public static final String RANGE_LARGE = "Large";
	
    protected TowerSchema(Class<? extends Tower> myConcreteType) {
        super(myConcreteType);

		myAttributeSet.add(TowerSchema.BUILDUP);
		myAttributeSet.add(TowerSchema.TILE_SIZE);
		myAttributeSet.add(TowerSchema.TOWER_IMAGE_NAME);
		myAttributeSet.add(TowerSchema.BULLET_IMAGE_NAME);
		myAttributeSet.add(TowerSchema.COST);
		myAttributeSet.add(TowerSchema.DAMAGE);
		myAttributeSet.add(TowerSchema.HEALTH);
		myAttributeSet.add(TowerSchema.RANGE);
		myAttributeSet.add(TowerSchema.LOCATION);
		myAttributeSet.add(TowerSchema.NAME);
    }
}

package main.java.schema;

import main.java.engine.objects.tower.Tower;

public abstract class TowerSchema extends TDObjectSchema {
	
    public static final String LOCATION = "location";
    public static final String HEALTH = "health";
    public static final String DAMAGE = "damage";
    public static final String RANGE = "range";
    public static final String COST = "cost";
    public static final String BUILDUP = "buildup";
    public static final String TOWER_IMAGE_NAME = "towerImageName";
    public static final String BULLET_IMAGE_NAME = "bulletImageName";
	
    protected TowerSchema(Class<? extends Tower> myConcreteType) {
        super(myConcreteType);
		myAttributeSet.add(TowerSchema.BUILDUP);
		myAttributeSet.add(TowerSchema.COST);
		myAttributeSet.add(TowerSchema.DAMAGE);
		myAttributeSet.add(TowerSchema.HEALTH);
		myAttributeSet.add(TowerSchema.RANGE);
		myAttributeSet.add(TowerSchema.LOCATION);
		myAttributeSet.add(TowerSchema.NAME);
		//this is a damn hack -- Gary
		myAttributeSet.add(MonsterSchema.ENTRANCE_LOCATION);
		myAttributeSet.add(MonsterSchema.EXIT_LOCATION);
		myAttributeSet.add(MonsterSchema.HEALTH);
		myAttributeSet.add(MonsterSchema.REWARD);
		myAttributeSet.add(MonsterSchema.SPEED);
		myAttributeSet.add(MonsterSchema.DAMAGE);
		myAttributeSet.add(MonsterSchema.NAME);
		myAttributeSet.add(MonsterSchema.FLYING_OR_GROUND);
		myAttributeSet.add(MonsterSchema.TILE_SIZE);
		myAttributeSet.add(MonsterSchema.ENEMY_IMAGE_PATH);
		myAttributeSet.add(MonsterSchema.COLLISION_IMAGE_PATH);

    }
}

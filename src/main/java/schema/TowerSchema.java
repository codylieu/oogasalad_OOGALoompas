package main.java.schema;

import main.java.engine.objects.tower.Tower;

public abstract class TowerSchema extends TDObjectSchema {
	
    protected TowerSchema(Class<? extends Tower> myConcreteType) {
        super(myConcreteType);
		myAttributeSet.add(Tower.BUILDUP);
		myAttributeSet.add(Tower.COST);
		myAttributeSet.add(Tower.DAMAGE);
		myAttributeSet.add(Tower.HEALTH);
		myAttributeSet.add(Tower.RANGE);
		myAttributeSet.add(Tower.LOCATION);
		myAttributeSet.add(Tower.NAME);
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

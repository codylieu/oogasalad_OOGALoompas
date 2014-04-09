package main.java.schema;

import main.java.engine.objects.monster.Monster;
import main.java.engine.objects.tower.Tower;

public abstract class MonsterSchema extends TDObjectSchema {
	
    public static final String HEALTH = "health";
	public static final String SPEED = "speed";
	public static final String REWARD = "moneyValue";
	public static final String ENTRANCE_X = "entranceX";
	public static final String ENTRANCE_Y = "entranceY";
	public static final String EXIT_X = "exitX";
	public static final String EXIT_Y = "exitY";
	public static final String ENEMY_IMAGE_PATH = "enemyImagePath";
	public static final String COLLISION_IMAGE_PATH = "collisonPath";
	public static final String FLYING_OR_GROUND = "flyingOrGround";
	public static final String TILE_SIZE = "tileSize";
	
	public static final String FLYING_OR_GROUND_FLYING = "Flying";
	public static final String FLYING_OR_GROUND_GROUND = "Ground";
	
	public static final String TILE_SIZE_SMALL = "Small";
	public static final String TILE_SIZE_MEDIUM = "Medium";
	public static final String TILE_SIZE_LARGE = "Large";
	
	public static final String DAMAGE = "attackDamage";
	public static final int MONSTER_CID = 1;

	protected MonsterSchema(Class<? extends Monster> myConcreteType) {
        super(myConcreteType);
		myAttributeSet.add(MonsterSchema.ENTRANCE_X);
		myAttributeSet.add(MonsterSchema.ENTRANCE_Y);
		myAttributeSet.add(MonsterSchema.EXIT_X);
		myAttributeSet.add(MonsterSchema.EXIT_Y);
		myAttributeSet.add(MonsterSchema.HEALTH);
		myAttributeSet.add(MonsterSchema.REWARD);
		myAttributeSet.add(MonsterSchema.SPEED);
		myAttributeSet.add(Monster.NAME);
    }
    
}

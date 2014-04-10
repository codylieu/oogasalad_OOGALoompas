package main.java.schema;

import main.java.engine.objects.monster.Monster;

public abstract class MonsterSchema extends TDObjectSchema {
    public static final String HEALTH = "health";
	public static final String SPEED = "speed";
	public static final String MONEY_VALUE = "moneyValue";
	public static final String ENTRANCE_LOCATION = "entrance";
	public static final String EXIT_LOCATION = "exit";
	public static final String ENEMY_IMAGE_NAME = "enemyImageName";
	public static final String COLLISION_IMAGE_NAME = "collisonImageName";
	public static final String FLYING_OR_GROUND = "flyingOrGround";
	public static final String TILE_SIZE = "tileSize";
	
	public static final String FLYING = "Flying";
	public static final String GROUND = "Ground";
	
	public static final String TILE_SIZE_SMALL = "Small";
	public static final String TILE_SIZE_MEDIUM = "Medium";
	public static final String TILE_SIZE_LARGE = "Large";
	
	public static final String DAMAGE = "attackDamage";
	public static final int MONSTER_CID = 1;

	protected MonsterSchema(Class<? extends Monster> myConcreteType) {
        super(myConcreteType);
		myAttributeSet.add(MonsterSchema.ENTRANCE_LOCATION);
		myAttributeSet.add(MonsterSchema.EXIT_LOCATION);
		myAttributeSet.add(MonsterSchema.HEALTH);
		myAttributeSet.add(MonsterSchema.MONEY_VALUE);
		myAttributeSet.add(MonsterSchema.SPEED);
		myAttributeSet.add(MonsterSchema.NAME);
    }
    
}
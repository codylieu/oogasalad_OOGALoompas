package main.java.schema;

import main.java.author.view.tabs.enemy.EnemyViewConstants;
import main.java.engine.objects.monster.Monster;

public abstract class MonsterSchema extends TDObjectSchema {
    public static final String HEALTH = "health";
	public static final String SPEED = "speed";
	public static final String REWARD = "moneyValue";
	public static final String ENTRANCE_LOCATION = "entrance";
	public static final String EXIT_LOCATION = "exit";
    public static final String BLOCKED_TILES = "blocked";
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
        myAttributeSet.add(MonsterSchema.BLOCKED_TILES);
		myAttributeSet.add(MonsterSchema.HEALTH);
		myAttributeSet.add(MonsterSchema.REWARD);
		myAttributeSet.add(MonsterSchema.SPEED);
		myAttributeSet.add(MonsterSchema.DAMAGE);
		myAttributeSet.add(MonsterSchema.NAME);
		myAttributeSet.add(MonsterSchema.FLYING_OR_GROUND);
		myAttributeSet.add(MonsterSchema.TILE_SIZE);
		myAttributeSet.add(MonsterSchema.ENEMY_IMAGE_NAME);
		myAttributeSet.add(MonsterSchema.COLLISION_IMAGE_NAME);
    }
}
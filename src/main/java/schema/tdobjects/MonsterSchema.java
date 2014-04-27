package main.java.schema.tdobjects;

import main.java.engine.objects.monster.Monster;


public abstract class MonsterSchema extends TDObjectSchema {
    public static final String HEALTH = "Health";
    public static final String SPEED = "Speed";
    public static final String REWARD = "Bounty Reward";
    public static final String DAMAGE = "Attack Damage";
	public static final String PATHFINDER_MANAGER = "Pathfinder Manager";
    public static final String ENTRANCE_LOCATION = "Entrance Location";
    public static final String EXIT_LOCATION = "Exit";
    public static final String BLOCKED_TILES = "Blocked Tiles";
    public static final String FLYING_OR_GROUND = "Flying or Ground";
    public static final String TILE_SIZE = "Tile Size";

    public static final String FLYING = "Flying";
    public static final String GROUND = "Ground";

    public static final String TILE_SIZE_SMALL = "Small";
    public static final String TILE_SIZE_MEDIUM = "Medium";
    public static final String TILE_SIZE_LARGE = "Large";

    public static final String RESURRECT_MONSTERSPAWNSCHEMA =
            "Monster schema to spawn upon this monster's death.";

    protected MonsterSchema (Class<? extends Monster> myConcreteType) {
        super(myConcreteType);

        myAttributeSet.add(MonsterSchema.ENTRANCE_LOCATION);
        myAttributeSet.add(MonsterSchema.EXIT_LOCATION);
        myAttributeSet.add(MonsterSchema.BLOCKED_TILES);
        myAttributeSet.add(MonsterSchema.HEALTH);
        myAttributeSet.add(MonsterSchema.REWARD);
        myAttributeSet.add(MonsterSchema.SPEED);
        myAttributeSet.add(MonsterSchema.DAMAGE);
        myAttributeSet.add(MonsterSchema.NAME);
        myAttributeSet.add(MonsterSchema.BLOCKED_TILES);
		myAttributeSet.add(MonsterSchema.PATHFINDER_MANAGER);
        myAttributeSet.add(MonsterSchema.TILE_SIZE);
        myAttributeSet.add(MonsterSchema.RESURRECT_MONSTERSPAWNSCHEMA);
    }
}

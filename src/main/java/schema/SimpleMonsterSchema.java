package main.java.schema;

import main.java.author.view.tabs.enemy.EnemyViewConstants;
import main.java.engine.objects.monster.Monster;
import main.java.engine.objects.monster.SimpleMonster;

import java.awt.Dimension;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * This is a settings object for a specific type of Enemy and at a high level is
 * a wrapper for a bunch of key value pairs that the Engine will need to
 * reference to create TDObjects. This class is not a specific instance of a
 * Enemy. Please refer to the Game Engine's TDObjects for the objects related to
 * ones you will see onscreen.
 */
public class SimpleMonsterSchema extends MonsterSchema {
	public static final Class<SimpleMonster> MY_CONCRETE_TYPE = SimpleMonster.class;

	public SimpleMonsterSchema() {
		super(MY_CONCRETE_TYPE);
	}


	/**
	 * @param name name of monster
	 */
	public SimpleMonsterSchema(String name) {
		this();
		
		populateDefaultAttributes(name);
	
	}

	public void populateDefaultAttributes(String name) {
		addAttribute(MonsterSchema.NAME, name);
		addAttribute(MonsterSchema.HEALTH, EnemyViewConstants.HEALTH_DEFAULT);
		addAttribute(MonsterSchema.SPEED, EnemyViewConstants.SPEED_DEFAULT);
		addAttribute(MonsterSchema.DAMAGE, EnemyViewConstants.DAMAGE_DEFAULT);
		addAttribute(MonsterSchema.MONEY_VALUE, EnemyViewConstants.REWARD_DEFAULT);    
		addAttribute(MonsterSchema.FLYING_OR_GROUND, EnemyViewConstants.FLYING_OR_GROUND_DEFAULT);    
		addAttribute(MonsterSchema.TILE_SIZE, EnemyViewConstants.TILE_SIZE_DEFAULT);
		addAttribute(MonsterSchema.ENEMY_IMAGE_NAME, "");
		addAttribute(MonsterSchema.COLLISION_IMAGE_NAME, "");
		
	}

	@Override
	protected Set<String> populateAdditionalAttributes() {
		// empty set, no new attributes
		return new HashSet<String>();
	}

	@Override
	public String defineName() {
		return "SimpleMonster";
	}

}
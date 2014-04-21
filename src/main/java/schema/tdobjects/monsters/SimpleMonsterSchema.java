package main.java.schema.tdobjects.monsters;

import main.java.author.view.tabs.enemy.EnemyViewConstants;
import main.java.engine.objects.monster.SimpleMonster;
import main.java.schema.tdobjects.MonsterSchema;
import main.java.schema.tdobjects.TDObjectSchema;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * This is a settings object for a specific type of Monster.
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
		addAttribute(MonsterSchema.REWARD, EnemyViewConstants.REWARD_DEFAULT);    
		addAttribute(MonsterSchema.FLYING_OR_GROUND, MonsterSchema.GROUND);    
		addAttribute(MonsterSchema.TILE_SIZE, MonsterSchema.TILE_SIZE_SMALL);
		addAttribute(TDObjectSchema.IMAGE_NAME, "monster.png");
		addAttribute(MonsterSchema.COLLISION_IMAGE_NAME, "");
	}

	@Override
	protected Set<String> populateAdditionalAttributes() {
		return new HashSet<String>(); // empty set, no new attributes
	}
}
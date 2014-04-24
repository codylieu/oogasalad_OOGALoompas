package main.java.schema.tdobjects.monsters;

import main.java.author.view.tabs.enemy.EnemyViewDefaults;
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

	@Override
	public void populateDefaultAttributes(String name) {
		addAttribute(MonsterSchema.NAME, name);
		addAttribute(MonsterSchema.HEALTH, EnemyViewDefaults.HEALTH_DEFAULT);
		addAttribute(MonsterSchema.SPEED, EnemyViewDefaults.SPEED_DEFAULT);
		addAttribute(MonsterSchema.DAMAGE, EnemyViewDefaults.DAMAGE_DEFAULT);
		addAttribute(MonsterSchema.REWARD, EnemyViewDefaults.REWARD_DEFAULT);    
		addAttribute(MonsterSchema.FLYING_OR_GROUND, MonsterSchema.GROUND);    
		addAttribute(MonsterSchema.TILE_SIZE, MonsterSchema.TILE_SIZE_SMALL);
		addAttribute(TDObjectSchema.IMAGE_NAME, EnemyViewDefaults.ENEMY_DEFAULT_IMAGE);
		addAttribute(MonsterSchema.COLLISION_IMAGE_NAME, "");
	}

	@Override
	protected Set<String> populateAdditionalAttributes() {
		return new HashSet<String>(); // empty set, no new attributes
	}
}
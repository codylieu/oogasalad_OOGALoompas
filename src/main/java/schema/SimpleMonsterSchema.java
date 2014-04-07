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

	private void populateDefaultAttributes(String name) {
		addAttribute(Monster.NAME, name);
		addAttribute(Monster.HEALTH, EnemyViewConstants.HEALTH_DEFAULT);
		addAttribute(Monster.SPEED, EnemyViewConstants.SPEED_DEFAULT);
		addAttribute(Monster.DAMAGE, EnemyViewConstants.DAMAGE_DEFAULT);
		addAttribute(Monster.REWARD, EnemyViewConstants.REWARD_DEFAULT);           
		
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

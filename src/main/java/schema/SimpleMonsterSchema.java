package main.java.schema;

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

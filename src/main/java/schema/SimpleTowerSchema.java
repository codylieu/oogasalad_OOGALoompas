package main.java.schema;

import main.java.engine.objects.tower.SimpleTower;

import java.awt.Dimension;
import java.util.HashSet;
import java.util.Set;


/**
 * 
 * This is a settings object for a specific type of Tower like a Fire Tower, and
 * at a high level is a wrapper for a bunch of key value pairs that the Engine
 * will need to reference to create TDObjects. This class is not a specific
 * instance of a Tower. Please refer to the Game Engine's TDObjects for the
 * objects related to ones you will see onscreen.
 */
public class SimpleTowerSchema extends TowerSchema {
    private static final Class<SimpleTower> MY_CONCRETE_TYPE = SimpleTower.class;

    public SimpleTowerSchema() {
        super(MY_CONCRETE_TYPE);
    }

	@Override
	protected Set<String> populateAdditionalAttributes() {
		//empty set, no new attributes
		return new HashSet<String>();
	}

	@Override
	public String defineName() {
		return "SimpleTower";
	}
	
}

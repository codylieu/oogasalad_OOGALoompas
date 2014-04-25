package main.java.schema;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


/**
 * the higher level settings defining the type of game and some basic
 * overarching stats
 */
public class GameSchema extends AbstractSchema {
	public static final String LIVES = "lives";
	public static final String MONEY = "money";
	public static final String LEVELDIFFICULTY = "difficulty";
	/**
	 * The value should be of type Boolean
	 */
	public static final String ISSURVIVALMODE = "survivalmodeenabled";

	public GameSchema()	{
		super();
	}
	
	@Override
	public void addAttribute(String attributeName, Serializable attributeValue) {
		myAttributesMap.put(attributeName, attributeValue);
		myAttributeSet.add(ISSURVIVALMODE);
	}
	
	@Override
	protected Set<String> populateAdditionalAttributes() {
		return new HashSet<String>();
	}
}

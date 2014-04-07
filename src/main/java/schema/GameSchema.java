package main.java.schema;

import java.util.Set;

/**
 * the higher level settings defining the type of game and some basic
 * overarching stats
 */
public class GameSchema extends AbstractSchema{

//	private int myStartingLives;
//	private int myStartingGold;
//	private int myLevelDifficulty;
//	private boolean isSurvivalMode;
	
	public static final String LIVES = "lives";
	public static final String GOLD = "gold";
	public static final String LEVELDIFFICULTY = "difficulty";
	public static final String ISSURVIVALMODE = "issurvivalmode";

	public GameSchema()	{
		super();
	}
	
	@Override
	public void addAttribute(String attributeName, Object attributeValue) {
		myAttributesMap.put(attributeName, attributeValue.toString());
		
	}
	
	@Override
	protected Set<String> populateAdditionalAttributes() {
		return null;
	}
	
}

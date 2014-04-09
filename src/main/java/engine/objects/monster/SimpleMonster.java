package main.java.engine.objects.monster;

import java.awt.geom.Point2D;
import java.util.Map;
import main.java.engine.objects.Exit;

public class SimpleMonster extends Monster {

	public static final double DEFAULT_HEALTH = 100;
	public static final double DEFAULT_MOVE_SPEED = 10;
	public static final double DEFAULT_REWARD_AMOUNT = 10;
	public static final String DEFAULT_MONSTER_GFX = "monster";

	public SimpleMonster (Point2D entrance, Exit exit) {
		super(entrance, exit, DEFAULT_HEALTH, DEFAULT_MOVE_SPEED, DEFAULT_REWARD_AMOUNT,
				DEFAULT_MONSTER_GFX);
		// myPathFinder = new StraightLinePath(this);
	}

	/**
	 * Create a new monster from a map of attributes. Should be called by factory.
	 * 
	 * @param attributes key value map of attributes as defined by MonsterSchema
	 */
	public SimpleMonster (Map<String, Object> attributes) {
		this(
				(Point2D) getValueOrDefault(attributes, ENTRANCE_LOCATION, new Point2D.Double(0,0)),
				(Exit) getValueOrDefault(attributes, EXIT_LOCATION, null),
				(double) getValueOrDefault(attributes, HEALTH, DEFAULT_HEALTH),
				(double) getValueOrDefault(attributes, SPEED, DEFAULT_MOVE_SPEED),
				(double) getValueOrDefault(attributes, MONEY_VALUE, DEFAULT_REWARD_AMOUNT),
				(String) getValueOrDefault(attributes, "image", DEFAULT_MONSTER_GFX));
	}

	/**
	 * 
	 * The normal constructor. Should not be called directly by factory.
	 * 
	 * @param entranceX
	 * @param entranceY
	 * @param exitX
	 * @param exitY
	 * @param health
	 * @param speed
	 * @param moneyValue
	 * @param imageName
	 */
	public SimpleMonster (Point2D entrance,
			Exit exit,
			double health,
			double speed,
			double moneyValue,
			String imageName) {

		super(entrance,
				exit,
				health, moneyValue, speed, imageName);

	}
}

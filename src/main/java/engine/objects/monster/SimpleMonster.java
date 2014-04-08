package main.java.engine.objects.monster;

import java.awt.geom.Point2D;
import java.util.Map;
import main.java.schema.SimpleMonsterSchema;
import main.java.schema.MonsterSchema;


public class SimpleMonster extends Monster {

	public static final double DEFAULT_HEALTH = 100;
	public static final double DEFAULT_MOVE_SPEED = 10;
	public static final double DEFAULT_REWARD_AMOUNT = 10;
	public static final String DEFAULT_MONSTER_GFX = "monster";

	public SimpleMonster (Point2D entrance, Point2D exit) {
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
				(double) getValueOrDefault(attributes, ENTRANCE_X, 0),
				(double) getValueOrDefault(attributes, ENTRANCE_Y, 0),
				(double) getValueOrDefault(attributes, EXIT_X, 100),
				(double) getValueOrDefault(attributes, EXIT_Y, 100),
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
	public SimpleMonster (double entranceX,
			double entranceY,
			double exitX,
			double exitY,
			double health,
			double speed,
			double moneyValue,
			String imageName) {

		super(new Point2D.Double(entranceX, entranceY),
				new Point2D.Double(exitX, exitY),
				health, moneyValue, speed, imageName);

	}
}

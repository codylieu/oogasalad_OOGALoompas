package main.java.engine.objects.monster;

import java.awt.geom.Point2D;
import java.util.Map;

import main.java.engine.objects.Exit;
import main.java.schema.MonsterSchema;

public class SimpleMonster extends Monster {

	public static final double DEFAULT_HEALTH = 100;
	public static final double DEFAULT_MOVE_SPEED = 10;
	public static final double DEFAULT_REWARD_AMOUNT = 10;

	/**
	 * Create a new monster from a map of attributes. Should be called by factory.
	 * 
	 * @param attributes key value map of attributes as defined by MonsterSchema
	 */
	public SimpleMonster (Map<String, Object> attributes) {
		this(
				(Point2D) getValueOrDefault(attributes, MonsterSchema.ENTRANCE_LOCATION, new Point2D.Double(0,0)),
				(Exit) getValueOrDefault(attributes, MonsterSchema.EXIT_LOCATION, null),
				(double) getValueOrDefault(attributes, MonsterSchema.HEALTH, DEFAULT_HEALTH),
				(double) getValueOrDefault(attributes, MonsterSchema.SPEED, DEFAULT_MOVE_SPEED),
				(double) getValueOrDefault(attributes, MonsterSchema.MONEY_VALUE, DEFAULT_REWARD_AMOUNT),
				(String) attributes.get(NAME));
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

        super(entrance, exit,
              health, moneyValue, speed, imageName);

    }
}

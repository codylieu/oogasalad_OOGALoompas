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
    public SimpleMonster (Map<String, String> attributes) {
        this(Double.parseDouble(getValueOrDefault(attributes, "entranceX", "0")),
             Double.parseDouble(getValueOrDefault(attributes, "entranceY", "0")),
             Double.parseDouble(getValueOrDefault(attributes, "exitX", "100")),
             Double.parseDouble(getValueOrDefault(attributes, "exitY", "100")),
             Double.parseDouble(getValueOrDefault(attributes, "health", DEFAULT_HEALTH + "")),
             Double.parseDouble(getValueOrDefault(attributes, "speed", DEFAULT_MOVE_SPEED + "")),
             Double.parseDouble(getValueOrDefault(attributes, "moneyValue", DEFAULT_REWARD_AMOUNT +
                                                                            "")),
             getValueOrDefault(attributes, "image", DEFAULT_MONSTER_GFX));
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

    /**
     * Within the attribute map, returns the value of the attributeName or returns the defaultValue
     * otherwise
     * 
     * @param attributes map of attributes from a schema
     * @param attributeName desired attribute
     * @param defaultValue default value of attribute if not in attributes map
     * @return
     */
    private static String getValueOrDefault (Map<String, String> attributes,
                                             String attributeName,
                                             String defaultValue) {
        return attributes.containsKey(attributeName) ? attributes.get(attributeName) : defaultValue;
    }

    /**
     * DEPRECATED, DELETE ME after transition to attributes map complete.
     */
    // TODO: deprecated, delete
    public SimpleMonster (Point2D entrance, Point2D exit, MonsterSchema schema) {
        super(entrance, exit, ((SimpleMonsterSchema) schema).getMyHealth(),
              ((SimpleMonsterSchema) schema).getMyMoveSpeed(), ((SimpleMonsterSchema) schema)
                      .getMyRewardAmount(),
              ((SimpleMonsterSchema) schema).getMyImage());
    }

}

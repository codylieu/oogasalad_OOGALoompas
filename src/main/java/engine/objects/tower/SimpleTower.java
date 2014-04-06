package main.java.engine.objects.tower;

import java.awt.geom.Point2D;
import java.util.Map;
import main.java.engine.objects.Projectile;
import main.java.schema.SimpleTowerSchema;
import main.java.schema.TowerSchema;


public class SimpleTower extends Tower {
    public static final String DEFAULT_GRAPHICS = "SimpleTower";
    public static final double DEFAULT_DAMAGE = 10;
    public static final double DEFAULT_HEALTH = 100;
    public static final double DEFAULT_RANGE = 200;
    public static final double DEFAULT_FIRING_SPEED = 5;
    public static final double DEFAULT_COST = 100;
    public static final double DEFAULT_BUILDUPTIME = 100;

    /**
     * Create a new tower from a map of attributes. Should be called by factory.
     * 
     * @param attributes key value map of attributes as defined by TowerSchema
     */
    public SimpleTower (Map<String, String> attributes) {
        this(
             Double.parseDouble(getValueOrDefault(attributes, "x", "0")),
             Double.parseDouble(getValueOrDefault(attributes, "y", "0")),
             Double.parseDouble(getValueOrDefault(attributes, "health", DEFAULT_HEALTH + "")),
             Double.parseDouble(getValueOrDefault(attributes, "damage", DEFAULT_DAMAGE + "")),
             Double.parseDouble(getValueOrDefault(attributes, "range", DEFAULT_RANGE + "")),
             Double.parseDouble(getValueOrDefault(attributes, "cost", DEFAULT_COST + "")),
             Double.parseDouble(getValueOrDefault(attributes, "buildup", DEFAULT_BUILDUPTIME + "")),
             getValueOrDefault(attributes, "image", DEFAULT_GRAPHICS));
    }

    /**
     * The normal constructor. Should not be called directly by factory.
     * 
     * @param x
     * @param y
     * @param health
     * @param damage
     * @param range
     * @param cost
     * @param buildup buildup time for this tower's construction
     * @param imageName
     */
    public SimpleTower (double x,
                        double y,
                        double health,
                        double damage,
                        double range,
                        double cost,
                        double buildup,
                        String imageName) {

        super(new Point2D.Double(x, y),
              imageName,
              damage,
              range,
              cost,
              buildup);
    }

    /**
     * DEPRECATED CONSTRUCTOR, DELETE ME after attribute map constructor utilized by factory
     */
    // TODO: DELETE THIS CONSTRUCTOR
    public SimpleTower (Point2D location, TowerSchema schema) {
        super(location, ((SimpleTowerSchema) schema).getMyImage(), ((SimpleTowerSchema) schema)
                .getMyDamage(),
              ((SimpleTowerSchema) schema).getMyRange(), ((SimpleTowerSchema) schema).getMyCost());
    }

    /**
     * Fires projected at a target with the tower's damage factor
     */
    @Override
    public void fireProjectile (Point2D target) {
        /* trigonometry from Guardian JGame example */
        double angle = Math.atan2(target.getX() - this.x, target.getY() - this.y);
        new Projectile(this.x, this.y, angle, myDamage);
    }

}

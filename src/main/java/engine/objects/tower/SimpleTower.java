package main.java.engine.objects.tower;

import java.awt.geom.Point2D;
import java.util.Map;

import main.java.engine.objects.Projectile;
import main.java.schema.TowerSchema;


public class SimpleTower extends Tower {


	/**
	 * Create a new tower from a map of attributes. Should be called by factory.
	 * 
	 * @param attributes key value map of attributes as defined by TowerSchema
	 */
	public SimpleTower (Map<String, Object> attributes) {
		this(
				(Point2D) getValueOrDefault(attributes, TowerSchema.LOCATION, new Point2D.Double(0,0)),
				(double) getValueOrDefault(attributes, TowerSchema.HEALTH, DEFAULT_HEALTH),
				(double) getValueOrDefault(attributes, TowerSchema.DAMAGE, DEFAULT_DAMAGE),
				(double) getValueOrDefault(attributes, TowerSchema.RANGE, DEFAULT_RANGE),
				(double) getValueOrDefault(attributes, TowerSchema.COST, DEFAULT_COST),
				(double) getValueOrDefault(attributes, TowerSchema.BUILDUP, DEFAULT_BUILDUPTIME),
				(String) attributes.get(TowerSchema.NAME));
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
	public SimpleTower (Point2D location,
			double health,
			double damage,
			double range,
			double cost,
			double buildup,
			String imageName) {

		super(location,
				imageName,
				damage,
				range,
				cost,
				buildup);
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

package main.java.engine.objects.tower;

import java.awt.geom.Point2D;

import main.java.engine.objects.Projectile;
import main.java.schema.TowerSchema;

public class SimpleTower extends Tower {
	public static final String DEFAULT_GRAPHICS = "SimpleTower";
	public static final double DEFAULT_DAMAGE = 10;
	public static final double DEFAULT_RANGE = 200;
	public static final double DEFAULT_FIRING_SPEED = 5;

    //TODO: DEFAULT SCHEMA
	
	public SimpleTower(Point2D location, TowerSchema schema) {
		super(location, schema);
	}
	
	@Override
	public void fireProjectile(Point2D target) {
		/* trigonometry from Guardian JGame example */
	    double angle = Math.atan2(target.getX() - this.x, target.getY() - this.y);
	    new Projectile(this.x, this.y, angle, myDamage);
	}
}

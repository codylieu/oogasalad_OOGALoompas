package main.java.engine.objects.tower;

import java.awt.geom.Point2D;

import main.java.engine.objects.Projectile;
import main.java.schema.TowerSchema;

public class SimpleTower extends Tower {
	public static final String DEFAULT_GRAPHICS = "SimpleTower";
	public static final double DEFAULT_DAMAGE = 10;
	public static final double DEFAULT_RANGE = 200;
	public static final double DEFAULT_FIRING_SPEED = 5;
	public static final double DEFAULT_COST = 100;


	public SimpleTower(Point2D location) {
		super(location, DEFAULT_GRAPHICS, DEFAULT_DAMAGE, DEFAULT_RANGE, DEFAULT_COST);
	}
	
	public SimpleTower(Point2D location, double damage, double range, String graphics, double cost) {
		super(location, graphics, damage, range, cost);
	}
	
	@Override
	public void fireProjectile(Point2D target) {
		/* trigonometry from Guardian JGame example */
	    double angle = Math.atan2(target.getX() - this.x, target.getY() - this.y);
	    new Projectile(this.x, this.y, angle, myDamage);
	}
}

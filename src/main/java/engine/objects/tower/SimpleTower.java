package main.java.engine.objects.tower;

import java.awt.geom.Point2D;

import main.java.engine.objects.Projectile;

public class SimpleTower extends Tower{
	
	static final String DEFAULT_GRAPHICS = "SimpleTower";
	static final double DEFAULT_DAMAGE = 2;
	static final double DEFAULT_RANGE = 10;

	public SimpleTower(double x, double y) {
		super(x, y, DEFAULT_GRAPHICS, DEFAULT_DAMAGE, DEFAULT_RANGE);
	}
	
	public SimpleTower(double x, double y, double damage, double range, String graphics) {
		super(x, y, graphics, damage, range);
	}
	
	@Override
	public void fireProjectile(Point2D target) {
		/* trigonometry from Guardian JGame example */
	    double angle = Math.atan2(target.getX() - this.x, target.getY() - this.y);
	    new Projectile(this.x, this.y, angle, myDamage);
	}
}

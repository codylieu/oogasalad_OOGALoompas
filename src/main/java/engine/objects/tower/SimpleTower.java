package main.java.engine.objects.tower;

import main.java.engine.objects.Projectile;

public class SimpleTower extends Tower{
	
	static final String DEFAULT_GRAPHICS = "SimpleTower";
	static final double DEFAULT_DAMAGE = 2;
	static final double DEFAULT_RANGE = 10;

	public SimpleTower(double x, double y) {
		super(x, y, DEFAULT_GRAPHICS, DEFAULT_DAMAGE, DEFAULT_RANGE);
	}
	
	@Override
	public void fireProjectile(double targetX, double targetY) {
		/* trigonometry from Guardian JGame example */
	    double angle = Math.atan2(targetX - this.x, targetY - this.y);
	    new Projectile(this.x, this.y, angle, myDamage);
	}
}

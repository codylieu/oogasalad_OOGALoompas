package main.java.engine.objects.tower;

import main.java.engine.objects.Projectile;

public class SimpleTower extends Tower{
	
	static final String GRAPHICS = "SimpleTower";
	static final double DAMAGE = 2;
	static final double RANGE = 10;

	public SimpleTower(double x, double y) {
		super(x, y, GRAPHICS, DAMAGE, RANGE);
	}
	
	@Override
	public void fireProjectile(double targetX, double targetY) {
		/* trigonometry from Guardian JGame example */
	    double angle = Math.atan2(targetX - this.x, targetY - this.y);
	    new Projectile(this.x, this.y, angle, myDamage);
	}
}

package main.java.engine.objects.tower;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.Map;

import main.java.engine.EnvironmentKnowledge;
import main.java.engine.objects.projectile.DamageProjectile;

public class BombTower extends ShootingTower {

	public BombTower(ITower baseTower, Map<String, Serializable> attributes) {
		super(baseTower, attributes);
	}

	@Override
    public void fireProjectile (Point2D target) {
        /* trigonometry from Guardian JGame example */
        double angle =
                Math.atan2(target.getX() - getXCoordinate(), target.getY() - getYCoordinate());
        new DamageProjectile(getXCoordinate(), getYCoordinate(), angle, myDamage, myBulletImage);
        new DamageProjectile(getXCoordinate(), getYCoordinate(), angle*1.5, myDamage, myBulletImage);
        new DamageProjectile(getXCoordinate(), getYCoordinate(), angle*.8, myDamage, myBulletImage);
    }

}

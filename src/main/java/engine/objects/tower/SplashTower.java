package main.java.engine.objects.tower;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.Map;

import main.java.engine.objects.TDObject;
import main.java.engine.objects.monster.Monster;
import main.java.engine.objects.projectile.DamageProjectile;
import main.java.engine.objects.projectile.FreezeProjectile;
import main.java.schema.tdobjects.TowerSchema;

/**
 * 
 * Towers that do not aim at particular monster(s)
 * but just shoot in all direction
 *
 */
public class SplashTower extends ShootingTower {

    public static final double[] SPLASH_X = { 0.0, 0.0, -4.0, 4.0, 2.0, -2.0, 2.0, -2.0};
    public static final double[] SPLASH_Y = { -4.0, 4.0, 0.0, 0.0, 2.0, -2.0, -2.0, 2.0};
	
	public SplashTower(ITower baseTower, Map<String, Serializable> attributes) {
		super(baseTower, attributes);
	}
	
    @Override
    public void fireProjectile (double angle) {
    	for (int i = 0; i < SPLASH_X.length; i++) {
            new DamageProjectile(centerCoordinate().getX(), centerCoordinate().getY(), SPLASH_X[i],
                           SPLASH_Y[i], myDamage, myBulletImage);
        }
    }

}

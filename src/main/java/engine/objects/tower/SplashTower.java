package main.java.engine.objects.tower;

import java.io.Serializable;
import java.util.Map;

import main.java.engine.objects.projectile.Bomb;
import main.java.engine.objects.projectile.DamageProjectile;

/**
 * 
 * Towers that do not aim at particular monster(s)
 * but just shoot in all direction
 *
 */
public class SplashTower extends ShootingTower {
	
	private static final String TOWER_TYPE = "Splansh Tower";
	
	public SplashTower(ITower baseTower, Map<String, Serializable> attributes) {
		super(baseTower, attributes);
	}
	
    @Override
    public void fireProjectile (double angle) {
    	for (int i = 0; i < Bomb.BOMB_SPRAY_X.length; i++) {
            new DamageProjectile(
            		baseTower.centerCoordinate().getX(),
            		baseTower.centerCoordinate().getY(),
            		Bomb.BOMB_SPRAY_X[i], Bomb.BOMB_SPRAY_Y[i], myDamage, myBulletImage);
        }
    }

}

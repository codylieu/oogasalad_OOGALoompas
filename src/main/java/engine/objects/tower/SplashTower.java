package main.java.engine.objects.tower;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.Map;

import main.java.engine.objects.TDObject;
import main.java.engine.objects.monster.Monster;
import main.java.engine.objects.projectile.Bomb;
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
	
	public SplashTower(ITower baseTower, Map<String, Serializable> attributes) {
		super(baseTower, attributes);
	}
	
    @Override
    public void fireProjectile (double angle) {
    	for (int i = 0; i < Bomb.BOMB_SPRAY_X.length; i++) {
            new DamageProjectile(
            		((SimpleTower) baseTower).centerCoordinate().getX(),
            		((SimpleTower) baseTower).centerCoordinate().getY(),
            		Bomb.BOMB_SPRAY_X[i], Bomb.BOMB_SPRAY_Y[i], myDamage, myBulletImage);
        }
    }

}

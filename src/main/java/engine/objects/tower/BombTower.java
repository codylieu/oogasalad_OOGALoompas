package main.java.engine.objects.tower;

import java.io.Serializable;
import java.util.Map;
import main.java.engine.objects.TDObject;
import main.java.engine.objects.projectile.Bomb;
import main.java.schema.tdobjects.TowerSchema;


/**
 * A type of tower that shoots a bomb which explodes into shrapnel upon hitting a target
 * 
 */
public class BombTower extends ShootingTower {

    public static final double DEFAULT_SHRAPNEL_DAMAGE = 10;
    public static final String TOWER_TYPE = "Bomb Tower";

    private String myShrapnelImage;
    private double myShrapnelDamage;

    /**
     * Constructor used by the factory in decorating a final tower.
     * 
     * @param baseTower
     * @param attributes
     */
    public BombTower (ITower baseTower, Map<String, Serializable> attributes) {
        super(baseTower, attributes);
        myShrapnelImage =
                (String) TDObject.getValueOrDefault(attributes, TowerSchema.SHRAPNEL_IMAGE_NAME,
                                                    TowerSchema.BULLET_IMAGE_NAME);
        myShrapnelDamage =
                Double.parseDouble(String.valueOf(TDObject
                        .getValueOrDefault(attributes, TowerSchema.SHRAPNEL_DAMAGE,
                                           DEFAULT_SHRAPNEL_DAMAGE)));
    }

    @Override
    public void fireProjectile (double angle) {
        new Bomb(
                 baseTower.centerCoordinate().getX(),
                 baseTower.centerCoordinate().getY(),
                 angle, myDamage, myShrapnelDamage,
                 myBulletImage, myShrapnelImage);
    }
    
    @Override
    public String getInfo() {
    	String info = super.getInfo() +
    			"\nShrapnelDamage: " + myShrapnelDamage;
    	return info;
    }

}
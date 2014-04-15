package main.java.engine.objects.tower;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.Map;

import main.java.engine.objects.TDObject;
import main.java.engine.objects.projectile.FreezeProjectile;
import main.java.schema.tdobjects.TowerSchema;

public class FreezeTower extends ShootingTower {
	
	public static final double DEFAULT_FREEZE_SLOWDOWN_PROPORTION = 0.5;

	protected double myFreezeSlowdownProportion;
	
	public FreezeTower(ITower baseTower, Map<String, Serializable> attributes) {
		super(baseTower, attributes);
		myFreezeSlowdownProportion = (double) TDObject.getValueOrDefault(attributes, TowerSchema.FREEZE_SLOWDOWN_PROPORTION, DEFAULT_FREEZE_SLOWDOWN_PROPORTION);
	}

	@Override
    public void fireProjectile (Point2D target) {
        /* trigonometry from Guardian JGame example */
        double angle =
                Math.atan2(target.getX() - getXCoordinate(), target.getY() - getYCoordinate());
        new FreezeProjectile(getXCoordinate(), getYCoordinate(), angle, myFreezeSlowdownProportion, myBulletImage);
    }

}

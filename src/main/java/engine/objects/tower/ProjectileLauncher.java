package main.java.engine.objects.tower;

import java.awt.geom.Point2D;
import main.java.engine.objects.Projectile;
import main.java.engine.objects.TDObject;

public class ProjectileLauncher {

    private static final int FIRING_INTERVAL_STEP = 2;
    private static final int MIN_FIRING_INTERVAL = 21;
    
    private boolean isSet = false;
    private double x, y, damage, firingspeed, range;
    private String img;
    private ITower baseTower;
    
    public ProjectileLauncher (ITower baseTower, double damage, double firingSpeed, double range, String img) {
        if(img != null) {
            isSet = true;
        }
        this.baseTower = baseTower;
        this.img = img;
        x = baseTower.getXCoordinate();
        y = baseTower.getYCoordinate();
        this.damage = damage;
        this.firingspeed = firingSpeed;
        this.range = range;
    }

    public void fire (Point2D target) {
        if (target == null) { return; }
        Point2D currCoor = new Point2D.Double(x, y);
        if (inFiringInterval() && target.distance(currCoor) < range) {
            fireProjectile(target);
        }
    }
    
    /**
     * Returns whether or not it is time for the tower to fire, based on its
     * firing speed
     * 
     * @return
     */
    private boolean inFiringInterval () {
        return baseTower.atInterval(MIN_FIRING_INTERVAL - FIRING_INTERVAL_STEP *
                          (int) Math.min(firingspeed, 10));
    }

    /**
     * Fires projected at a target with the tower's damage factor
     */
    public void fireProjectile (Point2D target) {
        /* trigonometry from Guardian JGame example */
        double angle =
                Math.atan2(target.getX() - x, target.getY() -y);
        new Projectile(x, y, angle, damage, img);
    }
    
    /**
     * Can this projectile launcher be fired?
     * @return
     */
    public boolean isSet(){
        return isSet;
    }

}

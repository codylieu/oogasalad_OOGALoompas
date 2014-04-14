package main.java.engine.objects.tower;

import java.awt.geom.Point2D;
import java.util.Map;

import main.java.engine.EnvironmentKnowledge;
import main.java.engine.objects.Projectile;
import main.java.schema.tdobjects.TowerSchema;
import main.java.schema.tdobjects.towers.ShootingTowerSchema;


public class ShootingTower extends Tower {
    public static final double DEFAULT_DAMAGE = 10;
    protected double myDamage;
    protected double myDamageOffset;
    /**
     * Create a new tower from a map of attributes. Should be called by factory.
     * 
     * @param attributes key value map of attributes as defined by TowerSchema
     */
    public ShootingTower (Map<String, Object> attributes) {
        this(
             (Point2D) getValueOrDefault(attributes, TowerSchema.LOCATION, new Point2D.Double(0, 0)),
             (double) getValueOrDefault(attributes, TowerSchema.HEALTH, DEFAULT_HEALTH),
             (double) getValueOrDefault(attributes, ShootingTowerSchema.DAMAGE, DEFAULT_DAMAGE),
             (double) getValueOrDefault(attributes, TowerSchema.RANGE, DEFAULT_RANGE),
             (double) getValueOrDefault(attributes, TowerSchema.COST, DEFAULT_COST),
             (double) getValueOrDefault(attributes, TowerSchema.BUILDUP, DEFAULT_BUILDUPTIME),
             (String) attributes.get(TowerSchema.NAME));
    }

    /**
     * The normal constructor. Should not be called directly by factory.
     * 
     * @param x
     * @param y
     * @param health
     * @param damage
     * @param range
     * @param cost
     * @param buildup buildup time for this tower's construction
     * @param imageName
     */
    public ShootingTower (Point2D location,
                        double health,
                        double damage,
                        double range,
                        double cost,
                        double buildup,
                        String imageName) {

        super(location,
              imageName,
              range,
              cost,
              buildup);
        myDamageOffset = 1;
        myDamage = damage;
    }

    @Override
    public void callTowerActions (EnvironmentKnowledge environ) {
    	//TODO: limit tower firing to after build is complete
        super.callTowerActions(environ);
        // in addition to build up time logic, also fire at the nearest enemy!
        doTowerFiring(environ.getNearestMonsterCoordinate(this.x, this.y));
    }


    /**
     * 
     * Calls the tower's firing method, if any, when within the appropriate firing interval.
     * 
     * @param target
     * 
     */
    private void doTowerFiring (Point2D target) {
        if (target == null) { return; }
        Point2D currCoor = new Point2D.Double(x, y);
        if (inFiringInterval() && target.distance(currCoor) < myRange) {
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
        return myTimingCounter % Math.max(myFiringSpeed, 10) / 10 == 0;
    }

    
    /**
     * Fires projected at a target with the tower's damage factor
     */
    public void fireProjectile (Point2D target) {
        /* trigonometry from Guardian JGame example */
        double angle = Math.atan2(target.getX() - this.x, target.getY() - this.y);
        new Projectile(this.x, this.y, angle, myDamage*myDamageOffset);
    }
    
    /**
     * Sets the the tower's damage offset as a proportion of original damage.
     * @param offsetProportion
     * @return offset proportion
     */
    public double setTowerDamageOffset (double offsetProportion) {
    	myDamageOffset = offsetProportion;
    	return myDamageOffset;
    }
}

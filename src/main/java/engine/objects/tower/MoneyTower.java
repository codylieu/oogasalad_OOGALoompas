package main.java.engine.objects.tower;

import java.awt.geom.Point2D;
import java.util.Map;
import main.java.engine.EnvironmentKnowledge;
import main.java.schema.TowerSchema;


public class MoneyTower extends Tower {

    public static final int DEFAULT_MONEY_GRANTED = 100;
    //TODO: Add to schema
    
    
    /**
     * Create a new tower from a map of attributes. Should be called by factory.
     * 
     * @param attributes key value map of attributes as defined by TowerSchema
     */
    public MoneyTower (Map<String, Object> attributes) {
        this(
             (Point2D) getValueOrDefault(attributes, TowerSchema.LOCATION, new Point2D.Double(0, 0)),
             (double) getValueOrDefault(attributes, TowerSchema.HEALTH, DEFAULT_HEALTH),
             (double) getValueOrDefault(attributes, TowerSchema.DAMAGE, DEFAULT_DAMAGE),
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
    public MoneyTower (Point2D location,
                       double health,
                       double damage,
                       double range,
                       double cost,
                       double buildup,
                       String imageName) {

        super(location,
              imageName,
              damage,
              range,
              cost,
              buildup);
    }


    @Override
    public void callTowerActions (EnvironmentKnowledge environ) {
        super.callTowerActions(environ);
        // in addition to build up time logic, also give player money at the appropriate interval
        grantPlayerMoney(environ);
    }

    private void grantPlayerMoney (EnvironmentKnowledge environ) {
        //TODO: remove magic number timing value
        if (myTimingCounter % 100 == 0) {
            environ.grantPlayerMoney(DEFAULT_MONEY_GRANTED);
        }
    }

}

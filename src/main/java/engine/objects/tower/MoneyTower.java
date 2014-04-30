package main.java.engine.objects.tower;

import java.io.Serializable;
import java.util.Map;

import main.java.engine.EnvironmentKnowledge;
import main.java.engine.objects.TDObject;
import main.java.schema.tdobjects.TowerSchema;


/**
 * Decorates a Tower with the ability to farm money for the current player.
 * 
 * @author Austin
 * 
 */
public class MoneyTower extends TowerBehaviorDecorator {

    /**
     * How much money to grant at one time.
     */
    public static final int DEFAULT_MONEY_GRANTED = 100;
    
    /**
     * At what interval to grant the current player money.
     * Larger number is longer interval
     */
    public static final int DEFAULT_MONEY_GRANT_INTERVAL = 100;
    
    public static final String TOWER_TYPE = "Money Tower";
    
    private double myMoneyGranted;
    private double myMoneyGrantInterval;
    
    /**
     * Create a new money farming tower by decorating a base tower.
     * 
     * MoneyTowers will grant player money if a regeneration time has passed.
     * 
     * @param baseTower the tower to gain new behavior
     * @param moneyGranted how much money to grant
     * @param moneyGrantInterval at what interval should money be granted to player
     * 
     */
    public MoneyTower (ITower baseTower, double moneyGranted, double moneyGrantInterval) {
        super(baseTower);
        myMoneyGranted = moneyGranted;
        myMoneyGrantInterval = moneyGrantInterval;
    }

    
    /**
     * Constructor used by the factory in decorating a final tower.
     * @param baseTower
     * @param attributes
     */
    public MoneyTower (ITower baseTower, Map<String, Serializable> attributes) {
        this(
             baseTower,
             Double.parseDouble(String.valueOf(TDObject.getValueOrDefault(attributes, TowerSchema.MONEY_GRANTED, DEFAULT_MONEY_GRANTED))),
             Double.parseDouble(String.valueOf(TDObject.getValueOrDefault(attributes, TowerSchema.MONEY_GRANT_INTERVAL,
                                                 DEFAULT_MONEY_GRANT_INTERVAL))));
    }
    
    
    /* 
     * In addition to base tower's behaviors, also give player money at the appropriate interval
     * @see main.java.engine.objects.tower.TowerBehaviorDecorator#doDecoratedBehavior(main.java.engine.EnvironmentKnowledge)
     */
    @Override
    void doDecoratedBehavior (EnvironmentKnowledge environ) {
        grantPlayerMoney(environ);
    }

    private void grantPlayerMoney (EnvironmentKnowledge environ) {
        if (baseTower.atInterval((int)myMoneyGrantInterval)) {
            environ.grantPlayerMoney((int)myMoneyGranted);
        }
    }

    @Override
    public String getInfo () {
        return baseTower.getInfo()
               + "\nMoney Granted: " + myMoneyGranted;
    }

}

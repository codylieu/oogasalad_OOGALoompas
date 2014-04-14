package main.java.engine.objects.tower;

import main.java.engine.EnvironmentKnowledge;


/**
 * Decorates a Tower with the ability to farm money for the current player.
 * @author Austin
 *
 */
public class MoneyTower extends TowerBehaviorDecorator {

    public static final int DEFAULT_MONEY_GRANTED = 100;
    public static final int DEFAULT_MONEY_GRANT_INTERVAL = 100;

    /**
     * Create a new money tower by decorating a base tower.
     * 
     * @param baseTower the tower to gain new behavior
     */
    public MoneyTower (ITower baseTower) {
        super(baseTower);
    }

    @Override
    public void callTowerActions (EnvironmentKnowledge environ) {
        super.callTowerActions(environ);
        // in addition to base tower's behaviors, also give player money at the appropriate interval
        grantPlayerMoney(environ);
    }

    private void grantPlayerMoney (EnvironmentKnowledge environ) {
        if (baseTower.atInterval(DEFAULT_MONEY_GRANT_INTERVAL)) {
            environ.grantPlayerMoney(DEFAULT_MONEY_GRANTED);
        }
    }

}

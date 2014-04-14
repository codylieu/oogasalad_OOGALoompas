package main.java.engine.objects.tower;

import java.util.List;
import main.java.engine.EnvironmentKnowledge;


public class PowerUpTower extends TowerBehaviorDecorator {

    private double myRange;

    public PowerUpTower (ITower baseTower, double range) {
        super(baseTower);
        myRange = range;
    }

    public static final double DEFAULT_DAMAGE_POWER_UP_PROPORTION = 1.5;

    @Override
    void doDecoratedBehavior (EnvironmentKnowledge environ) {
        super.callTowerActions(environ);
        List<ITower> nearbyTowers =
                environ.getTowerCoordinatesInRange(getXCoordinate(), getYCoordinate(), myRange);
        for (ITower t : nearbyTowers) {
            // t.setTowerDamageOffset(DEFAULT_DAMAGE_POWER_UP_PROPORTION);
            // add another shooting tower wrapper instead
        }
    }

}

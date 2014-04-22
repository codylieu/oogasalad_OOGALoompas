package main.java.engine.objects.tower;

import java.util.ArrayList;
import java.util.List;
import main.java.engine.EnvironmentKnowledge;
import main.java.engine.objects.detector.TargetDetectorInterface;
import main.java.engine.objects.detector.towerdetector.NearbyTowersDetector;


public class PowerUpTower extends TowerBehaviorDecorator {
	
	private static final String TOWER_TYPE = "Power-up Tower";
    private double myRange;
    private TargetDetectorInterface myDetector = new NearbyTowersDetector();

    public PowerUpTower (ITower baseTower, double range) {
        super(baseTower);
        myRange = range;
    }

    public static final double DEFAULT_DAMAGE_POWER_UP_PROPORTION = 1.5;

    @Override
    void doDecoratedBehavior (EnvironmentKnowledge environ) {
      //  super.callTowerActions(environ);
        List<Object> nearbyTowers = myDetector.findTarget(getXCoordinate(), getYCoordinate(), myRange, environ);
//                environ.getTowerCoordinatesInRange(getXCoordinate(), getYCoordinate(), myRange);
        for (Object obj : nearbyTowers) {
        	ITower t = (ITower) obj;
            // t.setTowerDamageOffset(DEFAULT_DAMAGE_POWER_UP_PROPORTION);
            // add another shooting tower wrapper instead
        }
    }

	@Override
	public List<String> getInfo() {
		List<String> info = new ArrayList<String>();
		info.add(this.getClass().getSimpleName());
		info.addAll(baseTower.getInfo());
		info.add(myRange+"");
		return info;
	}

}

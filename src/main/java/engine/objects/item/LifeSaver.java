package main.java.engine.objects.item;

import main.java.engine.EnvironmentKnowledge;

/**
 * 
 * Grants an additional life to player
 *
 */
public class LifeSaver extends TDItem {
	
	private static final int FLASH_INTERVAL = 5;
	private static final String IMAGE = "row_bomb";
	private static final double COST = 100;
	private static final double BUILDUP_TIME = 0;
	private static final double DAMAGE = 0;
	
	public LifeSaver(double x, double y) {
		super("lifesaver", x, y, IMAGE, COST, BUILDUP_TIME, DAMAGE);
	}
	
	@Override
	public void doAction(EnvironmentKnowledge environmentKnowledge) {
		timeCounter++;
		if (timeCounter >= buildupTime) {
			environmentKnowledge.grantPlayerLife();
		} else flash(timeCounter, FLASH_INTERVAL, IMAGE);
		terminateItem();
	}

}

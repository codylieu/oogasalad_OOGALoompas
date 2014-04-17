package main.java.engine.objects.item;

import main.java.engine.EnvironmentKnowledge;
import main.java.engine.objects.monster.Monster;

/**
 * Removes all current monsters from map
 *
 */
public class Annihilator extends TDItem {

	private static final int FLASH_INTERVAL = 5;
	private static final String IMAGE = "row_bomb";
	private static final double COST = 1000;
	private static final double BUILDUP_TIME = 100;
	private static final double DAMAGE = Double.MAX_VALUE;
	
	public Annihilator(double x, double y) {
		super("annihilator", x, y, IMAGE, COST, BUILDUP_TIME, DAMAGE);
	}

	@Override
	public void doAction(EnvironmentKnowledge environmentKnowledge) {
		timeCounter++;
		if (timeCounter >= buildupTime) {
			for (Monster m : environmentKnowledge.getAllMonsters()) {
				m.takeDamage(DAMAGE);
			}
			terminateItem();
		} else flash(timeCounter, FLASH_INTERVAL, IMAGE);
	}

}

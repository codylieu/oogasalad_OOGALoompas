package main.java.engine.objects.item;

import main.java.engine.EnvironmentKnowledge;
import main.java.engine.objects.monster.Monster;

/**
 * 
 * Instantly freeze all monsters for a short amount of time.
 * 
 * @author Lawrence
 *
 */
public class InstantFreeze extends TDItem {

	private static final int FLASH_INTERVAL = 5;
	private static final String IMAGE = "row_bomb";
	private static final double COST = 100;
	private static final double BUILDUP_Time = 100;
	private static final double DAMAGE = 0;

	public InstantFreeze(double x, double y) {
		super("instance_freeze", x, y, IMAGE, COST, BUILDUP_Time, DAMAGE);
	}

	@Override
	public void doAction(EnvironmentKnowledge environmentKnowledge) {
		timeCounter++;
		if (timeCounter < buildupTime) {
			freezeMonsters(environmentKnowledge);
		} else {
			recoverMonsterSpeed(environmentKnowledge);
			setDead();
		}

	}
	
	private void freezeMonsters(EnvironmentKnowledge environmentKnowledge) {
		for (Monster m : environmentKnowledge.getAllMonsters()) {
			m.setSpeed(0, 0);
		}
	}
	
	private void recoverMonsterSpeed(EnvironmentKnowledge environmentKnowledge) {
		for (Monster m : environmentKnowledge.getAllMonsters()) {
			m.setSpeed(m.getOriginalSpeed());
		}
	}

}

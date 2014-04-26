package main.java.engine.objects.powerup;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.Map;

import main.java.author.view.tabs.item.ItemViewConstants;
import main.java.engine.EnvironmentKnowledge;
import main.java.engine.objects.TDObject;
import main.java.engine.objects.monster.Monster;
import main.java.schema.tdobjects.ItemSchema;
import main.java.schema.tdobjects.powerups.AnnihilatorPowerupSchema;
import main.java.schema.tdobjects.powerups.InstantFreezerPowerupSchema;

/**
 * 
 * Instantly freeze all monsters for a short amount of time.
 * 
 * @author Lawrence
 *
 */
public class InstantFreeze extends AreaBomb {
	private static final String FROZEN_IMAGE_DEFAULT = "ice";
	private static final int FREEZE_SLOWDOWN_PROPORTION_DEFAULT = 0;
	private static final double FREEZE_DURATION_DEFAULT = 50;
	private double myFreezeDuration;
	private double myFreezeSlowDownProportion;
	private double myFreezeDurationCounter;

	public InstantFreeze(IPowerup baseItem, Map<String, Serializable> attributes) {
		super(baseItem, attributes);
		myFreezeDuration = 
				Double.parseDouble(String.valueOf(TDObject.getValueOrDefault(attributes, 
						ItemSchema.FREEZE_DURATION, 
						FREEZE_DURATION_DEFAULT)));
		myFreezeSlowDownProportion = 
				Double.parseDouble(String.valueOf(TDObject.getValueOrDefault(attributes, 

						ItemSchema.FREEZE_SLOWDOWN_PROPORTION, 
						FREEZE_SLOWDOWN_PROPORTION_DEFAULT)));
		myFreezeDurationCounter = 0;
	}

	private void freezeMonsters(EnvironmentKnowledge environmentKnowledge) {
		for (Monster m : environmentKnowledge.getAllMonsters()) {
			if (isInRange(m)) {
				m.setSpeed(m.getOriginalSpeed() * myFreezeSlowDownProportion);
				m.setImage(FROZEN_IMAGE_DEFAULT);
			}
		}
	}

	private void recoverMonsterSpeed(EnvironmentKnowledge environmentKnowledge) {
		for (Monster m : environmentKnowledge.getAllMonsters()) {
			m.setSpeed(m.getOriginalSpeed());
			m.setImage(m.getOriginalImage());
		}
	}

	@Override
	void doDecoratedBehavior(EnvironmentKnowledge environ) {
		if (myFreezeDurationCounter < myFreezeDuration) {
			freezeMonsters(environ);
			myFreezeDurationCounter++;
		} else {
			recoverMonsterSpeed(environ);
			remove();
		}
	}

}

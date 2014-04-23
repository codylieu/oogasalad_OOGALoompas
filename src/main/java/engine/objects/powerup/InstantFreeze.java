package main.java.engine.objects.powerup;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.Map;

import main.java.author.view.tabs.item.ItemViewConstants;
import main.java.engine.EnvironmentKnowledge;
import main.java.engine.objects.monster.Monster;
import main.java.schema.tdobjects.ItemSchema;
import main.java.schema.tdobjects.items.AnnihilatorItemSchema;
import main.java.schema.tdobjects.items.InstantFreezeItemSchema;

/**
 * 
 * Instantly freeze all monsters for a short amount of time.
 * 
 * @author Lawrence
 *
 */
public class InstantFreeze extends TDItem {
	private double freeze_duration;
	
	public InstantFreeze(Point2D location, String image, double cost, double freeze_duration, double damage, int flash_interval) {
		super("instance_freeze", location.getX(), location.getY(), null, cost, 0, damage, flash_interval);
		this.freeze_duration = freeze_duration;
	}
	
	public InstantFreeze(Map<String, Serializable> attributes) {
		this(
				(Point2D) getValueOrDefault(attributes, ItemSchema.LOCATION, new Point2D.Double(0, 0)),
				(String) getValueOrDefault(attributes, InstantFreezeItemSchema.IMAGE_NAME, ItemViewConstants.IMAGE_DEFAULT),
				(Double) getValueOrDefault(attributes, InstantFreezeItemSchema.COST, ItemViewConstants.COST_DEFAULT),
				(Double) getValueOrDefault(attributes, InstantFreezeItemSchema.FREEZE_DURATION, ItemViewConstants.FREEZE_DURATION_DEFAULT),
				(Double) getValueOrDefault(attributes, InstantFreezeItemSchema.DAMAGE, ItemViewConstants.DAMAGE_DEFAULT),
				(Integer) getValueOrDefault(attributes, InstantFreezeItemSchema.FLASH_INTERVAL, ItemViewConstants.FLASH_INTERVAL_DEFAULT)
				);
	}
	
	@Override
	public void move() {
		timeCounter++;
	}

	@Override
	public void doAction(EnvironmentKnowledge environmentKnowledge) {
		if (timeCounter < freeze_duration) {
			freezeMonsters(environmentKnowledge);
		} else {
			recoverMonsterSpeed(environmentKnowledge);
			terminateItem();
		}

	}
	
	private void freezeMonsters(EnvironmentKnowledge environmentKnowledge) {
		for (Monster m : environmentKnowledge.getAllMonsters()) {
			m.setSpeed(0, 0);
			m.setImage("ice");
		}
	}
	
	private void recoverMonsterSpeed(EnvironmentKnowledge environmentKnowledge) {
		for (Monster m : environmentKnowledge.getAllMonsters()) {
			m.setSpeed(m.getOriginalSpeed());
			m.setImage(m.getOriginalImage());
		}
	}

}

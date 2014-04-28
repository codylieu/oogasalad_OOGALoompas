package main.java.engine.objects.powerup;

import java.io.Serializable;
import java.util.Map;

import main.java.author.view.tabs.item.ItemViewConstants;
import main.java.engine.EnvironmentKnowledge;
import main.java.engine.objects.monster.Monster;
import main.java.schema.tdobjects.TowerSchema;
import main.java.schema.tdobjects.items.AnnihilatorItemSchema;

/**
 * Removes all current monsters from map
 *
 */
public class AnnihilatorPowerup extends TDPowerupPowerup {

	public AnnihilatorPowerup(String image, double cost, double buildup_time, double damage, int flash_interval) {
		super("annihilator", 0, 0, image, cost, buildup_time, damage, flash_interval);
	}

	public AnnihilatorPowerup(Map<String, Serializable> attributes) {
		this(
				(String) getValueOrDefault(attributes, AnnihilatorItemSchema.IMAGE_NAME, ItemViewConstants.IMAGE_DEFAULT),
				(Double) getValueOrDefault(attributes, AnnihilatorItemSchema.COST, ItemViewConstants.COST_DEFAULT),
				(Double) getValueOrDefault(attributes, AnnihilatorItemSchema.BUILDUP_TIME, ItemViewConstants.BUILDUP_DEFAULT),
				(Double) getValueOrDefault(attributes, AnnihilatorItemSchema.DAMAGE, ItemViewConstants.DAMAGE_DEFAULT),
				(Integer) getValueOrDefault(attributes, AnnihilatorItemSchema.FLASH_INTERVAL, ItemViewConstants.FLASH_INTERVAL_DEFAULT)
				);
	}

	@Override
	public void doAction(EnvironmentKnowledge environmentKnowledge) {
		timeCounter++;
		if (timeCounter >= buildupTime) {
			for (Monster m : environmentKnowledge.getAllMonsters()) {
				m.takeDamage(damage);
			}
			terminateItem();
		} else flash(timeCounter, flash_interval, image);
	}

}

package main.java.engine.objects.powerup;

import java.io.Serializable;
import java.util.Map;

import main.java.author.view.tabs.item.ItemViewConstants;
import main.java.engine.EnvironmentKnowledge;
import main.java.engine.objects.monster.Monster;
import main.java.schema.tdobjects.TowerSchema;
import main.java.schema.tdobjects.powerups.AnnihilatorPowerupSchema;

/**
 * Removes all current monsters from map
 *
 */
public class Annihilator extends TDItem {

	public Annihilator(IPowerup baseItem) {
		super(baseItem);
	}

	public Annihilator(Map<String, Serializable> attributes) {
		this(
				(String) getValueOrDefault(attributes, AnnihilatorPowerupSchema.IMAGE_NAME, ItemViewConstants.IMAGE_DEFAULT),
				(Double) getValueOrDefault(attributes, AnnihilatorPowerupSchema.COST, ItemViewConstants.COST_DEFAULT),
				(Double) getValueOrDefault(attributes, AnnihilatorPowerupSchema.BUILDUP_TIME, ItemViewConstants.BUILDUP_DEFAULT),
				(Double) getValueOrDefault(attributes, AnnihilatorPowerupSchema.DAMAGE, ItemViewConstants.DAMAGE_DEFAULT),
				(Integer) getValueOrDefault(attributes, AnnihilatorPowerupSchema.FLASH_INTERVAL, ItemViewConstants.FLASH_INTERVAL_DEFAULT)
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

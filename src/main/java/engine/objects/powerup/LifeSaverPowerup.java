package main.java.engine.objects.powerup;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.Map;

import main.java.author.view.tabs.item.ItemViewConstants;
import main.java.engine.EnvironmentKnowledge;
import main.java.schema.tdobjects.ItemSchema;
import main.java.schema.tdobjects.items.AreaBombItemSchema;

/**
 * 
 * Grants an additional life to player
 *
 */

public class LifeSaverPowerup extends TDPowerupPowerup {
	
	public LifeSaverPowerup(Point2D location, String image, double cost, double buildup_time, int flash_interval) {
		super("lifesaver", location.getX(), location.getY(), "row_bomb", cost, buildup_time, 0, flash_interval);
	}
	
	public LifeSaverPowerup(Map<String, Object> attributes) {
		this(
				(Point2D) getValueOrDefault(attributes, ItemSchema.LOCATION, new Point2D.Double(0, 0)),
				(String) getValueOrDefault(attributes, AreaBombItemSchema.IMAGE_NAME, ItemViewConstants.IMAGE_DEFAULT),
				(Double) getValueOrDefault(attributes, AreaBombItemSchema.COST, ItemViewConstants.COST_DEFAULT),
				(Double) getValueOrDefault(attributes, AreaBombItemSchema.BUILDUP_TIME, ItemViewConstants.BUILDUP_DEFAULT),
				(Integer) getValueOrDefault(attributes, AreaBombItemSchema.FLASH_INTERVAL, ItemViewConstants.FLASH_INTERVAL_DEFAULT)
				);
	}

	@Override
	public void doAction(EnvironmentKnowledge environmentKnowledge) {
		timeCounter++;
		if (timeCounter >= buildupTime) {
			environmentKnowledge.grantPlayerLife();
		} else flash(timeCounter, flash_interval, image);
		terminateItem();
	}

}

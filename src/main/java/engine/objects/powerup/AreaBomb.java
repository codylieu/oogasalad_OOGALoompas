package main.java.engine.objects.powerup;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.Map;

import main.java.author.view.tabs.item.ItemViewConstants;
import main.java.engine.objects.monster.Monster;
import main.java.schema.tdobjects.ItemSchema;
import main.java.schema.tdobjects.TowerSchema;
import main.java.schema.tdobjects.powerups.AnnihilatorPowerupSchema;
import main.java.schema.tdobjects.powerups.AreaBombPowerupSchema;

/**
 * 
 * A special bomb that destroys an area of monsters
 * 
 * @author Lawrence
 *
 */
public class AreaBomb extends RowBomb{

	private double range;

	public AreaBomb (Point2D location, double range, String image, double cost, double buildup_time, double damage, int flash_interval) {
		super(location, image, cost, buildup_time, damage, flash_interval);
		this.range = range;
	}

	public AreaBomb (Map<String, Serializable> attributes) {
		this(
				(Point2D) getValueOrDefault(attributes, ItemSchema.LOCATION, new Point2D.Double(0, 0)),
				(Double) getValueOrDefault(attributes, AreaBombPowerupSchema.RANGE, ItemViewConstants.RANGE_DEFAULT),
				(String) getValueOrDefault(attributes, AreaBombPowerupSchema.IMAGE_NAME, ItemViewConstants.IMAGE_DEFAULT),
				(Double) getValueOrDefault(attributes, AreaBombPowerupSchema.COST, ItemViewConstants.COST_DEFAULT),
				(Double) getValueOrDefault(attributes, AreaBombPowerupSchema.BUILDUP_TIME, ItemViewConstants.BUILDUP_DEFAULT),
				(Double) getValueOrDefault(attributes, AreaBombPowerupSchema.DAMAGE, ItemViewConstants.DAMAGE_DEFAULT),
				(Integer) getValueOrDefault(attributes, AreaBombPowerupSchema.FLASH_INTERVAL, ItemViewConstants.FLASH_INTERVAL_DEFAULT)
				);
	}

	@Override
	protected boolean isInRange(Monster m) {
		Point2D monster = m.centerCoordinate();
		Point2D bomb = new Point2D.Double(x, y);
		return (monster.distance(bomb) <= range);
	}

}

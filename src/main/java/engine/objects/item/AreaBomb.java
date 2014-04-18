package main.java.engine.objects.item;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.Map;

import main.java.author.view.tabs.item.ItemViewConstants;
import main.java.engine.objects.monster.Monster;
import main.java.schema.tdobjects.ItemSchema;
import main.java.schema.tdobjects.TowerSchema;
import main.java.schema.tdobjects.items.AreaBombItemSchema;

/**
 * 
 * A special bomb that destroys an area of monsters
 * 
 * @author Lawrence
 *
 */
public class AreaBomb extends RowBomb{

	private double range;

	public AreaBomb (Point2D location, double range) {
		super(location.getX(), location.getY());
		this.range = range;
	}

	public AreaBomb (Map<String, Serializable> attributes) {
		this(
				(Point2D) getValueOrDefault(attributes, ItemSchema.LOCATION, new Point2D.Double(0, 0)),
				(Double) getValueOrDefault(attributes, AreaBombItemSchema.RANGE, ItemViewConstants.RANGE_DEFAULT)
				);
	}

	@Override
	protected boolean isInRange(Monster m) {
		Point2D monster = m.centerCoordinate();
		Point2D bomb = new Point2D.Double(x, y);
		return (monster.distance(bomb) <= range);
	}

}

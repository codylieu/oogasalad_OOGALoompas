package main.java.engine.objects.item;

import java.awt.geom.Point2D;

import main.java.engine.objects.monster.Monster;

/**
 * 
 * A special bomb that destroys an area of monsters
 * 
 * @author Lawrence
 *
 */
public class AreaBomb extends RowBomb{
	
	private static final int FLASH_INTERVAL = 5;
	private static final String IMAGE = "row_bomb";
	private static final double COST = 100;
	private static final double BUILDUP_TIME = 100;
	private static final double DAMAGE = Double.MAX_VALUE;
	
	private double range;

	public AreaBomb (double x, double y, double range) {
		super(x, y);
		this.range = range;
	}

	@Override
	protected boolean isInRange(Monster m) {
		Point2D monster = m.centerCoordinate();
		Point2D bomb = new Point2D.Double(x, y);
		return (monster.distance(bomb) <= range);
	}

}

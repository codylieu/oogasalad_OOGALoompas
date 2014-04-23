package main.java.engine.objects.powerup;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.Map;

import main.java.engine.EnvironmentKnowledge;
import main.java.schema.tdobjects.ItemSchema;
import main.java.schema.tdobjects.TowerSchema;

public class SimplePowerup extends TDItem implements IPowerup{
	
	public static final int FLASH_INTERVAL_DEFAULT = 5;
	public static final double COST_DEFAULT = 20;
	public static final double BUILDUP_DEFAULT = 0;
	public static final double DAMAGE_DEFAULT = 5;
	public static final double FREEZE_DURATION_DEFAULT = 5;
	public static final String IMAGE_DEFAULT = "row_bomb";
	public static final double RANGE_DEFAULT = 10;
	
	protected double myTimingCounter;
	protected double myBuildUpTime;
	protected double myCost;
	protected int myFlashInterval;
	protected String myImage;
	
	public SimplePowerup(String name, Point2D location, String gfxname,
			double cost, double buildupTime, double damage, int flash_interval) {
		super(name, location.getX(), location.getY(), gfxname, cost, buildupTime, damage, flash_interval);
		myBuildUpTime = buildupTime;
		myCost = cost;
		myFlashInterval = flash_interval;
		myImage = gfxname;
	}	
	public SimplePowerup(Map<String, Serializable> attributes) {
		this(
				"tower",
				(Point2D) getValueOrDefault(attributes, ItemSchema.LOCATION, new Point2D.Double(0, 0)),
				(String) attributes.get(ItemSchema.NAME),
				(double) getValueOrDefault(attributes, ItemSchema.COST, COST_DEFAULT),
				(double) getValueOrDefault(attributes, ItemSchema.BUILDUP_TIME, BUILDUP_DEFAULT),
				(double) getValueOrDefault(attributes, ItemSchema.DAMAGE, DAMAGE_DEFAULT),
				(int) getValueOrDefault(attributes, ItemSchema.FLASH_INTERVAL, FLASH_INTERVAL_DEFAULT)
				);
	}

	@Override
	public boolean callItemActions(EnvironmentKnowledge environ) {
		myTimingCounter++;

		if (myTimingCounter <= myBuildUpTime) {
			flash(myTimingCounter, myFlashInterval, myImage);
			return false;   
			// do no further behavior if still building up
		}

		return true;
	}

	@Override
	public void doAction(EnvironmentKnowledge environmentKnowledge) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public double getCost () {
		return myCost;
	}
	
	@Override
	public void remove() {
		setImage(null);
		super.remove();
	}

}

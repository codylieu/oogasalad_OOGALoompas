package main.java.engine.objects.powerup;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.Map;

import main.java.engine.EnvironmentKnowledge;
import main.java.engine.objects.TDObject;
import main.java.schema.tdobjects.ItemSchema;
import main.java.schema.tdobjects.TowerSchema;

public class SimplePowerup extends TDObject implements IPowerup{
	
	private static final int POWERUP_CID = 5;
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
	protected Point2D myLocation;
	
	
	public SimplePowerup(Point2D location, double cost, 
			double buildupTime, int flash_interval, String gfxname) {
		super("item", location.getX(), location.getY(), POWERUP_CID, gfxname);
		myLocation = location;
		myBuildUpTime = buildupTime;
		myCost = cost;
		myFlashInterval = flash_interval;
		myImage = gfxname;
	}	
	public SimplePowerup(Map<String, Serializable> attributes) {
		this(
				(Point2D) getValueOrDefault(attributes, ItemSchema.LOCATION, new Point2D.Double(0, 0)),
				(double) getValueOrDefault(attributes, ItemSchema.COST, COST_DEFAULT),
				(double) getValueOrDefault(attributes, ItemSchema.BUILDUP_TIME, BUILDUP_DEFAULT),
				(int) getValueOrDefault(attributes, ItemSchema.FLASH_INTERVAL, FLASH_INTERVAL_DEFAULT),
				(String) attributes.get(ItemSchema.NAME)
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
	public double getCost () {
		return myCost;
	}
	
	@Override
	public void remove() {
		setImage(null);
		super.remove();
	}
	// TODO: we may need another way to remove the item. e.g. setDead().

	@Override
	public double getXCoordinate() {
		return myLocation.getY();
	}

	@Override
	public double getYCoordinate() {
		return myLocation.getX();
	}

	@Override
	public double getTimeCounter() {
		return myTimingCounter;
	}

}

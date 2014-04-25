package main.java.engine.objects.tower;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import main.java.engine.EnvironmentKnowledge;
import main.java.engine.objects.TDObject;
import main.java.schema.tdobjects.TowerSchema;


/**
 *
 * A Simple Tower that just sits there and does nothing. 
 * It is used to wrap around ("decorate") with tower behaviors such as shooting, money-farming ability, etc.
 * The TDObject factory will use this tower as the base tower to decorate.
 *
 */
public class SimpleTower extends TDObject implements ITower {

	public static final double DEFAULT_HEALTH = 100;
	public static final double DEFAULT_COST = 100;
	public static final double DEFAULT_BUILDUPTIME = 100;
	private static final String TOWER_TYPE = "Simple Tower";

	public static final int TOWER_CID = 0;

	public static final int FLASH_INTERVAL = 5;

	protected double myRange;
	protected double myCost;
	protected double myBuildUpTime;
	/**
	 * Its image name is defined to be exactly the same as its name.
	 */
	protected String myImage; 
	protected double myHealth;


	/**
	 * Name of tower to be upgraded into
	 */
	protected String myUpgradeTower;

	/**
	 * Internal timer shooting at intervals and timing build up phase.
	 */
	protected double myTimingCounter;

	/**
	 * Create a new tower
	 * 
	 * @param location point2d x,y coordinate
	 * @param tower_gfx image to be used
	 * @param damage damage of tower's projectiles
	 * @param range range of tower's aim
	 * @param cost money cost of creating tower
	 * @param buildup time for this tower's construction
	 */
	public SimpleTower (Point2D location, double health, double cost, double buildup, String upgradeTower, String name) {
		super("tower", location.getX(), location.getY(), TOWER_CID, name);
		myHealth = health;
		myImage = name;
		myCost = cost;
		myBuildUpTime = buildup;
		myUpgradeTower = upgradeTower;
	}

	public SimpleTower (Map<String, Serializable> attributes) {
		this(
				(Point2D) getValueOrDefault(attributes, TowerSchema.LOCATION, new Point2D.Double(0, 0)),
				(double) getValueOrDefault(attributes, TowerSchema.HEALTH, DEFAULT_HEALTH),
				(double) getValueOrDefault(attributes, TowerSchema.COST, DEFAULT_COST),
				(double) getValueOrDefault(attributes, TowerSchema.BUILDUP, DEFAULT_BUILDUPTIME),
				(String) getValueOrDefault(attributes, TowerSchema.UPGRADE_PATH, ""),
				(String) attributes.get(TowerSchema.NAME));     
	}
	
	@Override
	public boolean callTowerActions (EnvironmentKnowledge environ) {
		myTimingCounter++;

		if (myTimingCounter <= myBuildUpTime) {
			flash(myTimingCounter, FLASH_INTERVAL, myImage);
			return false;   
			// do no further behavior if still building up
		}

		return true;

	}

	@Override
	public double getCost () {
		return myCost;
	}

	public String toString () {
		return "Name: " + myImage
				+ "Cost: " + myCost + "\n";
	}

	@Override
	public boolean atInterval(int intervalFrequency) {
		return myTimingCounter % intervalFrequency == 0;
	}

	@Override
	public double getXCoordinate () {
		return x;
	}

	@Override
	public double getYCoordinate () {
		return y;
	}

	@Override
	public void remove() {
		setImage(null);
		super.remove();
	}

	@Override
	public String getUpgradeTowerName () {
		return myUpgradeTower;
	}

	@Override
	public String getInfo() {
		String info = "";
		info += "Cost: " + myCost +
				"\nBuildup Time: " + myBuildUpTime +
				"\nHealth: " + myHealth + 
				"\nUpgrade Tower: " + myUpgradeTower;
		return info;
	}

}

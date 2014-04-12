package main.java.engine.objects.tower;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Map;

import main.java.engine.EnvironmentKnowledge;
import main.java.schema.tdobjects.TowerSchema;

public class PowerUpTower extends Tower {
	
    public static final double DEFAULT_DAMAGE_POWER_UP_PROPORTION = 1.5;

	public PowerUpTower(Point2D location,
			double health,
			double damage,
			double range,
			double cost,
			double buildup,
			String imageName) {

		super(location,
				imageName,
				damage,
				range,
				cost,
				buildup);
	}

	public PowerUpTower (Map<String, Object> attributes) {
		this(
				(Point2D) getValueOrDefault(attributes, TowerSchema.LOCATION, new Point2D.Double(0, 0)),
				(double) getValueOrDefault(attributes, TowerSchema.HEALTH, DEFAULT_HEALTH),
				(double) getValueOrDefault(attributes, TowerSchema.DAMAGE, DEFAULT_DAMAGE),
				(double) getValueOrDefault(attributes, TowerSchema.RANGE, DEFAULT_RANGE),
				(double) getValueOrDefault(attributes, TowerSchema.COST, DEFAULT_COST),
				(double) getValueOrDefault(attributes, TowerSchema.BUILDUP, DEFAULT_BUILDUPTIME),
				(String) attributes.get(TowerSchema.NAME));	
	}
	
	public void callTowerActions (EnvironmentKnowledge environ) {
		super.callTowerActions(environ);
		List<Tower> nearbyTowers = environ.getTowerCoordinatesInRange(this.x, this.y, myRange);
		for(Tower t : nearbyTowers) {
			t.setTowerDamageOffset(DEFAULT_DAMAGE_POWER_UP_PROPORTION);
		}
	}
}

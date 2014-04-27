package main.java.engine.objects.monster;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import main.java.engine.PathfinderManager;
import main.java.engine.objects.Exit;
import main.java.schema.MonsterSpawnSchema;
import main.java.schema.tdobjects.MonsterSchema;

/**
 * A simple monster that does nothing but move. 
 *
 */
public class SimpleMonster extends Monster {
	public static final double DEFAULT_HEALTH = 100;
	public static final double DEFAULT_MOVE_SPEED = 1;
	public static final double DEFAULT_REWARD_AMOUNT = 10;

	/**
	 * Create a new monster from a map of attributes. Should be called by factory.
	 * 
	 * @param attributes key value map of attributes as defined by MonsterSchema
	 */
	public SimpleMonster (Map<String, Serializable> attributes) {
		this(
				(Point2D) getValueOrDefault(attributes, MonsterSchema.ENTRANCE_LOCATION, new Point2D.Double(0,0)),
				(Exit) getValueOrDefault(attributes, MonsterSchema.EXIT_LOCATION, null),
                (Set<Integer>) getValueOrDefault(attributes, MonsterSchema.BLOCKED_TILES, null),
				(double) getValueOrDefault(attributes, MonsterSchema.HEALTH, DEFAULT_HEALTH),
				(double) getValueOrDefault(attributes, MonsterSchema.SPEED, DEFAULT_MOVE_SPEED),
				(double) getValueOrDefault(attributes, MonsterSchema.REWARD, DEFAULT_REWARD_AMOUNT),
				(String) attributes.get(MonsterSchema.NAME),
				(PathfinderManager) getValueOrDefault(attributes, MonsterSchema.PATHFINDER_MANAGER, null),
				(MonsterSpawnSchema) getValueOrDefault(attributes, MonsterSchema.RESURRECT_MONSTERSPAWNSCHEMA, null));
	}

    private SimpleMonster (Point2D entrance,
                          Exit exit,
                          Set<Integer> blocked,
                          double health,
                          double speed,
                          double moneyValue,
                          String imageName,
						  PathfinderManager pathfinderManager,
                          MonsterSpawnSchema resurrectionSpawnSchema) {

        super(entrance, exit, blocked, health, speed, moneyValue, imageName,
				pathfinderManager, resurrectionSpawnSchema);

    }
}

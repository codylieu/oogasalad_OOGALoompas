package main.java.engine.factory;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.engine.objects.monster.Monster;
import main.java.engine.objects.tower.Tower;
import main.java.exceptions.engine.MonsterCreationFailureException;
import main.java.exceptions.engine.TowerCreationFailureException;
import main.java.schema.MonsterSchema;
import main.java.schema.TowerSchema;
import jgame.impl.JGEngineInterface;

public class TDObjectFactory {
	private JGEngineInterface engine;
	private Map<String, TowerSchema> towerSchemasMap;
	private Map<String, MonsterSchema> monsterSchemasMap;


	public TDObjectFactory(JGEngineInterface engine) {
		this.engine = engine;
		towerSchemasMap = new HashMap<String, TowerSchema>();
		monsterSchemasMap = new HashMap<String, MonsterSchema>();
	}

	public void loadSchemas(List<TowerSchema> towerSchemas, List<MonsterSchema> monsterSchemas) {
		//TODO: Get rid of repetition in loading schemas
		for (TowerSchema s : towerSchemas) {
			towerSchemasMap.put(s.getMyName(), s);
		}
		for (MonsterSchema s : monsterSchemas) {
			monsterSchemasMap.put(s.getMyName(), s);
		}
//		System.out.println(monsterSchemasMap.keySet().toString());
	}

	public Tower placeTower(Point2D location, String userTowerName) throws TowerCreationFailureException {
		Point2D tileOrigin = findTileOrigin(location);
		try {
			TowerSchema schema = towerSchemasMap.get(userTowerName);
			Object[] towerParameters = {tileOrigin, schema};
        	return (Tower) placeObject("main.java.engine.objects.tower.",schema.getMyConcreteType(), towerParameters);
		} catch (Exception e) {
			throw new TowerCreationFailureException();
		}
	}
	
	public Monster placeMonster(Point2D entrance, Point2D exit, String userMonsterName) throws MonsterCreationFailureException {
		System.out.println(monsterSchemasMap.keySet().toString());
		MonsterSchema schema = monsterSchemasMap.get(userMonsterName);
		Object[] monsterParameters = {entrance, exit, schema};
    	return (Monster) placeObject("main.java.engine.objects.monster.",schema.getMyConcreteType(), monsterParameters);
		/*try {
			MonsterSchema schema = monsterSchemas.get(userMonsterName);
			Object[] monsterParameters = {entrance, exit, schema};
        	return (Monster) placeObject("main.java.engine.objects.monster.",schema.getMyConcreteType(), monsterParameters);
		} catch (Exception e) {
			throw new MonsterCreationFailureException();
		}*/
	}

	private Object placeObject(String objectPackageLocation, String concreteName, Object[] parameters) {
		return Reflection.createInstance(objectPackageLocation+concreteName, parameters);
	}
	
	private Point2D findTileOrigin(Point2D location) {
		int curXTilePos = (int) location.getX()/engine.tileWidth() * engine.tileWidth();
		int curYTilePos = (int) location.getY()/engine.tileHeight() * engine.tileHeight();
		return new Point2D.Double(curXTilePos, curYTilePos);
	}


}

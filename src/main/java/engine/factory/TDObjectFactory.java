package main.java.engine.factory;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.engine.objects.tower.Tower;
import main.java.exceptions.engine.MonsterCreationFailureException;
import main.java.exceptions.engine.TowerCreationFailureException;
import main.java.schema.SimpleTowerSchema;
import main.java.schema.TowerSchema;
import jgame.impl.JGEngineInterface;

public class TDObjectFactory {
	private JGEngineInterface engine;
	private Map<String, SimpleTowerSchema> towerSchemas;

	public TDObjectFactory(JGEngineInterface engine) {
		this.engine = engine;
	}

	public void loadSchemas(List<SimpleTowerSchema> schemas) {
		towerSchemas = new HashMap<String, SimpleTowerSchema>();
		for (SimpleTowerSchema s : schemas) {
			towerSchemas.put(s.getMyName(), s);
		}
	}

	public Tower placeTower(Point2D location, String towerName) throws TowerCreationFailureException {
		Point2D tileOrigin = findTileOrigin(location);
		try {
			TowerSchema schema = towerSchemas.get(towerName);
			/*TODO: Perhaps make an abstract schema class that has abstract method
			 * getConstructorParameters to grab parameters for the constructor of the
			 * object it stores state for
			 */
			Object[] towerParameters = {tileOrigin, schema};
        	return (Tower) placeObject("main.java.engine.objects.tower.",schema.getMyConcreteType(), towerParameters);
		} catch (Exception e) {
			throw new TowerCreationFailureException();
		}
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

package main.java.engine;

import java.util.List;

import main.java.engine.objects.CollisionManager;
import main.java.engine.objects.TDObject;

/**
 * An interface for TD game model
 * @author lawrencelin
 *
 */
public interface IModel {

	/**
	 * Set the collision manager of this model
	 * see <code>CollisionManager</code> for how
	 * the collision manager works
	 * @param collisionManager
	 */
	public void setCollisionManager(CollisionManager collisionManager);
	
	/** Add a player to this game model
	 * @param player
	 */
	public void addPlayer(IPlayer player);

	/**
	 * Get the list of TDObjects associated with
	 * this game model
	 * @return list of TDobjects
	 */
	public List<TDObject> getListOfObjects();
}

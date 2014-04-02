package main.java.engine;

/**
 * A manager interface for any stats
 * associated with the current state of the game
 * @author lawrencelin
 *
 */
public interface IStateManager {

	/**
	 * Get the name of the state in question
	 * @return name of the attribute/state
	 */
	public String getName();
	
	/**
	 * Update the state
	 */
	public void updateStats();
	
	/**
	 * Get the value of the state
	 * that is being kept track of
	 * @return
	 */
	public Object getValue();
	
	/**
	 * Clear the value for this state
	 */
	public void clear();

}

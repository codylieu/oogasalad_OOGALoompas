package main.java.engine.objects.tower;

import main.java.engine.EnvironmentKnowledge;

public interface ITower {

    /**
     * Call every frame.
     * Does all actions/behaviors of this tower.
     * 
     * @param environ environment knowledge
     * @return
     */
    public boolean callTowerActions (EnvironmentKnowledge environ);
    
    /**
     * Checks if this tower's internal counter is at the interval passed in.
     * @param intervalFrequency how frequently this method should return true (1 means every frame)
     * @return boolean
     */
    public boolean atInterval(int intervalFrequency);
    
    /**
     *  Get Tower's X coordinate
     * @return
     */
    public double getXCoordinate();
    
    /**
     * Get Tower's Y coordinate
     * @return
     */
    public double getYCoordinate();
    
    /**
     * Force the immediate removal of this tower from the playing field.
     */
    public void remove();
    
    /**
     * Get this tower's cost
     * 
     * @param target
     */
    public double getCost();
    
}

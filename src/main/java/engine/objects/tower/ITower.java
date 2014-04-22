package main.java.engine.objects.tower;

import java.util.List;

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
    
    /**
     * Get the upgraded replacement tower's name.
     * Returns empty string if no upgraded tower is available.
     * @return String name of upgraded tower to replace the current one
     */
    public String getUpgradeTowerName();
    
    /**
     * Get the information about the tower.
     * 
     * @return a list of string representing the info of the tower
     */
    public List<String> getInfo();
    
}

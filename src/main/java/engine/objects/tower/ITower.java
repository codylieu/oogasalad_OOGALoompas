package main.java.engine.objects.tower;

import main.java.engine.EnvironmentKnowledge;

public interface ITower {

    /**
     * Call every frame.
     * Does action specific to the type of tower.
     * 
     * Towers that shoot will fire a projectile in the direction of the specified x,y target
     * coordinates, if it is within firing interval.
     * 
     * MoneyTowers will grant player money if a regeneration time has passed.
     * 
     * @param environ environment knowledge
     * @return
     */
    public boolean callTowerActions (EnvironmentKnowledge environ);
    
    public boolean atInterval(int interval);
    
    public double getXCoordinate();
    
    public double getYCoordinate();
    
    public void remove();
    
    public double getCost();
    
}

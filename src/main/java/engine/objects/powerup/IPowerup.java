package main.java.engine.objects.powerup;

import main.java.engine.EnvironmentKnowledge;

public interface IPowerup {
	public boolean callItemActions (EnvironmentKnowledge environ);
    
    public void remove();

    public double getCost();
    
    public double getXCoordinate();
    
    public double getYCoordinate();
    
    public double getTimeCounter();

}

package main.java.engine.objects.tower;

import main.java.engine.EnvironmentKnowledge;

public interface ITower {
    public void callTowerActions (EnvironmentKnowledge environ);
    public boolean atInterval(int interval);
    public double getXCoordinate();
    public double getYCoordinate();
}

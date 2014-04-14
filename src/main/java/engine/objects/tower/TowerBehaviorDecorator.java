package main.java.engine.objects.tower;

import main.java.engine.EnvironmentKnowledge;

abstract class TowerBehaviorDecorator implements ITower {
    /**
     * The base tower will have behaviors added to it ("decorations")
     */
    protected ITower baseTower;  
    
    public TowerBehaviorDecorator (ITower baseTower) {
        this.baseTower = baseTower;
    }
    
    @Override
    public boolean atInterval(int intervalFrequency) {
        return baseTower.atInterval(intervalFrequency);
    }
    
    @Override
    public double getXCoordinate(){
        return baseTower.getXCoordinate();
    }
    
    @Override
    public double getYCoordinate(){
        return baseTower.getYCoordinate();
    }
    
    @Override
    public void doFrame(EnvironmentKnowledge environ) {
        baseTower.doFrame(environ);
    }
    
    @Override
    public double getCost(){
        return baseTower.getCost();
    }
    
    @Override
    public void remove(){
        baseTower.remove();
    }
    
    @Override
    public boolean callTowerActions (EnvironmentKnowledge environ) {
        if(baseTower.callTowerActions(environ)){
            // in addition to base tower's behavior, also do additional behavior
            doDecoratedBehavior(environ);
        }
        return true;
    }

    abstract void doDecoratedBehavior (EnvironmentKnowledge environ);
}


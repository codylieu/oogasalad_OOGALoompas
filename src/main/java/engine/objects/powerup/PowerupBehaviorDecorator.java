package main.java.engine.objects.powerup;

import main.java.engine.EnvironmentKnowledge;

abstract class PowerupBehaviorDecorator implements IPowerup {
	protected IPowerup baseItem;
	
	public PowerupBehaviorDecorator (IPowerup baseItem) {
		this.baseItem = baseItem;
	}
	
    public void remove () {
    	baseItem.remove();
    }
    
    public double getCost () {
        return baseItem.getCost();
    }
    
    public boolean callItemActions (EnvironmentKnowledge environ) {
        if (baseItem.callItemActions(environ)) {
            // in addition to base tower's behavior, also do additional behavior
            doDecoratedBehavior(environ);
            return true;
        }
        return false;
    }
    
    @Override
    public double getXCoordinate () {
        return baseItem.getXCoordinate();
    }

    @Override
    public double getYCoordinate () {
        return baseItem.getYCoordinate();
    }
    
    @Override
    public double getTimeCounter() {
    	return baseItem.getTimeCounter();
    }
    
    /**
     * Do the additional behavior granted by this behavior decoration.
     * @param environ
     */
    abstract void doDecoratedBehavior (EnvironmentKnowledge environ);
    
}

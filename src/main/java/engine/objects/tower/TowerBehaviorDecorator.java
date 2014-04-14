package main.java.engine.objects.tower;

import main.java.engine.EnvironmentKnowledge;


/**
 * Abstract decorator class to "wrap" layers of behaviors/functionality around towers.
 * Allows mix and match of tower behaviors, e.g. shooting and money farming.
 * @author Austin
 *
 */
abstract class TowerBehaviorDecorator implements ITower {
    /**
     * The base tower that will have behaviors added to it ("decorations")
     */
    protected ITower baseTower;
    protected ProjectileLauncher launcher;

    public TowerBehaviorDecorator (ITower baseTower, ProjectileLauncher launcher) {
        this.baseTower = baseTower;
        this.launcher = launcher;
    }

    @Override
    public boolean atInterval (int intervalFrequency) {
        return baseTower.atInterval(intervalFrequency);
    }

    @Override
    public double getXCoordinate () {
        return baseTower.getXCoordinate();
    }

    @Override
    public double getYCoordinate () {
        return baseTower.getYCoordinate();
    }

    @Override
    public double getCost () {
        return baseTower.getCost();
    }

    @Override
    public void remove () {
        baseTower.remove();
    }

    @Override
    public boolean callTowerActions (EnvironmentKnowledge environ, ProjectileLauncher launcher) {
        if (baseTower.callTowerActions(environ, this.launcher)) {
            // in addition to base tower's behavior, also do additional behavior
            if (launcher.isSet()){
                launcher.fire(environ.getNearestMonsterCoordinate(getXCoordinate(), getYCoordinate()));
            } else if(this.launcher.isSet()) {
                this.launcher.fire(environ.getNearestMonsterCoordinate(getXCoordinate(), getYCoordinate()));
            }
            doDecoratedBehavior(environ);
            return true;
        }
        return false;
    }

    @Override
    public ProjectileLauncher getLauncher() {
        return launcher;
    }
    
    /**
     * Do the additional behavior granted by this behavior decoration.
     * @param environ
     */
    abstract void doDecoratedBehavior (EnvironmentKnowledge environ);
}

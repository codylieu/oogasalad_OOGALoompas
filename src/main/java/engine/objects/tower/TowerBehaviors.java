package main.java.engine.objects.tower;

import java.io.Serializable;


public enum TowerBehaviors implements Serializable {
    SHOOTING(ShootingTower.class),
    MONEY_FARMING(MoneyTower.class),
    BOMBING(BombTower.class),
    FREEZING(FreezeTower.class),
    SPLASHING(SplashTower.class);

    private Class<? extends ITower> concreteClass;

    private TowerBehaviors (Class<? extends ITower> concreteClass) {
        this.concreteClass = concreteClass;
    }

    public Class<? extends ITower> getConcreteClass () {
        return concreteClass;
    }

}

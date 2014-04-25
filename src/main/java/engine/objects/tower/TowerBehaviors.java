package main.java.engine.objects.tower;


public enum TowerBehaviors {
    SHOOTING(ShootingTower.class),
    MONEY_FARMING(MoneyTower.class),
    BOMBING(BombTower.class),
    FREEZING(FreezeTower.class),
    SPLASHING(SplashTower.class);

    private String concreteClass;

    private TowerBehaviors (Class<? extends ITower> concreteClass) {
        this.concreteClass = concreteClass.getName();
    }

    public String getConcreteClass () {
        return concreteClass;
    }

}

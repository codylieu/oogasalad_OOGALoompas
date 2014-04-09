package main.java.schema;

import main.java.engine.objects.monster.Monster;
import main.java.engine.objects.tower.Tower;

public abstract class MonsterSchema extends TDObjectSchema {
    protected MonsterSchema(Class<? extends Monster> myConcreteType) {
        super(myConcreteType);
		myAttributeSet.add(Monster.ENTRANCE_LOCATION);
		myAttributeSet.add(Monster.EXIT_LOCATION);
		myAttributeSet.add(Monster.HEALTH);
		myAttributeSet.add(Monster.MONEY_VALUE);
		myAttributeSet.add(Monster.SPEED);
		myAttributeSet.add(Monster.NAME);
    }
    
}

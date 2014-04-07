package main.java.schema;

import main.java.engine.objects.monster.Monster;
import main.java.engine.objects.tower.Tower;

public abstract class MonsterSchema extends TDObjectSchema {
    protected MonsterSchema(Class<? extends Monster> myConcreteType) {
        super(myConcreteType);
		myAttributeSet.add(Monster.ENTRANCE_X);
		myAttributeSet.add(Monster.ENTRANCE_Y);
		myAttributeSet.add(Monster.EXIT_X);
		myAttributeSet.add(Monster.EXIT_Y);
		myAttributeSet.add(Monster.HEALTH);
		myAttributeSet.add(Monster.REWARD);
		myAttributeSet.add(Monster.SPEED);
		myAttributeSet.add(Monster.NAME);
    }
    
}

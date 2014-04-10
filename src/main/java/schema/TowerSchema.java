package main.java.schema;

import main.java.engine.objects.tower.Tower;

public abstract class TowerSchema extends TDObjectSchema {
	
    protected TowerSchema(Class<? extends Tower> myConcreteType) {
        super(myConcreteType);
		myAttributeSet.add(Tower.BUILDUP);
		myAttributeSet.add(Tower.COST);
		myAttributeSet.add(Tower.DAMAGE);
		myAttributeSet.add(Tower.HEALTH);
		myAttributeSet.add(Tower.RANGE);
		myAttributeSet.add(Tower.LOCATION);
		myAttributeSet.add(TowerSchema.NAME);
    }
}

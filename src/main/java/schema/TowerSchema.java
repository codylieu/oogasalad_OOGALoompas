package main.java.schema;

import main.java.engine.objects.tower.Tower;

public abstract class TowerSchema extends TDObjectSchema {
    protected TowerSchema(Class<? extends Tower> myConcreteType) {
        super(myConcreteType);
    }
}

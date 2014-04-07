package main.java.schema;

import main.java.engine.objects.monster.Monster;

public abstract class MonsterSchema extends TDObjectSchema {
    protected MonsterSchema(Class<? extends Monster> myConcreteType) {
        super(myConcreteType);
    }
}

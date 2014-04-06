package main.java.schema;

import main.java.engine.objects.TDObject;

public abstract class TDObjectSchema {
    private String myName;
    private Class<? extends TDObject> myConcreteType;

    protected TDObjectSchema(Class<? extends TDObject> myConcreteType) {
        this.myConcreteType = myConcreteType;
    }

    public Class<? extends TDObject> getMyConcreteType() {
        return myConcreteType;
    }

    public void setMyName(String name) {
        myName = name;
    }

    public String getMyName() {
        return myName;
    }
}

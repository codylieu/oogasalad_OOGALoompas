package main.java.schema;

public abstract class TDObjectSchema {
    private String myName;
    private Class myConcreteType;

    protected TDObjectSchema(Class myConcreteType) {
        this.myConcreteType = myConcreteType;
    }

    public Class getMyConcreteType() {
        return myConcreteType;
    }

    public void setMyName(String name) {
        myName = name;
    }

    public String getMyName() {
        return myName;
    }
}

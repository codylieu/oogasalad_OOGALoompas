package main.java.schema.tdobjects;

import java.io.Serializable;
import main.java.engine.objects.TDObject;
import main.java.schema.AbstractSchema;
import main.java.schema.HasDefaultValues;

public abstract class TDObjectSchema extends AbstractSchema implements Serializable, HasDefaultValues {
    public static String NAME = "name";
	public static String IMAGE_NAME = "imageName";

	private Class<? extends TDObject> myConcreteType;

	protected TDObjectSchema(Class<? extends TDObject> concreteType) {
		super();
		myConcreteType = concreteType;
		myAttributeSet.add(IMAGE_NAME);
	}

	public Class<? extends TDObject> getMyConcreteType() {
		return myConcreteType;
	}
}

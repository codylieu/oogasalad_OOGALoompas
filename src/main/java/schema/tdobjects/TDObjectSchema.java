package main.java.schema.tdobjects;

import java.io.Serializable;
import main.java.engine.objects.TDObject;
import main.java.schema.AbstractSchema;
import main.java.schema.HasDefaultValues;

public abstract class TDObjectSchema extends AbstractSchema implements Serializable, HasDefaultValues {
    public static String NAME = "name";
	public static String IMAGE_NAME = "imageName";

	private String myConcreteType;

	protected TDObjectSchema(Class<? extends TDObject> concreteType) {
		super();
		myConcreteType = concreteType.getName();
		myAttributeSet.add(IMAGE_NAME);
	}

	public String getMyConcreteType() {
		return myConcreteType;
	}
}

package main.java.schema;

import java.io.Serializable;

import main.java.engine.objects.TDObject;
import main.java.exceptions.engine.InvalidParameterForConcreteTypeException;

public abstract class TDObjectSchema extends AbstractSchema	implements Serializable, HasDefaultValues {
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

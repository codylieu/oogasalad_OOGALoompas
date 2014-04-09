package main.java.schema;

import java.io.Serializable;

import main.java.engine.objects.TDObject;
import main.java.exceptions.engine.InvalidParameterForConcreteTypeException;

public abstract class TDObjectSchema extends AbstractSchema	implements Serializable {
	
	private Class<? extends TDObject> myConcreteType;

	protected TDObjectSchema(Class<? extends TDObject> concreteType) {
		super();
		myConcreteType = concreteType;
	}

	public Class<? extends TDObject> getMyConcreteType() {
		return myConcreteType;
	}

	/**
	 * Add a new attribute and its value to the internal attributes map. Ensure
	 * attribute has toString method.
	 * 
	 * @param attributeName
	 * @param attributeValue
	 * @throws InvalidParameterForConcreteTypeException 
	 */
	@Override
	public void addAttribute(String attributeName, Serializable attributeValue){
		if(myAttributeSet.contains(attributeName)) {
			myAttributesMap.put(attributeName, attributeValue);
		} 
		else {
			try {
				throw new InvalidParameterForConcreteTypeException();
			} catch (InvalidParameterForConcreteTypeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
//		myAttributesMap.put(TDObject.NAME, defineName()); i don't think i need this -- jordan
	}
	
	/**
	 * Give me the name of the object to be created.
	 * @return Name of the object
	 */
	public abstract String defineName();
	
}

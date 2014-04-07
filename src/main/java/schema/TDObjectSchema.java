package main.java.schema;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import main.java.engine.objects.TDObject;

public abstract class TDObjectSchema {
	private Class<? extends TDObject> myConcreteType;
	private Map<String, String> myAttributesMap;
	protected Set<String> myAttributeSet;

	protected TDObjectSchema(Class<? extends TDObject> concreteType) {
		myConcreteType = concreteType;
		myAttributesMap = new HashMap<String, String>();
		myAttributeSet = new HashSet<String>();
		myAttributeSet.addAll(populateAdditionalAttributes());
	}

	/**
	 * Return a set of strings representing all attributes for this specific object
	 * subclass.
	 */
	protected abstract Set<String> populateAdditionalAttributes();

	public Class<? extends TDObject> getMyConcreteType() {
		return myConcreteType;
	}

	/**
	 * Add a new attribute and its value to the internal attributes map. Ensure
	 * attribute has toString method.
	 * 
	 * @param attributeName
	 * @param attributeValue
	 */
	public void addAttribute(String attributeName, Object attributeValue) {
		myAttributesMap.put(attributeName, attributeValue.toString());
		myAttributesMap.put(TDObject.NAME, defineName());
	}

	/**
	 * Get the internal map of attributes
	 * 
	 * @return unmodifiable map
	 */
	public Map<String, String> getAttributesMap() {
		return Collections.unmodifiableMap(myAttributesMap);
	}
	
	/**
	 * Give me the name of the object to be created.
	 * @return Name of the object
	 */
	public abstract String defineName();
	
}

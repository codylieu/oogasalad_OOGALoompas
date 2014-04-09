package main.java.schema;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import main.java.exceptions.engine.InvalidParameterForConcreteTypeException;

public abstract class AbstractSchema {
	
	protected Map<String, Serializable> myAttributesMap;
	protected Set<String> myAttributeSet;
	//TODO: Ensure that types of values of myAttributesMap match. Perhaps make myAttributesSet into a map that maps name of attribute with type?
	
	protected AbstractSchema()	{
		//myAttributesMap = (Map<String, ? extends Serializable>) new HashMap<String, Object>();
		myAttributesMap = new HashMap<String, Serializable>();
		myAttributeSet = new HashSet<String>();
		myAttributeSet.addAll(populateAdditionalAttributes());
	}
	
	/**
	 * Return a set of strings representing all attributes for this specific object
	 * subclass.
	 */
	protected abstract Set<String> populateAdditionalAttributes();
	
	/**
	 * Add a new attribute and its value to the internal attributes map. Ensure
	 * attribute has toString method.
	 * 
	 * @param attributeName
	 * @param attributeValue
	 * @throws InvalidParameterForConcreteTypeException 
	 */
	public abstract void addAttribute(String attributeName, Serializable attributeValue) throws InvalidParameterForConcreteTypeException;
	

	/**
	 * Get the internal map of attributes
	 * 
	 * @return unmodifiable map
	 */
	public Map<String, Serializable> getAttributesMap() {
//		return Collections.unmodifiableMap(myAttributesMap); TODO: need to add x and y when placing towers, how to fix?
        return new HashMap<>(myAttributesMap);
	}
	
	
}

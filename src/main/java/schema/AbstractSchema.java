package main.java.schema;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import main.java.exceptions.engine.InvalidParameterForConcreteTypeException;

public abstract class AbstractSchema {
	protected Map<String, Object> myAttributesMap;
	protected Set<String> myAttributeSet;
	// TODO: Ensure that types of values of myAttributesMap match.
	// Perhaps make myAttributesSet into a map that maps name of attribute with type?
	
	protected AbstractSchema()	{
		myAttributesMap = new HashMap<>();
		myAttributeSet = new HashSet<>();
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
	public void addAttribute(String attributeName, Object attributeValue) {
        if (myAttributeSet.contains(attributeName)) {
            myAttributesMap.put(attributeName, attributeValue);
        } else {
            try {
                throw new InvalidParameterForConcreteTypeException(); // TODO: actually throw instead of catching
            } catch (InvalidParameterForConcreteTypeException e) {
                e.printStackTrace();
            }
        }
    }

	/**
	 * Get the internal map of attributes.
	 * 
	 * @return copy of the attributes map
	 */
	public Map<String, Object> getAttributesMap() {
        return new HashMap<>(myAttributesMap);
	}
}

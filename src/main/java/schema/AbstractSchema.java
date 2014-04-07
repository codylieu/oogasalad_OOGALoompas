package main.java.schema;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class AbstractSchema {
	
	protected Map<String, String> myAttributesMap;
	protected Set<String> myAttributeSet;
	
	protected AbstractSchema()	{
		myAttributesMap = new HashMap<String, String>();
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
	 */
	public abstract void addAttribute(String attributeName, Object attributeValue);
	

	/**
	 * Get the internal map of attributes
	 * 
	 * @return unmodifiable map
	 */
	public Map<String, String> getAttributesMap() {
		return Collections.unmodifiableMap(myAttributesMap);
	}
	
	
}

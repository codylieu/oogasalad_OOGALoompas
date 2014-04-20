package main.java.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import main.java.exceptions.engine.InvalidParameterForConcreteTypeException;

public abstract class AbstractObjectTest {
	protected Map<String, Serializable> myAttributesMap;
	protected Set<String> myAttributeSet;
	
	public AbstractObjectTest()	{
		myAttributesMap = new HashMap<>();
		myAttributeSet = new HashSet<>();
		myAttributeSet.addAll(populateAdditionalAttributes());
	}
	
	protected abstract Set<String> populateAdditionalAttributes();
	
//	public TestObject(String n, int xCoor, int yCoor)	{
//		x = xCoor;
//		y = yCoor;
//		myName = n;
//		myExtended = new ExtendedTestObject("wee");
//		
//	}
	
	public void addAttribute(String attributeName, Serializable attributeValue){
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
	
	public Map<String, Serializable> getAttributesMap() {
        return new HashMap<>(myAttributesMap);
	}}

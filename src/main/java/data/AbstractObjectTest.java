package main.java.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
	
	public Map<String, Serializable> getAttributesMap() {
        return new HashMap<>(myAttributesMap);
	}}

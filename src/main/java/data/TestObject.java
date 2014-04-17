package main.java.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.io.Serializable;

public class TestObject extends AbstractObjectTest {
	public static final String DAMAGE = "Damage";
	public static final String NAME = "name";
	
	public TestObject(){
		myAttributeSet.add(DAMAGE);
		myAttributeSet.add(NAME);
	}
	
	public void populateDefaultAttributes(String Name){
		addAttribute(TestObject.DAMAGE, 10);
		addAttribute()
	}
	
	@Override
	protected Set<String> populateAdditionalAttributes() {
		// TODO Auto-generated method stub
		return new HashSet<>();
	}
}

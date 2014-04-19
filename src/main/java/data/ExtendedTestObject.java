package main.java.data;

import java.util.Set;

public class ExtendedTestObject extends AbstractObjectTest{

	private String name;
	
	public ExtendedTestObject(String n) {
		super();
		// TODO Auto-generated constructor stub
		name = n;
	}

	@Override
	protected Set<String> populateAdditionalAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

}

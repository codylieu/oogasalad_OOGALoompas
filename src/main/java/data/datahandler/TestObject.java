package main.java.data.datahandler;

public class TestObject {
	public int x;
	public int y;
	public String myName;
	public ExtendedTestObject myExtended;
	
	public TestObject()	{}
	
	public TestObject(String n, int xCoor, int yCoor)	{
		x = xCoor;
		y = yCoor;
		myName = n;
		myExtended = new ExtendedTestObject("wee");
	}
	
	public String toString()	{
		return "name: " + myName + " x: " + x + " y: " + y;
	}
}

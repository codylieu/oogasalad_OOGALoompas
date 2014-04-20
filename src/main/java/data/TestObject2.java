package main.java.data;


import jgame.JGObject;

public class TestObject2 extends JGObject{

	public String name;
	
	public TestObject2(String n) {
		super(n, true, 0, 0, 0,"blue_bullet.png");
		name = n;
	}

}

package main.java.exceptions.data;

public class TestExceptions extends Exception {
	public TestExceptions() {
		super("This is a test for making exceptions");
	}

	public static void main(String[] args){
		try {
			throw new TestExceptions();
		}
		catch( Exception a ) {
			System.out.println("Working Status: " + a.getMessage() );
		}
	}
}

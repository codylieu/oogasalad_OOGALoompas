package main.java.exceptions.engine;

public class InvalidParameterForConcreteTypeException extends Exception {
	public InvalidParameterForConcreteTypeException(){
		super("Parameter is not supported for this TDObject type");
	}
}

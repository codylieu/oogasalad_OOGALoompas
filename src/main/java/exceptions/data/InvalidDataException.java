package main.java.exceptions.data;

import java.io.IOException;

/**
 * Generalized Data Exception that will be thrown when a field value is missing
 * for an object
 * @author In-Young Jo
 *
 */
public class InvalidDataException extends IOException	{
	public InvalidDataException(String field, Object obj)	{
		super("Invalid or missing " + field + " for " + obj.getClass());
	}
}

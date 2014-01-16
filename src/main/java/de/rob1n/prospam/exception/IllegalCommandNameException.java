package de.rob1n.prospam.exception;

public class IllegalCommandNameException extends Exception
{
	private static final long serialVersionUID = -6049295019709128933L;
	
	public IllegalCommandNameException()
	{
		super("Unknown Command");
	}
}

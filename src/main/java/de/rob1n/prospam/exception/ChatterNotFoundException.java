package de.rob1n.prospam.exception;

public class ChatterNotFoundException extends Exception
{
	private static final long serialVersionUID = 5584336563420774195L;

	public ChatterNotFoundException()
	{
		super("Chatter not found");
	}
}

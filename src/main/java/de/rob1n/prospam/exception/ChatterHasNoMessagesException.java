package de.rob1n.prospam.exception;

public class ChatterHasNoMessagesException extends Exception
{
	private static final long serialVersionUID = -739216888889625843L;

	public ChatterHasNoMessagesException()
	{
		super("Player has no Messages");
	}
}

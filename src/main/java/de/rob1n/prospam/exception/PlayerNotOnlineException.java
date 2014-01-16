package de.rob1n.prospam.exception;

public class PlayerNotOnlineException extends Exception
{
	private static final long serialVersionUID = -1743193396304231557L;

	public PlayerNotOnlineException()
	{
		super("Player not online");
	}
}

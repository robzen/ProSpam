package de.rob1n.prospam.chatter;

import java.util.Date;

public class ChatMessage
{
	public final long timeSubmitted;
	public final String message;
	
	public ChatMessage(final String message)
	{
		this.timeSubmitted = (new Date()).getTime();
		this.message = message;
	}
}
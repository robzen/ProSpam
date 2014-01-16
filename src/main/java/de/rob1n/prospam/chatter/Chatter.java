package de.rob1n.prospam.chatter;

import de.rob1n.prospam.exception.ChatterHasNoMessagesException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Chatter
{
	private final String name;
	private List<ChatMessage> submittedMessages = new ArrayList<ChatMessage>();
	private long spamStarted = 0;
	
	private static final int MAX_MESSAGE_HISTORY = 40;
	
	private int spamCountCaps = 0;
	private int spamCountChars = 0;
	private int spamCountFlood = 0;
	private int spamCountSimilar = 0;
	private int spamCountUrls = 0;
	private int spamCountBlacklist = 0;
	
	public Chatter(String name)
	{
		this.name = name;
	}
	
	public Chatter(final String name, ChatMessage message)
	{
		this.name = name;
		this.submittedMessages.add(message);
	}
	
	public void increaseSpamCountCaps()
	{
		spamCountCaps++;
		setSpamStarted();
	}
	
	public void increaseSpamCountChars()
	{
		spamCountChars++;
		setSpamStarted();
	}
	
	public void increaseSpamCountFlood()
	{
		spamCountFlood++;
		setSpamStarted();
	}
	
	public void increaseSpamCountSimilar()
	{
		spamCountSimilar++;
		setSpamStarted();
	}
	
	public void increaseSpamCountUrls()
	{
		spamCountUrls++;
		setSpamStarted();
	}
	
	public void increaseSpamCountBlacklist()
	{
		spamCountBlacklist++;
		setSpamStarted();
	}
	
	private void setSpamStarted()
	{
		if(spamStarted == 0)
			spamStarted = (new Date()).getTime();
	}
	
	public long getSpamStarted()
	{
		return spamStarted;
	}
	
	public int getSpamCountCaps()
	{
		return spamCountCaps;
	}

	public int getSpamCountChars()
	{
		return spamCountChars;
	}

	public int getSpamCountFlood()
	{
		return spamCountFlood;
	}
	
	public int getSpamCountSimilar()
	{
		return spamCountSimilar;
	}

	public int getSpamCountUrls()
	{
		return spamCountUrls;
	}

	public int getSpamCountBlacklist()
	{
		return spamCountBlacklist;
	}

	public void resetSpamViolations()
	{
		spamCountCaps = 0;
		spamCountChars = 0;
		spamCountFlood = 0;
		spamCountSimilar = 0;
		spamCountUrls = 0;
		spamCountBlacklist = 0;
		
		spamStarted = 0;
	}
	
	public synchronized void addMessage(ChatMessage message)
	{
		try
		{
			//chatter message list nicht zu voll werden lassen
			if(submittedMessages.size() >= MAX_MESSAGE_HISTORY)
				submittedMessages.remove(0);
		}
		catch(Exception ignored) {  }
		
		submittedMessages.add(message);
	}
	
	public synchronized ChatMessage getLastMessage() throws ChatterHasNoMessagesException 
	{
		if(submittedMessages.size() <= 0)
		{
			throw new ChatterHasNoMessagesException();
		}
		
		return submittedMessages.get(submittedMessages.size()-1);
	}
	
	public synchronized List<ChatMessage> getMessages()
	{
		return submittedMessages;
	}
	
	public String getName()
	{
		return name;
	}
}

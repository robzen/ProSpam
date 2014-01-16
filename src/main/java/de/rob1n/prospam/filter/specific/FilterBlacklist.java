package de.rob1n.prospam.filter.specific;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.chatter.Chatter;
import de.rob1n.prospam.data.specific.Blacklist;
import de.rob1n.prospam.filter.Filter;

import java.util.List;
import java.util.regex.Matcher;

public class FilterBlacklist extends Filter
{
	private final Blacklist blacklist;
	private final List<String> blacklistStrings;
	
	public FilterBlacklist(ProSpam plugin)
	{
		super(plugin);
		
		blacklist = dataHandler.getBlacklist();
		blacklistStrings = blacklist.blacklist;
	}

	@Override
	protected String executeFilter(final Chatter chatter, final String message)
	{
		String cleanedMessage = message;
		
		try
		{
			for(String blacklistEntry: blacklistStrings)
			{
				final boolean hasParameterIgnoreLine = blacklistEntry.contains("{i}");
				final boolean hasParameterExact = blacklistEntry.contains("{e}");
				
				if(hasParameterIgnoreLine)
					blacklistEntry = blacklistEntry.replace("{i}", "");
				
				if(hasParameterExact)
					blacklistEntry = blacklistEntry.replace("{e}", "");
				
				if(message.contains(blacklistEntry))
				{
					if(hasParameterExact)
					{
						final String msgBefore = cleanedMessage;
						
						cleanedMessage = cleanedMessage.replaceAll("(?i)\\b"+blacklistEntry+"\\b", getCensorChars(blacklistEntry));
						
						if(hasParameterIgnoreLine && !cleanedMessage.equalsIgnoreCase(msgBefore))
							return null;
					}
					else
					{
						if(hasParameterIgnoreLine)
							return null;
						else
							cleanedMessage = cleanedMessage.replaceAll("(?i)"+blacklistEntry, getCensorChars(blacklistEntry));
					}
				}
			}
			
			return cleanedMessage;
		}
		catch(NullPointerException ignored) {  }
		
		return message;
	}
	
	private String getCensorChars(final String word)
	{
		String coverChars = blacklist.cover_chars;
		final int coverCharsSize = coverChars.length() - 1;
		final int wordLength = word.length();
		
		if(coverCharsSize < 0)
			coverChars = "*";
		
		String censorChars = "";
		for(int i=0; i<wordLength; ++i)
		{
			censorChars += i == 0 ? word.charAt(0) : coverChars.charAt((int)(Math.random()*coverCharsSize));
		}
		
		return Matcher.quoteReplacement(censorChars);
	}

}

package de.rob1n.prospam.filter.specific;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.chatter.Chatter;
import de.rob1n.prospam.data.specific.Blacklist;
import de.rob1n.prospam.filter.Filter;

import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterBlacklist extends Filter
{
	private final Blacklist blacklist;
	private final List<String> blacklistStrings;

    private final Random rand = new Random();
	
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
				
				if(message.toLowerCase().contains(blacklistEntry.toLowerCase()))
				{
					if(hasParameterExact)
					{
						final String msgBefore = cleanedMessage;
						
						cleanedMessage = cleanedMessage.replaceAll("(?i)\\b" + Pattern.quote(blacklistEntry) + "\\b", Matcher.quoteReplacement(getCensorChars(blacklistEntry)));
						
						if(hasParameterIgnoreLine && !cleanedMessage.equalsIgnoreCase(msgBefore))
							return null;
					}
					else
					{
						if(hasParameterIgnoreLine)
							return null;
						else
							cleanedMessage = cleanedMessage.replaceAll("(?i)" + Pattern.quote(blacklistEntry), Matcher.quoteReplacement(getCensorChars(blacklistEntry)));
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
        StringBuilder sb = new StringBuilder(word.length());

        if(coverChars.trim().isEmpty())
            coverChars = "*";

        for(int i=0; i<word.length(); i++)
        {
            if(i == 0 && word.length() > 1)
                sb.append(word.charAt(0));
            else
            {
                char randomChar = coverChars.charAt(rand.nextInt((coverChars.length())));
                sb.append(randomChar);
            }
        }

        return sb.toString();
	}
}

package de.rob1n.prospam.filter.specific;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.chatter.Chatter;
import de.rob1n.prospam.filter.Filter;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;

public class FilterCaps extends Filter
{
	public FilterCaps(ProSpam plugin)
	{
		super(plugin);
	}

	@Override
	protected String executeFilter(final Chatter chatter, final String message)
	{
		final Player[] onlinePlayers = plugin.getServer().getOnlinePlayers();
		
		String[] wordArr = message.split(" ");
		final int wordArrLength = wordArr.length;
		
		for(int i=0; i<wordArrLength; ++i)
		{
			//wort nicht checken falls spielername
			if(isPlayer(onlinePlayers, wordArr[i]))
				continue;
			
			//wort nicht checken falls in whitelist
			if(isWhitelisted(wordArr[i]))
				continue;
			
			if(tooManyCaps(wordArr[i]))
				wordArr[i] = wordArr[i].toLowerCase();
		}
		
		return StringUtils.join(wordArr, " ");
	}
	
	private boolean tooManyCaps(final String word)
	{
		final int wordLength = word.length();
		int capsCount = 0;
		
		//dont check words with less than 4 chars
		if(wordLength <= 3)
			return false;
		
		try
		{
			for(int i=0; i<wordLength; ++i)
			{
				if(Character.isUpperCase(word.charAt(i)))
					capsCount++;
			}
		}
		catch(IndexOutOfBoundsException e) { return false; }
		
		return (capsCount >= ((float)wordLength/100)*settings.filter_caps_max);
	}
}

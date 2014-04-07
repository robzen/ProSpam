package de.rob1n.prospam.filter.specific;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.chatter.Chatter;
import de.rob1n.prospam.filter.Filter;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;

import java.util.regex.Pattern;

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

        //dont filter if caps count is acceptable
        if(!tooManyCaps(message))
            return message;
		
		for(int i=0; i<wordArrLength; ++i)
		{
			//wort nicht checken falls spielername
			if(isPlayerName(onlinePlayers, wordArr[i]))
				continue;

            //wort nicht checken falls smilie
            if(isSmilie(wordArr[i]))
                continue;
			
			//wort nicht checken falls in whitelist
			if(isWhitelisted(wordArr[i]))
				continue;
			
			if(tooManyCaps(wordArr[i]))
				wordArr[i] = wordArr[i].toLowerCase();
		}
		
		return StringUtils.join(wordArr, " ");
	}

    private boolean isSmilie(String txt)
    {
        return Pattern.matches("(?i)X-?D{1,3}|D-?X{1,3}|:-?D{1,3}|D{1,3}-?:|D{1,3}-?:|:-?P{1,3}|:-?O{1,3}|O{1,3}-?:", txt);
    }
	
	private boolean tooManyCaps(final String word)
	{
		return (countCaps(word) >= ((float)word.length()/100)*settings.filter_caps_max);
	}

    private int countCaps(String txt)
    {
        int counter = 0;

        try
        {
            for(int i=0; i<txt.length(); ++i)
            {
                if(Character.isUpperCase(txt.charAt(i)))
                    counter++;
            }
        }
        catch(IndexOutOfBoundsException ignored) { }

        return counter;
    }
}

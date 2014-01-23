package de.rob1n.prospam.filter.specific;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.chatter.Chatter;
import de.rob1n.prospam.filter.Filter;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;

public class FilterChars extends Filter
{
	//private static final int MAX_IDENTICAL_CHARS = 3;

	public FilterChars(ProSpam plugin)
	{
		super(plugin);
	}

	@Override
	protected String executeFilter(final Chatter chatter, final String message)
	{
		final Player[] onlinePlayers = plugin.getServer().getOnlinePlayers();

		String[] wordArr = message.split(" ");
		final int wordArrLength = wordArr.length;

		for (int i = 0; i < wordArrLength; ++i)
		{
			// wort nicht checken falls spielername
			if (isPlayer(onlinePlayers, wordArr[i]))
				continue;

			// wort nicht checken falls in whitelist
			if (isWhitelisted(wordArr[i]))
				continue;

            //wort nicht filtern falls zahl <= 10 chars 1000000000
            if(StringUtils.isNumeric(wordArr[i]) && wordArr[i].length() <= 10)
                continue;
			
			//filter multiple chars
			wordArr[i] = wordArr[i].replaceAll("(?i)(.)\\1\\1+", "$1$1$1");
		}
		
		return StringUtils.join(wordArr, " ");
	}
}

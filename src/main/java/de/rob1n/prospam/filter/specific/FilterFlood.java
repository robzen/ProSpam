package de.rob1n.prospam.filter.specific;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.chatter.Chatter;
import de.rob1n.prospam.exception.ChatterHasNoMessagesException;
import de.rob1n.prospam.filter.Filter;

import java.util.Date;

public class FilterFlood extends Filter
{

	public FilterFlood(ProSpam plugin)
	{
		super(plugin);
	}

	@Override
	protected String executeFilter(Chatter chatter, String message)
	{
		final long timeNow = (new Date()).getTime();
		
		try
		{
			if((timeNow - chatter.getLastMessage().timeSubmitted)/1000L <= settings.filter_flood_lock)
				return null;
		}
		catch(ChatterHasNoMessagesException ignored) { }
				
		return message;
	}

}

package de.rob1n.prospam.filter;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.chatter.Chatter;
import de.rob1n.prospam.data.DataHandler;
import de.rob1n.prospam.data.StringList;
import de.rob1n.prospam.data.specific.Settings;
import de.rob1n.prospam.data.specific.Strings;
import org.bukkit.entity.Player;


public abstract class Filter
{
	protected final ProSpam plugin;
	protected final DataHandler dataHandler;
	protected final Settings settings;
	protected final Strings strings;
	protected final StringList whitelist;
	
	
	public Filter(ProSpam plugin)
	{
		this.plugin = plugin;
		
		dataHandler = plugin.getDataHandler();
		settings = dataHandler.getSettings();
		strings = dataHandler.getStrings();
		whitelist = new StringList(dataHandler.getWhitelist().whitelist);
	}
	
	public String execute(final Chatter chatter, final String message) throws IllegalArgumentException
	{
		if(plugin == null || message == null)
			throw new IllegalArgumentException("There was a problem executing the filter");
		
		return executeFilter(chatter, message);
	}
	
	/**
	 * @param chatter the chatter
	 * @param message the chat message
	 * @return the filtered message
	 * if null is returned the chatevent should get canceled
	 */
	protected abstract String executeFilter(final Chatter chatter, final String message);
	
	protected boolean isPlayer(final Player[] playersOnline, final String word)
	{
		for(Player p: playersOnline)
		{
			if(p.getName().equalsIgnoreCase(word))
				return true;
		}
		
		return false;
	}
	
	protected boolean isWhitelisted(final String word)
	{
		return settings.whitelist_enabled && whitelist.containsIgnoreCase(word);
	}
}
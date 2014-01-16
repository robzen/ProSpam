package de.rob1n.prospam.chatter;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.exception.ChatterNotFoundException;
import de.rob1n.prospam.exception.PlayerNotOnlineException;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ChatterHandler
{
	private static List<Chatter> chatters = new ArrayList<Chatter>();
	
	private final ProSpam plugin;
	
	public ChatterHandler(ProSpam plugin)
	{
		this.plugin = plugin;
	}
	
	public static Chatter getChatter(final String playerName, final boolean addIfMissing) throws ChatterNotFoundException
	{
		for(Chatter c: chatters)
		{
			if(c.getName().equalsIgnoreCase(playerName))
				return c;
		}
		
		if(addIfMissing)
		{
			//add new chatter to the list
			final Chatter newChatter = new Chatter(playerName);
			chatters.add(newChatter);
			
			return newChatter;
		}
		else
		{
			throw new ChatterNotFoundException();
		}
	}
	
	/**
	 * @param playerName the playername
	 * @return if it exists, chatter from the chatters list
	 * else adds new chatter to the list and returns it
	 */
	public static Chatter getChatter(final String playerName)
	{
		try
		{
			return getChatter(playerName, true);
		}
		catch (ChatterNotFoundException e)
		{
			//never happens
			return null;
		}
	}
	
	/**
	 * @param chatter the chatter
	 * @return player object if online
	 * @throws PlayerNotOnlineException if player is offline
	 */
	public Player getPlayer(final Chatter chatter) throws PlayerNotOnlineException
	{
		final Player player = plugin.getServer().getPlayer(chatter.getName());
		
		if(player == null)
			throw new PlayerNotOnlineException();
		
		return player;
	}
	
	public List<Chatter> getChatters()
	{
		return chatters;
	}
}

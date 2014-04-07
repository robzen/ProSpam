package de.rob1n.prospam.chatter;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.exception.NotFoundException;
import de.rob1n.prospam.exception.PlayerNotOnlineException;
import org.bukkit.entity.Player;

import java.util.*;

public class ChatterHandler
{
	private final static List<Chatter> chatters = new ArrayList<Chatter>();
	
	private final ProSpam plugin;
	
	public ChatterHandler(ProSpam plugin)
	{
		this.plugin = plugin;
	}
	
	public static Chatter getChatter(final UUID uuid, final boolean addIfMissing) throws NotFoundException
	{
		for(Chatter c: chatters)
		{
			if(c.getUUID().equals(uuid))
				return c;
		}
		
		if(addIfMissing)
		{
			//add new chatter to the list
			final Chatter newChatter = new Chatter(uuid);
			chatters.add(newChatter);
			
			return newChatter;
		}
		else
		{
			throw new NotFoundException("Chatter not found");
		}
	}
	
	/**
	 * @param uuid the id of the player
	 * @return if it exists, chatter from the chatters list
	 * else adds new chatter to the list and returns it
	 */
	public static Chatter getChatter(final UUID uuid)
	{
		try
		{
			return getChatter(uuid, true);
		}
		catch (NotFoundException e)
		{
			//never happens
			return null;
		}
	}

    /**
     * Get chatter by name
     * @param name the chatters name
     * @return all players with this name, empty set if none found
     */
    public static Set<Chatter> getChatter(final String name)
    {
        Set<Chatter> chattersFound = new HashSet<Chatter>(1);
        for (Chatter chatter : chatters)
        {
            try
            {
                //noinspection deprecation
                if(chatter.getPlayer().getName().equalsIgnoreCase(name))
                    chattersFound.add(chatter);
            }
            catch (PlayerNotOnlineException ignored)
            {
                //saved chatter isn't online anymore
            }
        }

        return chattersFound;
    }
	
	/**
	 * @param chatter the chatter
	 * @return player object if online
	 * @throws PlayerNotOnlineException if player is offline
	 */
	public Player getPlayer(final Chatter chatter) throws PlayerNotOnlineException
	{
		final Player player = plugin.getServer().getPlayer(chatter.getUUID());
		
		if(player == null)
			throw new PlayerNotOnlineException();
		
		return player;
	}
	
	public List<Chatter> getChatters()
	{
		return chatters;
	}
}

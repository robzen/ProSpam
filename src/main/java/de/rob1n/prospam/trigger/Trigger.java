package de.rob1n.prospam.trigger;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.chatter.Chatter;
import de.rob1n.prospam.data.specific.Settings;
import de.rob1n.prospam.exception.IllegalCommandNameException;
import de.rob1n.prospam.exception.PlayerNotOnlineException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.Map.Entry;

public class Trigger
{
	protected final ProSpam plugin;
	protected final Settings settings;

	public Trigger(ProSpam plugin)
	{
		this.plugin = plugin;
		this.settings = plugin.getDataHandler().getSettings();
	}

	public boolean execute(final Chatter chatter, final int spamCount, final HashMap<Integer, List<String>> cmdMap)
	{
		final long timeNow = (new Date()).getTime();
		final long spamStarted = chatter.getSpamStarted();
		
		List<String> everytimeActions = null;
		Iterator<Entry<Integer, List<String>>> it = cmdMap.entrySet().iterator();
		final Player player;
		try
		{
			player = plugin.getChatterHandler().getPlayer(chatter);
		}
		catch (PlayerNotOnlineException e)
		{
			plugin.getLogger().warning("Could not execute trigger: " + e.getMessage());
			return false;
		}

		if ((settings.trigger_counter_reset != 0) && (timeNow - spamStarted >= (settings.trigger_counter_reset * 1000 * 60)))
			chatter.resetSpamViolations();

		while (it.hasNext())
		{
			Map.Entry<Integer, List<String>> pairs = it.next();

			try
			{
				final int key = pairs.getKey();
				final List<String> cmds = pairs.getValue();

				if (key == 0)
					everytimeActions = cmds;

				if (key == spamCount)
				{
					everytimeActions = null;

					execute(player, cmds);
				}
			}
			catch (Exception e)
			{
				plugin.getLogger().warning("Could not execute trigger: " + e.getMessage());
				return false;
			}
		}

		if (everytimeActions != null)
		{
			try
			{
				execute(player, everytimeActions);
			}
			catch (Exception e)
			{
				plugin.getLogger().warning("Could not execute trigger: " + e.getMessage());
				return false;
			}
		}

		return true;
	}

	private void execute(final Player player, final List<String> cmds) throws IllegalCommandNameException, PlayerNotOnlineException
	{
		for (String cmd : cmds)
		{
			try
			{
				//replace {u} with playerName
				cmd = cmd.replaceAll("\\{u\\}", player.getName());
				cmd = cmd.substring(1); // cut the / char
			}
			catch (Exception e)
			{
				throw new IllegalCommandNameException();
			}

			if (player.isOnline())
			{
				// execute command
				if (!Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), cmd))
					throw new IllegalCommandNameException();
			}
			else
				throw new PlayerNotOnlineException();
		}
	}
}

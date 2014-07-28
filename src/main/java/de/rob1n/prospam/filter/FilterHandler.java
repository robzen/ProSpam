package de.rob1n.prospam.filter;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.chatter.Chatter;
import de.rob1n.prospam.chatter.ChatterHandler;
import de.rob1n.prospam.data.specific.Settings;
import de.rob1n.prospam.data.specific.Strings;
import de.rob1n.prospam.filter.specific.*;
import de.rob1n.prospam.trigger.Trigger;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class FilterHandler
{
	private final ProSpam plugin;

	private final FilterBlacklist filterBlacklist;
	private final FilterCaps filterCaps;
	private final FilterChars filterChars;
	private final FilterSimilar filterSimilar;
	private final FilterUrls filterUrls;
	private final FilterFlood filterFlood;

	private final Settings settings;
	private final Strings strings;
	
	private final Trigger trigger;

	public FilterHandler(ProSpam plugin)
	{
		this.plugin = plugin;

		filterBlacklist = new FilterBlacklist(plugin);
		filterCaps = new FilterCaps(plugin);
		filterChars = new FilterChars(plugin);
		filterSimilar = new FilterSimilar(plugin);
		filterUrls = new FilterUrls(plugin);
		filterFlood = new FilterFlood(plugin);

		settings = plugin.getDataHandler().getSettings();
		strings = plugin.getDataHandler().getStrings();
		
		trigger = new Trigger(plugin);
	}

    /**
     * Execute all the filters
     * @param player the player
     * @param chatMessage the txt to filter
     * @return the filtered txt, if null is returend the whole txt should be ignored
     */
	@SuppressWarnings("deprecation")
    public String execute(final Player player, final String chatMessage)
	{
		if (!player.hasPermission("prospam.nocheck"))
		{
			final Chatter chatter = ChatterHandler.getChatter(player.getUniqueId());

			final List<String> filters_triggered = new ArrayList<String>();
			String previouslyFilteredMessage = chatMessage;
			
			String filteredMessage = chatMessage;

			try
			{
				if (settings.filter_enabled_chars)
				{
					filteredMessage = filterChars.execute(chatter, filteredMessage);
					
					//check if this filter got triggert
					if(filteredMessage == null || !previouslyFilteredMessage.equals(filteredMessage))
					{
						chatter.increaseSpamCountChars();
						filters_triggered.add("Chars");
						
						if(settings.trigger_enabled_chars)
							trigger.execute(chatter, chatter.getSpamCountChars(), settings.trigger_chars);
					}
					previouslyFilteredMessage = filteredMessage;
					
					if (filteredMessage == null)
						return null;
				}

				if (settings.filter_enabled_caps)
				{
					filteredMessage = filterCaps.execute(chatter, filteredMessage);
					
					//check if this filter got triggert
					if(filteredMessage == null || !previouslyFilteredMessage.equals(filteredMessage))
					{
						chatter.increaseSpamCountCaps();
						filters_triggered.add("Caps");

						if(settings.trigger_enabled_caps)
							trigger.execute(chatter, chatter.getSpamCountCaps(), settings.trigger_caps);
					}
					previouslyFilteredMessage = filteredMessage;
					
					if (filteredMessage == null)
						return null;
				}
				
				if (settings.filter_enabled_urls)
				{
					filteredMessage = filterUrls.execute(chatter, filteredMessage);
					
					//check if this filter got triggert
					if(filteredMessage == null || !previouslyFilteredMessage.equals(filteredMessage))
					{
						chatter.increaseSpamCountUrls();
						filters_triggered.add("URL");
						
						if(settings.trigger_enabled_urls)
							trigger.execute(chatter, chatter.getSpamCountUrls(), settings.trigger_urls);
					}
					previouslyFilteredMessage = filteredMessage;
					
					if (filteredMessage == null)
						return null;
				}

				if (settings.filter_enabled_flood)
				{
					filteredMessage = filterFlood.execute(chatter, filteredMessage);
					
					if (filteredMessage == null)
					{
						chatter.increaseSpamCountFlood();
						filters_triggered.add("Flood");
						informSpam(player.getUniqueId(), player.getName(), filters_triggered, chatMessage);
						
						if(settings.trigger_enabled_flood)
							trigger.execute(chatter, chatter.getSpamCountFlood(), settings.trigger_flood);
						
						if(strings.filter_lines_locked != null && !strings.filter_lines_locked.isEmpty())
							player.sendMessage(ChatColor.translateAlternateColorCodes('&', strings.filter_lines_locked));
						
						return null;
					}
				}

				if (settings.filter_enabled_similar)
				{
					filteredMessage = filterSimilar.execute(chatter, filteredMessage);
					
					if (filteredMessage == null)
					{
						chatter.increaseSpamCountSimilar();
						filters_triggered.add("Similar");
						informSpam(player.getUniqueId(), player.getName(), filters_triggered, chatMessage);
						
						if(settings.trigger_enabled_similar)
							trigger.execute(chatter, chatter.getSpamCountSimilar(), settings.trigger_similar);
						
						if(strings.filter_lines_similar != null && !strings.filter_lines_similar.isEmpty())
							player.sendMessage(ChatColor.translateAlternateColorCodes('&', strings.filter_lines_similar));
						
						return null;
					}
				}

				if (settings.filter_enabled_blacklist)
				{
					filteredMessage = filterBlacklist.execute(chatter, filteredMessage);
					
					//check if this filter got triggered
					if(filteredMessage == null || !previouslyFilteredMessage.equals(filteredMessage))
					{
						chatter.increaseSpamCountBlacklist();
						filters_triggered.add("Blacklist");
						
						if(settings.trigger_enabled_blacklist)
							trigger.execute(chatter, chatter.getSpamCountBlacklist(), settings.trigger_blacklist);
					}
					
					if (filteredMessage == null)
					{
						informSpam(player.getUniqueId(), player.getName(), filters_triggered, chatMessage);
						
						if(strings.blacklist_lines_ignored != null && !strings.blacklist_lines_ignored.isEmpty())
							player.sendMessage(ChatColor.translateAlternateColorCodes('&', strings.blacklist_lines_ignored));
						
						return null;
					}
				}
				
				if(filters_triggered.size() > 0)
					informSpam(player.getUniqueId(), player.getName(), filters_triggered, chatMessage);

				return filteredMessage;
			}
			catch (IllegalArgumentException e)
			{
                ProSpam.log(Level.SEVERE, e.getMessage());
			}
			catch (NullPointerException e)
			{
                ProSpam.log(Level.SEVERE, e.getMessage());
			}
		}

		return chatMessage;
	}
	
	private void informSpam(final UUID uuid, final String playerName, final List<String> triggeredFilters, final String origMessage)
	{
		final Player[] players = plugin.getServer().getOnlinePlayers();
		final String joinedFilters = StringUtils.join(triggeredFilters, ", ");
		
		if(settings.log_spam)
            ProSpam.log(Level.INFO, MessageFormat.format("{0} [UUID: {1}] triggered a spam filter: {2} [{3}]", playerName, uuid, joinedFilters, origMessage));
		
		for(Player player: players)
		{
			if(player.hasPermission("prospam.inform")/* && !player.getName().equals(playerName)*/)
				player.sendMessage(ProSpam.prefixed(ChatColor.translateAlternateColorCodes('&', MessageFormat.format(strings.trigger_information, playerName, joinedFilters))));
		}
	}
}

package de.rob1n.prospam.listener;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.chatter.ChatMessage;
import de.rob1n.prospam.chatter.Chatter;
import de.rob1n.prospam.chatter.ChatterHandler;
import de.rob1n.prospam.data.specific.Settings;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener
{
	private final ProSpam plugin;
	
	private final Settings settings;
	
	public ChatListener(ProSpam plugin)
	{
		this.plugin = plugin;
		this.settings = plugin.getDataHandler().getSettings();
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event)
	{
		if(settings.enabled)
		{
			final Player player = event.getPlayer();
			final String message = event.getMessage();
			
			final Chatter chatter = ChatterHandler.getChatter(player.getName());
			
			String filteredMessage = plugin.getFilterHandler().execute(player, message);
			
			if(filteredMessage != null)
			{
				chatter.addMessage(new ChatMessage(filteredMessage));
				event.setMessage(filteredMessage);
			}
			else
				event.setCancelled(true);
		}
	}
}

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
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

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
        String filtered = filterText(event.getPlayer(), event.getMessage());

        if(filtered != null)
        {
            event.setMessage(filtered);
        }
        else
        {
            event.setCancelled(true);
        }
	}

    @EventHandler(ignoreCancelled = true)
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event)
    {
        String txt = event.getMessage();

        for(String cmd: settings.filter_commands)
        {
            String cmdStr[] = txt.split(" ");

            if(cmdStr.length >= 1 && cmdStr[0].equalsIgnoreCase(cmd))
            {
                String filtered = filterText(event.getPlayer(), txt.substring(cmd.length()));

                if(filtered != null)
                {
                    event.setMessage(cmd + filtered);
                }
                else
                {
                    event.setCancelled(true);
                }

                return;
            }
        }
    }

    /**
     * Apply the filters
     * @param player the player
     * @param txt the text to filter
     * @return the filtered txt or null if event should be ignored
     */
    private String filterText(Player player, String txt)
    {
        if(settings.enabled)
        {
            final Chatter chatter = ChatterHandler.getChatter(player.getUniqueId());
            String filteredTxt = plugin.getFilterHandler().execute(player, txt);

            if(filteredTxt != null)
            {
                //log message (saves ma. 40 msgs)
                chatter.addMessage(new ChatMessage(filteredTxt));

                return filteredTxt;
            }

            return null;
        }

        //if not enabled just return the input txt
        return txt;
    }
}

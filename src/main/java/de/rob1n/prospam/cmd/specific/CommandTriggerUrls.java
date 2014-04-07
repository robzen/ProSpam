package de.rob1n.prospam.cmd.specific;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.cmd.CommandWithGui;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandTriggerUrls extends CommandTrigger implements CommandWithGui
{

	public CommandTriggerUrls(ProSpam plugin)
	{
		super(plugin);
	}

	@Override
	public String getName()
	{
		return "trigger-urls";
	}

	@Override
	public String getDescription()
	{
		return "Trigger a server command if someone is posting a url";
	}

	@Override
	public String getUsage()
	{
		return "trigger-urls [violation #] <commands>";
	}
	
	@Override
	public String[] getAliases()
	{
		return new String[] { "tu", "tur", "t-u", "t-ur", "turl", "t-url" };
	}

	@Override
	public void saveInSettings(int vNumber, List<String> cmds)
	{
		settings.trigger_urls.put(vNumber, cmds);
	}

    @Override
    public void showGui(Player player)
    {
        showGui(player, "URLs", settings.trigger_urls);
    }
}

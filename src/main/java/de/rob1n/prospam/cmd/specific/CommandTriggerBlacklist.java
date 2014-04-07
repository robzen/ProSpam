package de.rob1n.prospam.cmd.specific;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.cmd.CommandWithGui;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

public class CommandTriggerBlacklist extends CommandTrigger implements CommandWithGui
{

	public CommandTriggerBlacklist(ProSpam plugin)
	{
		super(plugin);
	}

	@Override
	public String getName()
	{
		return "trigger-blacklist";
	}

	@Override
	public String getDescription()
	{
		return "Trigger a server command if someone is posting a word from the blacklist";
	}

	@Override
	public String getUsage()
	{
		return "trigger-blacklist [violation #] <commands>";
	}
	
	@Override
	public String[] getAliases()
	{
		return new String[] { "tb", "tbl", "t-b", "t-bl" };
	}

	@Override
	public void saveInSettings(int vNumber, List<String> cmds)
	{
		settings.trigger_blacklist.put(vNumber, cmds);
	}

    @Override
    public HashMap<Integer, List<String>> getTriggers()
    {
        return settings.trigger_blacklist;
    }

    @Override
    public void showGui(Player player)
    {
        showGui(player, "Blacklist", settings.trigger_blacklist);
    }
}

package de.rob1n.prospam.cmd.specific;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.cmd.CommandWithGui;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

public class CommandTriggerFlood extends CommandTrigger implements CommandWithGui
{

	public CommandTriggerFlood(ProSpam plugin)
	{
		super(plugin);
	}

	@Override
	public String getName()
	{
		return "trigger-flood";
	}

	@Override
	public String getDescription()
	{
		return "Trigger a server command if someone is posting too many messages in a defined time";
	}

	@Override
    public String[] getArgs()
    {
        return new String[] {"[violation #]", "<commands>"};
    }
	
	@Override
	public String[] getAliases()
	{
		return new String[] { "tf", "tfl", "t-f", "t-fl" };
	}

	@Override
	public void saveInSettings(int vNumber, List<String> cmds)
	{
		settings.trigger_flood.put(vNumber, cmds);
	}

    @Override
    public HashMap<Integer, List<String>> getTriggers()
    {
        return settings.trigger_flood;
    }

    @Override
    public void showGui(Player player)
    {
        showGui(player, "Flood", settings.trigger_flood);
    }
}

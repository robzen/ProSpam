package de.rob1n.prospam.cmd.specific;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.cmd.CommandWithGui;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

public class CommandTriggerSimilar extends CommandTrigger implements CommandWithGui
{

	public CommandTriggerSimilar(ProSpam plugin)
	{
		super(plugin);
	}

	@Override
	public String getName()
	{
		return "trigger-similar";
	}

	@Override
	public String getDescription()
	{
		return "Trigger a server command if someone posts a message multiple times";
	}

	@Override
    public String[] getArgs()
    {
        return new String[] {"[violation #]", "<commands>"};
    }
	
	@Override
	public String[] getAliases()
	{
		return new String[] { "ts", "tsi", "t-s", "t-si" };
	}

	@Override
	public void saveInSettings(int vNumber, List<String> cmds)
	{
		settings.trigger_similar.put(vNumber, cmds);
	}

    @Override
    public HashMap<Integer, List<String>> getTriggers()
    {
        return settings.trigger_similar;
    }

    @Override
    public void showGui(Player player)
    {
        showGui(player, "Similar", settings.trigger_similar);
    }
}

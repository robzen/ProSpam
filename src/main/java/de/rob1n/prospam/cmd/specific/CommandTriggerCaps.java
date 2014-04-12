package de.rob1n.prospam.cmd.specific;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.cmd.CommandWithGui;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

public class CommandTriggerCaps extends CommandTrigger implements CommandWithGui
{

	public CommandTriggerCaps(ProSpam plugin)
	{
		super(plugin);
	}

	@Override
	public String getName()
	{
		return "trigger-caps";
	}

	@Override
	public String getDescription()
	{
		return "Trigger a server command if someone is using too many caps";
	}

	@Override
    public String[] getArgs()
    {
        return new String[] {"[violation #]", "<commands>"};
    }
	
	@Override
	public String[] getAliases()
	{
		return new String[] { "tc", "tca", "t-c", "t-ca" };
	}

	@Override
	public void saveInSettings(int vNumber, List<String> cmds)
	{
		settings.trigger_caps.put(vNumber, cmds);
	}

    @Override
    public HashMap<Integer, List<String>> getTriggers()
    {
        return settings.trigger_caps;
    }

    @Override
    public void showGui(Player player)
    {
        showGui(player, "Caps", settings.trigger_caps);
    }
}

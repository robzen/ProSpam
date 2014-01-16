package de.rob1n.prospam.cmd.specific;

import de.rob1n.prospam.ProSpam;

import java.util.List;

public class CommandTriggerFlood extends CommandTrigger
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
	public String getUsage()
	{
		return "trigger-flood [violation #] <commands>";
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
}

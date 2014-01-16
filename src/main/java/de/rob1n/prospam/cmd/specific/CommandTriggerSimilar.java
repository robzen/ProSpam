package de.rob1n.prospam.cmd.specific;

import de.rob1n.prospam.ProSpam;

import java.util.List;

public class CommandTriggerSimilar extends CommandTrigger
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
	public String getUsage()
	{
		return "trigger-similar [violation #] <commands>";
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
}

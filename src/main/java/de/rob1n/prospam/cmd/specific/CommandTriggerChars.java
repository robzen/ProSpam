package de.rob1n.prospam.cmd.specific;

import de.rob1n.prospam.ProSpam;

import java.util.List;

public class CommandTriggerChars extends CommandTrigger
{

	public CommandTriggerChars(ProSpam plugin)
	{
		super(plugin);
	}

	@Override
	public String getName()
	{
		return "trigger-chars";
	}

	@Override
	public String getDescription()
	{
		return "Trigger a server command if someone is using too many chars";
	}

	@Override
	public String getUsage()
	{
		return "trigger-chars [violation #] <commands>";
	}
	
	@Override
	public String[] getAliases()
	{
		return new String[] { "tch", "t-ch" };
	}

	@Override
	public void saveInSettings(int vNumber, List<String> cmds)
	{
		settings.trigger_chars.put(vNumber, cmds);
	}
}

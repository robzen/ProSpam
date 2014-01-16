package de.rob1n.prospam.cmd.specific;

import de.rob1n.prospam.ProSpam;

import java.util.List;

public class CommandTriggerBlacklist extends CommandTrigger
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
}

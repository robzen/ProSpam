package de.rob1n.prospam.cmd.specific;

import de.rob1n.prospam.ProSpam;

import java.util.List;

public class CommandTriggerCaps extends CommandTrigger
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
	public String getUsage()
	{
		return "trigger-caps [violation #] <commands>";
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
}

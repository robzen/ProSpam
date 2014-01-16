package de.rob1n.prospam.cmd.specific;

import de.rob1n.prospam.ProSpam;

import java.util.List;

public class CommandTriggerUrls extends CommandTrigger
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
}

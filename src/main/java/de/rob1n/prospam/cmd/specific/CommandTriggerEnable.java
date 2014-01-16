package de.rob1n.prospam.cmd.specific;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.cmd.Command;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;

public class CommandTriggerEnable extends Command
{

	public CommandTriggerEnable(ProSpam plugin)
	{
		super(plugin);
	}

	@Override
	public String getName()
	{
		return "trigger-enable";
	}

	@Override
	public String getDescription()
	{
		return "Enable spam triggers";
	}

	@Override
	public String getUsage()
	{
		return "trigger-enable <caps|chars|flood|similar|urls|blacklist>";
	}

	@Override
	public void execute(CommandSender sender, String[] parameter) throws IllegalArgumentException
	{
		if(parameter.length <= 1)
			throw new IllegalArgumentException();
		
		final String triggerName = parameter[1];
		if(triggerName.equalsIgnoreCase("caps"))
			settings.trigger_enabled_caps = true;
		else if(triggerName.equalsIgnoreCase("chars"))
			settings.trigger_enabled_chars = true;
		else if(triggerName.equalsIgnoreCase("flood"))
			settings.trigger_enabled_flood = true;
		else if(triggerName.equalsIgnoreCase("similar"))
			settings.trigger_enabled_similar = true;
		else if(triggerName.equalsIgnoreCase("urls"))
			settings.trigger_enabled_urls = true;
		else if(triggerName.equalsIgnoreCase("blacklist"))
			settings.trigger_enabled_blacklist = true;
		else
			throw new IllegalArgumentException();
		
		sender.sendMessage(plugin.prefixed(StringUtils.capitalize(triggerName)+" triggers successfully enabled"));
		
		if(!settings.save())
			sender.sendMessage(plugin.prefixed("Could not save state in the config file!"));
	}

}

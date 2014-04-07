package de.rob1n.prospam.cmd.specific;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.cmd.Command;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;

public class CommandFilterDisable extends Command
{
	public CommandFilterDisable(ProSpam plugin)
	{
		super(plugin);
	}

	@Override
	public String getName()
	{
		return "filter-disable";
	}

	@Override
	public String getDescription()
	{
		return "Disables a Filter";
	}

	@Override
	public String getUsage()
	{
		return "filter-disable <caps|chars|flood|similar|urls|blacklist>";
	}

	@Override
	public void execute(CommandSender sender, String[] parameter)
	{
		if(parameter.length <= 1)
			throw new IllegalArgumentException();
		
		final String filterName = parameter[1];
		if(filterName.equalsIgnoreCase("caps"))
			settings.filter_enabled_caps = false;
		else if(filterName.equalsIgnoreCase("chars"))
			settings.filter_enabled_chars = false;
		else if(filterName.equalsIgnoreCase("flood"))
			settings.filter_enabled_flood = false;
		else if(filterName.equalsIgnoreCase("similar"))
			settings.filter_enabled_similar = false;
		else if(filterName.equalsIgnoreCase("urls"))
			settings.filter_enabled_urls = false;
		else if(filterName.equalsIgnoreCase("blacklist"))
			settings.filter_enabled_blacklist = false;
		else
			throw new IllegalArgumentException();
		
		sender.sendMessage(ProSpam.prefixed(StringUtils.capitalize(filterName)+" filter successfully disabled"));
		
		if(!settings.save())
			sender.sendMessage(ProSpam.prefixed("Could not save state in the config file!"));
	}
}

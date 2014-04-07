package de.rob1n.prospam.cmd.specific;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.cmd.Command;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;

public class CommandFilterEnable extends Command
{
	public CommandFilterEnable(ProSpam plugin)
	{
		super(plugin);
	}

	@Override
	public String getName()
	{
		return "filter-enable";
	}

	@Override
	public String getDescription()
	{
		return "Enables a Filter";
	}

	@Override
	public String getUsage()
	{
		return "filter-enable <caps|chars|flood|similar|urls|blacklist>";
	}

	@Override
	public void execute(CommandSender sender, String[] parameter)
	{
		if(parameter.length <= 1)
			throw new IllegalArgumentException();
		
		final String filterName = parameter[1];
		if(filterName.equalsIgnoreCase("caps"))
			settings.filter_enabled_caps = true;
		else if(filterName.equalsIgnoreCase("chars"))
			settings.filter_enabled_chars = true;
		else if(filterName.equalsIgnoreCase("flood"))
			settings.filter_enabled_flood = true;
		else if(filterName.equalsIgnoreCase("similar"))
			settings.filter_enabled_similar = true;
		else if(filterName.equalsIgnoreCase("urls"))
			settings.filter_enabled_urls = true;
		else if(filterName.equalsIgnoreCase("blacklist"))
			settings.filter_enabled_blacklist = true;
		else
			throw new IllegalArgumentException();
		
		sender.sendMessage(ProSpam.prefixed(StringUtils.capitalize(filterName)+" filter successfully enabled"));
		
		if(!settings.save())
			sender.sendMessage(ProSpam.prefixed("Could not save state in the config file!"));
	}

}

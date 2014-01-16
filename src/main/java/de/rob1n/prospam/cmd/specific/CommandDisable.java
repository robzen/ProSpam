package de.rob1n.prospam.cmd.specific;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.cmd.Command;
import org.bukkit.command.CommandSender;

public class CommandDisable extends Command
{

	public CommandDisable(ProSpam plugin)
	{
		super(plugin);
	}

	@Override
	public String getName()
	{
		return "disable";
	}

	@Override
	public String getDescription()
	{
		return "Disables the plugin";
	}

	@Override
	public String getUsage()
	{
		return "disable";
	}

	@Override
	public void execute(CommandSender sender, String[] parameter)
	{
		settings.enabled = false;
		
		sender.sendMessage(plugin.prefixed("Plugin successfully disabled."));
		
		if(!settings.save())
			sender.sendMessage(plugin.prefixed("Could not save state in the config file!"));
	}

}

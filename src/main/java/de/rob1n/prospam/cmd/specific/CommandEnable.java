package de.rob1n.prospam.cmd.specific;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.cmd.Command;
import org.bukkit.command.CommandSender;

public class CommandEnable extends Command
{

	public CommandEnable(ProSpam plugin)
	{
		super(plugin);
	}

	@Override
	public String getName()
	{
		return "enable";
	}

	@Override
	public String getDescription()
	{
		return "Enables the plugin";
	}

	@Override
	public String getUsage()
	{
		return "enable";
	}

	@Override
	public void execute(CommandSender sender, String[] parameter)
	{
		settings.enabled = true;
		
		sender.sendMessage(plugin.prefixed("Plugin successfully enabled."));
		
		if(!settings.save())
			sender.sendMessage(plugin.prefixed("Could not save state in the config file!"));
	}

}

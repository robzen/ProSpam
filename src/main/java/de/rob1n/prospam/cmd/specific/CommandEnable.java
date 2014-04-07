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
		
		if(!settings.save())
        {
            sender.sendMessage(ProSpam.prefixed("Could not save state in the config file!"));
        }
        else
        {
            sender.sendMessage(ProSpam.prefixed("Plugin successfully enabled."));
        }
	}

}

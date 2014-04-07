package de.rob1n.prospam.cmd.specific;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.cmd.Command;
import org.bukkit.command.CommandSender;

public class CommandWhitelistDisable extends Command
{

	public CommandWhitelistDisable(ProSpam plugin)
	{
		super(plugin);
	}

	@Override
	public String getName()
	{
		return "whitelist-disable";
	}

	@Override
	public String getDescription()
	{
		return "Disable the whitelist";
	}

	@Override
	public String getUsage()
	{
		return "whitelist-disable";
	}

	@Override
	public void execute(CommandSender sender, String[] parameter) throws IllegalArgumentException
	{
		settings.whitelist_enabled = false;
		
		if(!settings.save())
        {
            sender.sendMessage(ProSpam.prefixed("Could not save state in the config file!"));
        }
        else
        {
            sender.sendMessage(ProSpam.prefixed("Whitelist successfully disabled."));
        }
	}

}

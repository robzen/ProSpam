package de.rob1n.prospam.cmd.specific;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.cmd.Command;
import org.bukkit.command.CommandSender;

public class CommandWhitelistEnable extends Command
{

	public CommandWhitelistEnable(ProSpam plugin)
	{
		super(plugin);
	}

	@Override
	public String getName()
	{
		return "whitelist-enable";
	}

	@Override
	public String getDescription()
	{
		return "Enable the whitelist";
	}

	@Override
	public String getUsage()
	{
		return "whitelist-enable";
	}

	@Override
	public void execute(CommandSender sender, String[] parameter) throws IllegalArgumentException
	{
		settings.whitelist_enabled = true;
		
		sender.sendMessage(plugin.prefixed("Whitelist successfully enabled."));
		
		if(!settings.save())
			sender.sendMessage(plugin.prefixed("Could not save state in the config file!"));
	}

}

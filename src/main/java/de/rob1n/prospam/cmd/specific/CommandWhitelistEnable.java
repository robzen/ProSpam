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
		

		if(!settings.save())
        {
            sender.sendMessage(ProSpam.prefixed("Could not save state in the config file!"));
        }
        else
        {
            sender.sendMessage(ProSpam.prefixed("Whitelist successfully enabled."));
        }
	}

}

package de.rob1n.prospam.cmd.specific;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.cmd.Command;
import org.bukkit.command.CommandSender;

public class CommandReload extends Command
{

	public CommandReload(ProSpam plugin)
	{
		super(plugin);
	}

	@Override
	public String getName()
	{
		return "reload";
	}

	@Override
	public String getDescription()
	{
		return "Reload settings from the config file";
	}

	@Override
	public String getUsage()
	{
		return "reload";
	}

	@Override
	public void execute(CommandSender sender, String[] parameter) throws IllegalArgumentException
	{
		plugin.getDataHandler().loadAll();
		
		sender.sendMessage(plugin.prefixed("Settings successfully loaded."));
	}

}

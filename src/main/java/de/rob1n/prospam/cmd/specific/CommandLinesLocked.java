package de.rob1n.prospam.cmd.specific;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.cmd.Command;
import org.bukkit.command.CommandSender;

public class CommandLinesLocked extends Command
{

	public CommandLinesLocked(ProSpam plugin)
	{
		super(plugin);
	}

	@Override
	public String getName()
	{
		return "lines-locked";
	}

	@Override
	public String getDescription()
	{
		return "Timespan players have to wait between their messages";
	}

	@Override
	public String getUsage()
	{
		return "lines-locked <seconds>";
	}

	@Override
	public void execute(CommandSender sender, String[] parameter) throws IllegalArgumentException
	{
		if(parameter.length <= 1)
			throw new IllegalArgumentException();
		
		final int lockTime;
		try
		{
			lockTime = Integer.parseInt(parameter[1]);
		}
		catch(NumberFormatException e)
		{
			throw new IllegalArgumentException();
		}
			
		if(lockTime < 0)
			throw new IllegalArgumentException();
		
		settings.filter_flood_lock = lockTime;
		
		sender.sendMessage(plugin.prefixed("Timespan successfully set to "+lockTime+" seconds"));
		
		if(!settings.save())
			sender.sendMessage(plugin.prefixed("Could not save state in the config file!"));
	}

}

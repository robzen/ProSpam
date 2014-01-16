package de.rob1n.prospam.cmd.specific;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.cmd.Command;
import org.bukkit.command.CommandSender;

public class CommandLinesSimilar extends Command
{

	public CommandLinesSimilar(ProSpam plugin)
	{
		super(plugin);
	}

	@Override
	public String getName()
	{
		return "lines-similar";
	}

	@Override
	public String getDescription()
	{
		return "Timespan players are forbidden to post a similar chatline";
	}

	@Override
	public String getUsage()
	{
		return "lines-similar <seconds>";
	}

	@Override
	public void execute(CommandSender sender, String[] parameter) throws IllegalArgumentException
	{
		if(parameter.length <= 1)
			throw new IllegalArgumentException();
		
		final int similarLockTime;
		try
		{
			similarLockTime = Integer.parseInt(parameter[1]);
		}
		catch(NumberFormatException e)
		{
			throw new IllegalArgumentException();
		}
			
		if(similarLockTime < 0)
			throw new IllegalArgumentException();
		
		settings.filter_lines_similar = similarLockTime;
		
		sender.sendMessage(plugin.prefixed("Timespan successfully set to "+similarLockTime+" seconds"));
		
		if(!settings.save())
			sender.sendMessage(plugin.prefixed("Could not save state in the config file!"));
	}

}

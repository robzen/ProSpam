package de.rob1n.prospam.cmd.specific;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.cmd.Command;
import org.bukkit.command.CommandSender;

public class CommandMaxCaps extends Command
{

	public CommandMaxCaps(ProSpam plugin)
	{
		super(plugin);
	}

	@Override
	public String getName()
	{
		return "max-caps";
	}

	@Override
	public String getDescription()
	{
		return "Maximum percent of caps a word can have";
	}

	@Override
	public String getUsage()
	{
		return "max-caps <percent>";
	}

	@Override
	public void execute(CommandSender sender, String[] parameter) throws IllegalArgumentException
	{
		if(parameter.length <= 1)
			throw new IllegalArgumentException();
		
		final int maxCapsPercent;
		try
		{
			maxCapsPercent = Integer.parseInt(parameter[1]);
		}
		catch(NumberFormatException e)
		{
			throw new IllegalArgumentException();
		}
			
		if(maxCapsPercent < 0 || maxCapsPercent > 100)
			throw new IllegalArgumentException();
		
		settings.filter_caps_max = maxCapsPercent;
		
		sender.sendMessage(plugin.prefixed("Max. caps per word successfully set to "+maxCapsPercent+"%"));
		
		if(!settings.save())
			sender.sendMessage(plugin.prefixed("Could not save state in the config file!"));
	}

}

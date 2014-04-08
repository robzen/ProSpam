package de.rob1n.prospam.cmd;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.exception.IllegalCommandNameException;
import org.bukkit.command.CommandSender;

import java.util.logging.Level;

public class CommandHandler
{
	private final CommandList commandList;
	
	public CommandHandler(ProSpam plugin)
	{
		commandList = new CommandList(plugin);
	}
	
	public boolean execute(CommandSender sender, /*String label, */String[] args)
	{
		Command cmd = null;
		
		try
		{			
			cmd = commandList.get(args[0]);
			cmd.execute(sender, args);
		}
		catch (IllegalCommandNameException e)
		{
			sender.sendMessage(ProSpam.error("Illegal Command"));
			return callHelpCommand(sender);
		}
		catch (Exception e)
		{
			if(cmd != null)
				sender.sendMessage(ProSpam.error("Illegal Arguments. Usage: "+cmd.getUsage()));
			else
				return callHelpCommand(sender);
		}
		
		return true;
	}
	
	private boolean callHelpCommand(final CommandSender sender)
	{
		try
		{
			commandList.get("help").execute(sender, null);
		}
		catch (Exception e)
		{
            ProSpam.log(Level.SEVERE, e.getMessage());
			
			return false;
		}
		
		return true;
	}
	
	public CommandList getCommandList()
	{
		return commandList;
	}
}

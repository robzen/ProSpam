package de.rob1n.prospam.cmd;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.exception.IllegalCommandNameException;
import org.bukkit.command.CommandSender;

public class CommandHandler
{
	private final ProSpam plugin;
	private final CommandList commandList;
	
	public CommandHandler(ProSpam plugin)
	{
		this.plugin = plugin;
		
		commandList = new CommandList(plugin);
	}
	
	public boolean execute(CommandSender sender, String label, String[] args)
	{
		if(!sender.hasPermission("prospam.config"))
			return true;
		
		Command cmd = null;
		
		try
		{			
			cmd = commandList.get(args[0]);
			cmd.execute(sender, args);
		}
		catch (IllegalCommandNameException e)
		{
			sender.sendMessage(plugin.error("Illegal Command"));
			return callHelpCommand(sender);
		}
		catch (Exception e)
		{
			if(cmd != null)
				sender.sendMessage(plugin.error("Illegal Arguments. Usage: "+cmd.getUsage()));
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
			plugin.getLogger().severe(e.getMessage());
			
			return false;
		}
		
		return true;
	}
	
	public CommandList getCommandList()
	{
		return commandList;
	}
}

package de.rob1n.prospam.cmd.specific;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.cmd.Command;
import de.rob1n.prospam.cmd.CommandList;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CommandHelp extends Command
{
	public CommandHelp(ProSpam plugin)
	{
		super(plugin);
	}

	@Override
	public String getName()
	{
		return "help";
	}

	@Override
	public String getDescription()
	{
		return "How to use the plugin";
	}

	@Override
	public String getUsage()
	{
		return "help";
	}

	@Override
	public void execute(CommandSender sender, String[] parameter)
	{
		final CommandList cmdList = plugin.getCommandHandler().getCommandList();
		
		sender.sendMessage(plugin.prefixed("[Version: "+ProSpam.VERSION+" by prodaim] "+"List of Commands"));
		for(Command cmd: cmdList)
		{
			sender.sendMessage(ChatColor.GRAY+"/prospam "+ChatColor.LIGHT_PURPLE+cmd.getUsage());
			sender.sendMessage(ChatColor.ITALIC+cmd.getDescription()+ChatColor.RESET);
			//sender.sendMessage(ChatColor.GRAY+"---------------------------------------------"+ChatColor.RESET);
		}
	}

}

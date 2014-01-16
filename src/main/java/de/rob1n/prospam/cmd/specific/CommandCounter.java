package de.rob1n.prospam.cmd.specific;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.chatter.Chatter;
import de.rob1n.prospam.chatter.ChatterHandler;
import de.rob1n.prospam.cmd.Command;
import de.rob1n.prospam.exception.ChatterNotFoundException;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Date;

public class CommandCounter extends Command
{

	public CommandCounter(ProSpam plugin)
	{
		super(plugin);
	}

	@Override
	public String getName()
	{
		return "counter";
	}

	@Override
	public String getDescription()
	{
		return "Displays spam history of the player. Since last restart";
	}

	@Override
	public String getUsage()
	{
		return "counter <player>";
	}

	@Override
	public void execute(CommandSender sender, String[] parameter) throws IllegalArgumentException
	{
		if(parameter.length <= 1)
			throw new IllegalArgumentException();
		
		try
		{
			final Chatter chatter = ChatterHandler.getChatter(parameter[1], false);
			
			sender.sendMessage(plugin.prefixed("Spam violations of user "+chatter.getName()));
			sender.sendMessage("|  "+ChatColor.GRAY+"Caps: "+ChatColor.RESET+chatter.getSpamCountCaps());
			sender.sendMessage("|  "+ChatColor.GRAY+"Char spam: "+ChatColor.RESET+chatter.getSpamCountChars());
			sender.sendMessage("|  "+ChatColor.GRAY+"Flood: "+ChatColor.RESET+chatter.getSpamCountFlood());
			sender.sendMessage("|  "+ChatColor.GRAY+"Similar messages: "+ChatColor.RESET+chatter.getSpamCountSimilar());
			sender.sendMessage("|  "+ChatColor.GRAY+"Urls: "+ChatColor.RESET+chatter.getSpamCountUrls());
			sender.sendMessage("|  "+ChatColor.GRAY+"Blacklisted words: "+ChatColor.RESET+chatter.getSpamCountBlacklist());
			
			final long spamStarted = chatter.getSpamStarted();
			if(spamStarted != 0)
			{
				int nextReset = (int) ((spamStarted + (settings.trigger_counter_reset*1000*60)) - (new Date()).getTime())/(1000*60);
			
				sender.sendMessage("|  "+ChatColor.DARK_PURPLE+"Reset "+(nextReset > 0 ? "in "+nextReset+" minutes" : "with the next spam"));
			}
		}
		catch (ChatterNotFoundException e1)
		{
			sender.sendMessage(plugin.error("No Data found for player "+parameter[1]));
		}
	}

}

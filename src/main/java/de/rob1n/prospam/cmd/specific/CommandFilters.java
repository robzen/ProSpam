package de.rob1n.prospam.cmd.specific;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.cmd.Command;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CommandFilters extends Command
{

	public CommandFilters(ProSpam plugin)
	{
		super(plugin);
	}

	@Override
	public String getName()
	{
		return "filters";
	}

	@Override
	public String getDescription()
	{
		return "Display filter states";
	}

	@Override
	public String getUsage()
	{
		return "filters";
	}

	@Override
	public void execute(CommandSender sender, String[] parameter) throws IllegalArgumentException
	{
		sender.sendMessage(plugin.prefixed("Filter states"));
		sender.sendMessage("|  Caps Filter"+ChatColor.GRAY+" is "+ChatColor.RESET+(settings.filter_enabled_caps ? ChatColor.DARK_GREEN+"enabled" : ChatColor.DARK_RED+"disabled"));
		sender.sendMessage("|  Char Filter"+ChatColor.GRAY+" is "+ChatColor.RESET+(settings.filter_enabled_chars ? ChatColor.DARK_GREEN+"enabled" : ChatColor.DARK_RED+"disabled"));
		sender.sendMessage("|  Flood Filter"+ChatColor.GRAY+" is "+ChatColor.RESET+(settings.filter_enabled_flood ? ChatColor.DARK_GREEN+"enabled" : ChatColor.DARK_RED+"disabled"));
		sender.sendMessage("|  Similar Filter"+ChatColor.GRAY+" is "+ChatColor.RESET+(settings.filter_enabled_similar ? ChatColor.DARK_GREEN+"enabled" : ChatColor.DARK_RED+"disabled"));
		sender.sendMessage("|  Url Filter"+ChatColor.GRAY+" is "+ChatColor.RESET+(settings.filter_enabled_urls ? ChatColor.DARK_GREEN+"enabled" : ChatColor.DARK_RED+"disabled"));
		sender.sendMessage("|  Blacklist Filter"+ChatColor.GRAY+" is "+ChatColor.RESET+(settings.filter_enabled_blacklist ? ChatColor.DARK_GREEN+"enabled" : ChatColor.DARK_RED+"disabled"));
	}

}

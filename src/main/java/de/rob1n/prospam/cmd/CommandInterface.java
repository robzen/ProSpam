package de.rob1n.prospam.cmd;

import org.bukkit.command.CommandSender;

public interface CommandInterface
{
	public String getName();
	public String getDescription();
	public String[] getArgs();
	public String[] getAliases();
	
	public void execute(CommandSender sender, String[] parameter) throws IllegalArgumentException;
}

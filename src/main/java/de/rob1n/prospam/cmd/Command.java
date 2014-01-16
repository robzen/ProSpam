package de.rob1n.prospam.cmd;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.data.specific.Blacklist;
import de.rob1n.prospam.data.specific.Settings;
import de.rob1n.prospam.data.specific.Whitelist;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class Command implements CommandInterface
{	
	protected final ProSpam plugin;
	
	protected final Settings settings;
	protected final Blacklist blacklist;
	protected final Whitelist whitelist;
	
	public Command(ProSpam plugin)
	{
		this.plugin = plugin;
		
		settings = plugin.getDataHandler().getSettings();
		blacklist = plugin.getDataHandler().getBlacklist();
		whitelist = plugin.getDataHandler().getWhitelist();
	}
	
	protected boolean isPlayer(CommandSender sender)
	{
		return (sender instanceof Player);
	}
	
	public String[] getAliases()
	{
		return new String[0];
	}
}

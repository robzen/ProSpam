package de.rob1n.prospam.data;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.data.specific.Blacklist;
import de.rob1n.prospam.data.specific.Settings;
import de.rob1n.prospam.data.specific.Strings;
import de.rob1n.prospam.data.specific.Whitelist;

public class DataHandler
{
	private final Settings settings;
	private final Strings strings;
	private final Whitelist whitelist;
	private final Blacklist blacklist;
	
	public DataHandler(ProSpam plugin)
	{
		settings = new Settings(plugin, "config.yml");
		strings = new Strings(plugin, "strings.yml");
		whitelist = new Whitelist(plugin, "whitelist.yml");
		blacklist = new Blacklist(plugin, "blacklist.yml");
	}
	
	public Settings getSettings()
	{
		return settings;
	}

	public Strings getStrings()
	{
		return strings;
	}

	public Whitelist getWhitelist()
	{
		return whitelist;
	}

	public Blacklist getBlacklist()
	{
		return blacklist;
	}
	
	public void loadAll()
	{
		settings.load();
		strings.load();
		whitelist.load();
		blacklist.load();
	}
}

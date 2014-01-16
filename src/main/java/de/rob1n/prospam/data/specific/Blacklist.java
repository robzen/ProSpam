package de.rob1n.prospam.data.specific;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.data.ConfigFile;

import java.util.List;

public class Blacklist extends ConfigFile
{
	public static final String OPTION_COVER_CHARS = "cover-chars";
	public static final String OPTION_BLACKLIST = "blacklist";
	
	public String cover_chars;
	public List<String> blacklist;
	
	public Blacklist(ProSpam plugin, String fileName)
	{
		super(plugin, fileName);
	}

	@Override
	protected void loadSettings()
	{
		cover_chars = getConfig().getString(OPTION_COVER_CHARS, "*§$&%#");
		blacklist = getConfig().getStringList(OPTION_BLACKLIST);
	}

	@Override
	protected void saveSettings()
	{
		getConfig().set(OPTION_COVER_CHARS, cover_chars);
		getConfig().set(OPTION_BLACKLIST, blacklist);
	}
}

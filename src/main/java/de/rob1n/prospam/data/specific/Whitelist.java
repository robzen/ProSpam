package de.rob1n.prospam.data.specific;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.data.ConfigFile;

import java.util.List;

public class Whitelist extends ConfigFile
{
	public static final String OPTION_WHITELIST = "whitelist";
	
	public List<String> whitelist;
	
	public Whitelist(ProSpam plugin, String fileName)
	{
		super(plugin, fileName);
	}

	@Override
	protected void loadSettings()
	{
		whitelist = getConfig().getStringList(OPTION_WHITELIST);
	}

	@Override
	protected void saveSettings()
	{
		getConfig().set(OPTION_WHITELIST, whitelist);
	}

}

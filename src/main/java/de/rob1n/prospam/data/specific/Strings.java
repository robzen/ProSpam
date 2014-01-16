package de.rob1n.prospam.data.specific;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.data.ConfigFile;

public class Strings extends ConfigFile
{
	public static final String STRINGS_FILTER_LINES_SIMILAR = "filter-lines-similar";
	public static final String STRINGS_FILTER_LINES_LOCKED = "filter-lines-locked";
	public static final String STRINGS_FILTER_URLS_BLOCKED = "filter-urls-blocked";
	public static final String STRINGS_BLACKKLIST_LINES_IGNORED = "blacklist-lines-ignored";
	
	public String filter_lines_similar;
	public String filter_lines_locked;
	public String filter_urls_blocked;
	public String blacklist_lines_ignored;
	
	public Strings(ProSpam plugin, String fileName)
	{
		super(plugin, fileName);
	}

	@Override
	protected void loadSettings()
	{
		filter_lines_similar = getConfig().getString(STRINGS_FILTER_LINES_SIMILAR, "Your message has been ignored");
		filter_lines_locked = getConfig().getString(STRINGS_FILTER_LINES_LOCKED, "Please wait a moment till you send your next message");
		filter_urls_blocked = getConfig().getString(STRINGS_FILTER_URLS_BLOCKED, "<url blocked>");
		blacklist_lines_ignored = getConfig().getString(STRINGS_BLACKKLIST_LINES_IGNORED, "Your message has been ignored due to a bad word");
	}

	@Override
	protected void saveSettings()
	{
		getConfig().set(STRINGS_FILTER_LINES_SIMILAR, filter_lines_similar);
		getConfig().set(STRINGS_FILTER_LINES_LOCKED, filter_lines_locked);
		getConfig().set(STRINGS_FILTER_URLS_BLOCKED, filter_urls_blocked);
		getConfig().set(STRINGS_BLACKKLIST_LINES_IGNORED, blacklist_lines_ignored);
	}
}

package de.rob1n.prospam.data.specific;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.data.ConfigFile;
import org.bukkit.configuration.MemorySection;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

public class Settings extends ConfigFile
{
	public static final String OPTIONS_ENABLED = "enabled";
	public static final String OPTIONS_FILTER_COMMANDS = "filter-commands";
	public static final String OPTIONS_LOG_SPAM = "log-spam";
	public static final String OPTIONS_FILTER_ENABLED_CAPS = "filter-enabled-caps";
	public static final String OPTIONS_FILTER_ENABLED_CHARS = "filter-enabled-chars";
	public static final String OPTIONS_FILTER_ENABLED_FLOOD = "filter-enabled-flood";
	public static final String OPTIONS_FILTER_ENABLED_SIMILAR = "filter-enabled-similar";
	public static final String OPTIONS_FILTER_ENABLED_URLS = "filter-enabled-urls";
	public static final String OPTIONS_FILTER_ENABLED_BLACKLIST = "filter-enabled-blacklist";
	public static final String OPTIONS_WHITELIST_ENABLED = "whitelist-enabled";
	public static final String OPTIONS_FILTER_CAPS_MAX = "filter-caps-max";
	public static final String OPTIONS_FILTER_FLOOD_LOCK = "filter-flood-lock";
	public static final String OPTIONS_FILTER_LINES_SIMILAR = "filter-lines-similar";
	public static final String OPTIONS_TRIGGER_ENABLED_CAPS = "trigger-enabled-caps";
	public static final String OPTIONS_TRIGGER_ENABLED_CHARS = "trigger-enabled-chars";
	public static final String OPTIONS_TRIGGER_ENABLED_FLOOD = "trigger-enabled-flood";
	public static final String OPTIONS_TRIGGER_ENABLED_SIMILAR = "trigger-enabled-similar";
	public static final String OPTIONS_TRIGGER_ENABLED_URLS = "trigger-enabled-urls";
	public static final String OPTIONS_TRIGGER_ENABLED_BLACKLIST = "trigger-enabled-blacklist";
	public static final String OPTIONS_TRIGGER_COUNTER_RESET = "trigger-counter-reset";
	public static final String OPTIONS_TRIGGER_CAPS = "trigger-caps";
	public static final String OPTIONS_TRIGGER_CHARS = "trigger-chars";
	public static final String OPTIONS_TRIGGER_FLOOD = "trigger-flood";
	public static final String OPTIONS_TRIGGER_SIMILAR = "trigger-similar";
	public static final String OPTIONS_TRIGGER_URLS = "trigger-urls";
	public static final String OPTIONS_TRIGGER_BLACKLIST = "trigger-blacklist";
	
	public boolean enabled;
	public boolean log_spam;
    public List<String> filter_commands;
	public boolean filter_enabled_caps;
	public boolean filter_enabled_chars;
	public boolean filter_enabled_flood;
	public boolean filter_enabled_similar;
	public boolean filter_enabled_urls;
	public boolean filter_enabled_blacklist;
	public boolean whitelist_enabled;
	public int filter_caps_max;
	public int filter_flood_lock;
	public int filter_lines_similar;
	public boolean trigger_enabled_caps;
	public boolean trigger_enabled_chars;
	public boolean trigger_enabled_flood;
	public boolean trigger_enabled_similar;
	public boolean trigger_enabled_urls;
	public boolean trigger_enabled_blacklist;
	public int trigger_counter_reset;
	public HashMap<Integer, List<String>> trigger_caps;
	public HashMap<Integer, List<String>> trigger_chars;
	public HashMap<Integer, List<String>> trigger_flood;
	public HashMap<Integer, List<String>> trigger_similar;
	public HashMap<Integer, List<String>> trigger_urls;
	public HashMap<Integer, List<String>> trigger_blacklist;
	
	public Settings(ProSpam plugin, String fileName)
	{
		super(plugin, fileName);
	}
	
	private HashMap<Integer, List<String>> loadTrigger(final String triggerName)
	{
		final HashMap<Integer, List<String>> map = new HashMap<Integer, List<String>>();
		
		try
		{
			final MemorySection triggerCaps = (MemorySection) getConfig().get(triggerName);
			if(triggerCaps != null)
			{
				final Set<String> occurencesKey = triggerCaps.getKeys(false);

                for (final String occString : occurencesKey)
                {
                    final int occ = Integer.parseInt(occString);
                    // load cmds for specific occurence
                    final List<String> cmdList = getConfig().getStringList(triggerName + "." + occ);

                    map.put(occ, cmdList);
                }
			}
		}
		catch(NumberFormatException e)
		{
            ProSpam.log(Level.SEVERE, "Could not parse "+triggerName);
		}

		return map;
	}

	@Override
	protected void loadSettings()
	{
		enabled = getConfig().getBoolean(OPTIONS_ENABLED, true);

        filter_commands = getConfig().getStringList(OPTIONS_FILTER_COMMANDS);
		
		log_spam = getConfig().getBoolean(OPTIONS_LOG_SPAM, true);
		
		filter_enabled_caps = getConfig().getBoolean(OPTIONS_FILTER_ENABLED_CAPS, true);
		filter_enabled_chars = getConfig().getBoolean(OPTIONS_FILTER_ENABLED_CHARS, true);
		filter_enabled_flood = getConfig().getBoolean(OPTIONS_FILTER_ENABLED_FLOOD, true);
		filter_enabled_similar = getConfig().getBoolean(OPTIONS_FILTER_ENABLED_SIMILAR, true);
		filter_enabled_urls = getConfig().getBoolean(OPTIONS_FILTER_ENABLED_URLS, true);
		filter_enabled_blacklist = getConfig().getBoolean(OPTIONS_FILTER_ENABLED_BLACKLIST, true);
		
		whitelist_enabled = getConfig().getBoolean(OPTIONS_WHITELIST_ENABLED, true);
		filter_caps_max = getConfig().getInt(OPTIONS_FILTER_CAPS_MAX, 61);
		filter_flood_lock = getConfig().getInt(OPTIONS_FILTER_FLOOD_LOCK, 3);
		filter_lines_similar = getConfig().getInt(OPTIONS_FILTER_LINES_SIMILAR, 120);
		
		trigger_enabled_caps = getConfig().getBoolean(OPTIONS_TRIGGER_ENABLED_CAPS, false);
		trigger_enabled_chars = getConfig().getBoolean(OPTIONS_TRIGGER_ENABLED_CHARS, false);
		trigger_enabled_flood = getConfig().getBoolean(OPTIONS_TRIGGER_ENABLED_FLOOD, false);
		trigger_enabled_similar = getConfig().getBoolean(OPTIONS_TRIGGER_ENABLED_SIMILAR, false);
		trigger_enabled_urls = getConfig().getBoolean(OPTIONS_TRIGGER_ENABLED_URLS, false);
		trigger_enabled_blacklist = getConfig().getBoolean(OPTIONS_TRIGGER_ENABLED_BLACKLIST, false);
		
		trigger_counter_reset = getConfig().getInt(OPTIONS_TRIGGER_COUNTER_RESET, 240);
		
		trigger_caps = loadTrigger(OPTIONS_TRIGGER_CAPS);
		trigger_chars = loadTrigger(OPTIONS_TRIGGER_CHARS);
		trigger_flood = loadTrigger(OPTIONS_TRIGGER_FLOOD);
		trigger_similar = loadTrigger(OPTIONS_TRIGGER_SIMILAR);
		trigger_urls = loadTrigger(OPTIONS_TRIGGER_URLS);
		trigger_blacklist = loadTrigger(OPTIONS_TRIGGER_BLACKLIST);
	}
	
	@Override
	protected void saveSettings()
	{
		getConfig().set(OPTIONS_ENABLED, enabled);

        getConfig().set(OPTIONS_FILTER_COMMANDS, filter_commands);
		
		getConfig().set(OPTIONS_LOG_SPAM, log_spam);
		
		getConfig().set(OPTIONS_FILTER_ENABLED_CAPS, filter_enabled_caps);
		getConfig().set(OPTIONS_FILTER_ENABLED_CHARS, filter_enabled_chars);
		getConfig().set(OPTIONS_FILTER_ENABLED_FLOOD, filter_enabled_flood);
		getConfig().set(OPTIONS_FILTER_ENABLED_SIMILAR, filter_enabled_similar);
		getConfig().set(OPTIONS_FILTER_ENABLED_URLS, filter_enabled_urls);
		getConfig().set(OPTIONS_FILTER_ENABLED_BLACKLIST, filter_enabled_blacklist);
		
		getConfig().set(OPTIONS_WHITELIST_ENABLED, whitelist_enabled);
		getConfig().set(OPTIONS_FILTER_CAPS_MAX, filter_caps_max);
		getConfig().set(OPTIONS_FILTER_FLOOD_LOCK, filter_flood_lock);
		getConfig().set(OPTIONS_FILTER_LINES_SIMILAR, filter_lines_similar);
		
		getConfig().set(OPTIONS_TRIGGER_ENABLED_CAPS, trigger_enabled_caps);
		getConfig().set(OPTIONS_TRIGGER_ENABLED_CHARS, trigger_enabled_chars);
		getConfig().set(OPTIONS_TRIGGER_ENABLED_FLOOD, trigger_enabled_flood);
		getConfig().set(OPTIONS_TRIGGER_ENABLED_SIMILAR, trigger_enabled_similar);
		getConfig().set(OPTIONS_TRIGGER_ENABLED_URLS, trigger_enabled_urls);
		getConfig().set(OPTIONS_TRIGGER_ENABLED_BLACKLIST, trigger_enabled_blacklist);
		
		getConfig().set(OPTIONS_TRIGGER_COUNTER_RESET, trigger_counter_reset);
		
		getConfig().set(OPTIONS_TRIGGER_CAPS, trigger_caps);
		getConfig().set(OPTIONS_TRIGGER_CHARS, trigger_chars);
		getConfig().set(OPTIONS_TRIGGER_FLOOD, trigger_flood);
		getConfig().set(OPTIONS_TRIGGER_SIMILAR, trigger_similar);
		getConfig().set(OPTIONS_TRIGGER_URLS, trigger_urls);
		getConfig().set(OPTIONS_TRIGGER_BLACKLIST, trigger_blacklist);
	}
}

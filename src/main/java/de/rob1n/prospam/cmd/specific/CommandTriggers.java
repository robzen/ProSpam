package de.rob1n.prospam.cmd.specific;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.cmd.Command;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class CommandTriggers extends Command
{

	public CommandTriggers(ProSpam plugin)
	{
		super(plugin);
	}

	public String getName()
	{
		return "triggers";
	}

	@Override
	public String getDescription()
	{
		return "Displays the defined spam triggers";
	}

	@Override
    public String[] getArgs()
    {
        return new String[] {"[caps|chars|flood|similar|urls|blacklist]"};
    }

	@Override
	public String[] getAliases()
	{
		return new String[] { "trigger", "t" };
	}

	@Override
	public void execute(CommandSender sender, String[] parameter) throws IllegalArgumentException
	{
		final String trigger = parameter.length <= 1 ? "all" : parameter[1];

		if (!trigger.equalsIgnoreCase("all") && !trigger.equalsIgnoreCase("caps") && !trigger.equalsIgnoreCase("chars") && !trigger.equalsIgnoreCase("flood")
				&& !trigger.equalsIgnoreCase("similar") && !trigger.equalsIgnoreCase("urls") && !trigger.equalsIgnoreCase("blacklist"))
			throw new IllegalArgumentException();

		sender.sendMessage(ProSpam.prefixed("Displaying triggers"));

		if (trigger.equalsIgnoreCase("caps") || trigger.equals("all"))
		{
			sender.sendMessage(ChatColor.GOLD + "Caps triggers" + ChatColor.RESET + " ["
					+ (settings.trigger_enabled_caps ? ChatColor.DARK_GREEN + "enabled" : ChatColor.DARK_RED + "disabled") + ChatColor.RESET + "]");
			displayTrigger(sender, settings.trigger_caps);
		}
		if (trigger.equalsIgnoreCase("chars") || trigger.equals("all"))
		{
			sender.sendMessage(ChatColor.GOLD + "Chars triggers" + ChatColor.RESET + " ["
					+ (settings.trigger_enabled_chars ? ChatColor.DARK_GREEN + "enabled" : ChatColor.DARK_RED + "disabled") + ChatColor.RESET + "]");
			displayTrigger(sender, settings.trigger_chars);
		}
		if (trigger.equalsIgnoreCase("flood") || trigger.equals("all"))
		{
			sender.sendMessage(ChatColor.GOLD + "Flood triggers" + ChatColor.RESET + " ["
					+ (settings.trigger_enabled_flood ? ChatColor.DARK_GREEN + "enabled" : ChatColor.DARK_RED + "disabled") + ChatColor.RESET + "]");
			displayTrigger(sender, settings.trigger_flood);
		}
		if (trigger.equalsIgnoreCase("similar") || trigger.equals("all"))
		{
			sender.sendMessage(ChatColor.GOLD + "Similar triggers" + ChatColor.RESET + " ["
					+ (settings.trigger_enabled_similar ? ChatColor.DARK_GREEN + "enabled" : ChatColor.DARK_RED + "disabled") + ChatColor.RESET + "]");
			displayTrigger(sender, settings.trigger_similar);
		}
		if (trigger.equalsIgnoreCase("urls") || trigger.equals("all"))
		{
			sender.sendMessage(ChatColor.GOLD + "URL triggers" + ChatColor.RESET + " ["
					+ (settings.trigger_enabled_urls ? ChatColor.DARK_GREEN + "enabled" : ChatColor.DARK_RED + "disabled") + ChatColor.RESET + "]");
			displayTrigger(sender, settings.trigger_urls);
		}
		if (trigger.equalsIgnoreCase("blacklist") || trigger.equals("all"))
		{
			sender.sendMessage(ChatColor.GOLD + "Blacklist triggers" + ChatColor.RESET + " ["
					+ (settings.trigger_enabled_blacklist ? ChatColor.DARK_GREEN + "enabled" : ChatColor.DARK_RED + "disabled") + ChatColor.RESET + "]");
			displayTrigger(sender, settings.trigger_blacklist);
		}
	}

	private void displayTrigger(final CommandSender sender, final HashMap<Integer, List<String>> trigger)
	{

        for (Entry<Integer, List<String>> pairs : trigger.entrySet())
        {
            final int spamOccurence = pairs.getKey();

            for (String cmd : pairs.getValue())
            {
                sender.sendMessage("|  " + ChatColor.GRAY + "[" + spamOccurence + "] " + ChatColor.RESET + cmd);
            }
        }
	}

}

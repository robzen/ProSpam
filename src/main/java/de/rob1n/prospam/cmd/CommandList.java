package de.rob1n.prospam.cmd;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.cmd.specific.*;
import de.rob1n.prospam.exception.IllegalCommandNameException;

import java.util.ArrayList;

public class CommandList extends ArrayList<Command>
{
	private static final long serialVersionUID = 6333444737973299230L;
	
	public CommandList(ProSpam plugin)
	{
		add(new CommandHelp(plugin));
		add(new CommandEnable(plugin));
		add(new CommandDisable(plugin));
		add(new CommandReload(plugin));
		add(new CommandFilterEnable(plugin));
		add(new CommandFilterDisable(plugin));
		add(new CommandFilters(plugin));
		add(new CommandCounter(plugin));
		add(new CommandWhitelistEnable(plugin));
		add(new CommandWhitelistDisable(plugin));
		add(new CommandMaxCaps(plugin));
		add(new CommandLinesLocked(plugin));
		add(new CommandLinesSimilar(plugin));
		add(new CommandTriggerEnable(plugin));
		add(new CommandTriggerDisable(plugin));
		add(new CommandTriggerCounterReset(plugin));
		add(new CommandTriggers(plugin));
		add(new CommandTriggerCaps(plugin));
		add(new CommandTriggerChars(plugin));
		add(new CommandTriggerFlood(plugin));
		add(new CommandTriggerSimilar(plugin));
		add(new CommandTriggerUrls(plugin));
		add(new CommandTriggerBlacklist(plugin));
	}
	
	/**
	 * @param cmdName the cmd name
	 * @return if it exists, the command with the specific name.
	 * @throws IllegalCommandNameException
	 */
	public Command get(String cmdName) throws IllegalCommandNameException
	{
		Command cmd;

        for (Command command : this)
        {
            cmd = command;

            if (cmd.getName().equalsIgnoreCase(cmdName)) return cmd;

            for (String alias : cmd.getAliases())
            {
                if (alias.equalsIgnoreCase(cmdName)) return cmd;
            }
        }
		
		throw new IllegalCommandNameException();
	}
}

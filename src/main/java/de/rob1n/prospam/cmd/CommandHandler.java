package de.rob1n.prospam.cmd;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.exception.IllegalCommandNameException;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class CommandHandler implements TabCompleter
{
	private final CommandList commandList;
	
	public CommandHandler(ProSpam plugin)
	{
		commandList = new CommandList(plugin);
	}
	
	public boolean execute(CommandSender sender, /*String label, */String[] args)
	{
		Command cmd = null;
		
		try
		{			
			cmd = commandList.get(args[0]);
			cmd.execute(sender, args);
		}
		catch (IllegalCommandNameException e)
		{
			sender.sendMessage(ProSpam.error("Illegal Command"));
			return callHelpCommand(sender);
		}
		catch (Exception e)
		{
			if(cmd != null)
				sender.sendMessage(ProSpam.error("Illegal Arguments. Needed: "+ StringUtils.join(cmd.getArgs(), " ")));
			else
				return callHelpCommand(sender);
		}
		
		return true;
	}
	
	private boolean callHelpCommand(final CommandSender sender)
	{
		try
		{
			commandList.get("help").execute(sender, null);
		}
		catch (Exception e)
		{
            ProSpam.log(Level.SEVERE, e.getMessage());
			
			return false;
		}
		
		return true;
	}
	
	public CommandList getCommandList()
	{
		return commandList;
	}

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String cmdName /*prospam*/, String[] strings)
    {
        List<String> completions = new ArrayList<String>();
        String toComplete = strings[0];

        if(toComplete.isEmpty())
        { //just /prospam
            for (Command cmd : commandList)
            {
                completions.add(cmd.getName());
            }
        }
        else
        {
            if(strings.length < 2)
            {
                //list all commands starting with toComplete
                for (Command cmd : commandList)
                {
                    if(cmd.getName().startsWith(toComplete))
                    {
                        completions.add(cmd.getName());
                    }

                    for(String alias : cmd.getAliases())
                    {
                        if(alias.startsWith(toComplete))
                        {
                            completions.add(alias);
                        }
                    }
                }
            }

            try
            { //toComplete is a full command
                if(toComplete.isEmpty() && strings.length >= 2)
                {
                    toComplete = strings[strings.length - 2];
                }

                Command lastCmd = commandList.get(toComplete);

                if(strings.length >= 2)
                {
                    if(lastCmd.getArgs().length > strings.length - 2)
                    {
                        String cmdArg = lastCmd.getArgs()[strings.length - 2];

                        if(!cmdArg.isEmpty())
                        {
                            //completions.add(cmdArg);
                            commandSender.sendMessage(ProSpam.prefixed("Acceptable Args: "+cmdArg));

                            if(cmdArg.equalsIgnoreCase("<player>"))
                            {
                                //return null so take onlinePlayers as completion
                                return null;
                            }
                            /*try
                            {
                                //strip <> or []
                                String innerArgs = cmdArg.substring(1, cmdArg.length() - 1);

                                if(innerArgs.contains("|"))
                                {
                                    completions.addAll(Arrays.asList(innerArgs.split("|")));
                                }
                                else
                                {
                                    completions.add(innerArgs);
                                }
                            }
                            catch (IndexOutOfBoundsException ignored)
                            {
                                //no args defined
                                //nothing to do here
                            }*/
                        }
                    }
                }
            }
            catch (IllegalCommandNameException ignored)
            {
                //toComplete is not known as command
                //do nothing special here
            }
        }

        return completions;
    }
}

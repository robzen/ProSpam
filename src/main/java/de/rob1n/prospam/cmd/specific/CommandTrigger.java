package de.rob1n.prospam.cmd.specific;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.cmd.Command;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public abstract class CommandTrigger extends Command
{

	public CommandTrigger(ProSpam plugin)
	{
		super(plugin);
	}
	
	public abstract void saveInSettings(final int vNumber, final List<String> cmds);

	@Override
	public void execute(final CommandSender sender, final String[] parameter) throws IllegalArgumentException
	{
		if(parameter.length <= 1)
			throw new IllegalArgumentException();
		
		int vNumber = 0;
		List<String> cmds;

        try
		{
			vNumber = Integer.parseInt(parameter[1]);
			parameter[1] = ""; //strip number
		}
		catch(NumberFormatException ignored) { }
			
		if(vNumber < 0)
			throw new IllegalArgumentException();
		
		parameter[0] = ""; //strip "trigger-caps"
		cmds = getTriggerCmds(parameter);
		
		saveInSettings(vNumber, cmds);
		
		if(cmds.size() > 0)
			sender.sendMessage(plugin.prefixed("Trigger successfully modified"));
		else
			sender.sendMessage(plugin.prefixed("Trigger successfully removed"));
		
		if(!settings.save())
			sender.sendMessage(plugin.prefixed("Could not save state in the config file!"));
	}
	
	private List<String> getTriggerCmds(final String[] params)
	{		
		final String paramString = StringUtils.join(params, " ");
		final String[] cmds = paramString.split(",");
		
		List<String> cmdList = new ArrayList<String>();
		
		if(paramString.trim().isEmpty())
			return cmdList;
		
		for(String cmd : cmds)
		{
			cmd = cmd.trim();
			
			if(cmd.length() <= 0)
				throw new IllegalArgumentException();
			
			if(cmd.charAt(0) != '/')
				cmdList.add("/"+cmd);
			else
				cmdList.add(cmd);
		}
		
		return cmdList;
	}

}

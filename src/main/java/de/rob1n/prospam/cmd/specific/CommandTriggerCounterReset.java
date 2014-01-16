package de.rob1n.prospam.cmd.specific;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.chatter.Chatter;
import de.rob1n.prospam.chatter.ChatterHandler;
import de.rob1n.prospam.cmd.Command;
import de.rob1n.prospam.exception.ChatterNotFoundException;
import org.bukkit.command.CommandSender;

import java.util.List;

public class CommandTriggerCounterReset extends Command
{

	public CommandTriggerCounterReset(ProSpam plugin)
	{
		super(plugin);
	}

	@Override
	public String getName()
	{
		return "trigger-counter-reset";
	}

	@Override
	public String getDescription()
	{
		return "Reset the violation counter immediately/every x minutes (0=never)";
	}

	@Override
	public String getUsage()
	{
		return "trigger-counter-reset [minutes] [player]";
	}

	@Override
	public void execute(CommandSender sender, String[] parameter) throws IllegalArgumentException
	{
		if (parameter.length <= 1)
		{
			// reset spam violations of all players
			final List<Chatter> chatters = plugin.getChatterHandler().getChatters();
			for (Chatter chatter : chatters)
			{
				chatter.resetSpamViolations();
			}

			sender.sendMessage(plugin.prefixed("Spam violation counter successfully resetted"));
		}
		else
		{
			if (parameter.length == 2)
			{
				int resetTime;
				try
				{
					resetTime = Integer.parseInt(parameter[1]);

					if (resetTime < 0)
						throw new IllegalArgumentException();

					settings.trigger_counter_reset = resetTime;

					if (resetTime != 0)
						sender.sendMessage(plugin.prefixed("Reset timer successfully set to " + resetTime + " minutes"));
					else
						sender.sendMessage(plugin.prefixed("Timed trigger counter reset disabled"));

					if (!settings.save())
						sender.sendMessage(plugin.prefixed("Could not save state in the config file!"));
				}
				catch (NumberFormatException e)
				{
					try
					{
						final Chatter chatter = ChatterHandler.getChatter(parameter[1], false);
						chatter.resetSpamViolations();

						sender.sendMessage(plugin.prefixed("Spam violation counter successfully resetted for Player " + chatter.getName()));
					}
					catch (ChatterNotFoundException e1)
					{
						sender.sendMessage(plugin.error("Player not found"));
					}
				}

			}
			else
				throw new IllegalArgumentException();
		}
	}
}

package de.rob1n.prospam.cmd.specific;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.chatter.Chatter;
import de.rob1n.prospam.chatter.ChatterHandler;
import de.rob1n.prospam.cmd.Command;
import de.rob1n.prospam.exception.PlayerNotOnlineException;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Set;

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

			sender.sendMessage(ProSpam.prefixed("Spam violation counter successfully resetted"));
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

					if (!settings.save())
                    {
                        sender.sendMessage(ProSpam.prefixed("Could not save state in the config file!"));
                    }
                    else
                    {
                        if (resetTime != 0)
                            sender.sendMessage(ProSpam.prefixed("Reset timer successfully set to " + resetTime + " minutes"));
                        else
                            sender.sendMessage(ProSpam.prefixed("Timed trigger counter reset disabled"));
                    }
				}
				catch (NumberFormatException e)
				{
                    final Set<Chatter> chatters = ChatterHandler.getChatter(parameter[1]);

                    for (Chatter chatter : chatters)
                    {
                        chatter.resetSpamViolations();

                        try
                        {
                            //noinspection deprecation
                            String name = chatter.getPlayer().getName();
                            sender.sendMessage(ProSpam.prefixed("Spam violation counter successfully resetted for " + name));
                        }
                        catch (PlayerNotOnlineException ignored)
                        {
                            sender.sendMessage(ProSpam.prefixed("Spam violation counter successfully resetted for Player UUID: " + chatter.getUUID()));
                        }
                    }
				}
			}
			else
				throw new IllegalArgumentException();
		}
	}
}

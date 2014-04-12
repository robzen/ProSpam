package de.rob1n.prospam.cmd.specific;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.chatter.Chatter;
import de.rob1n.prospam.chatter.ChatterHandler;
import de.rob1n.prospam.cmd.Command;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Set;
import java.util.UUID;

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
    public String[] getArgs()
    {
        return new String[] {"[minutes]", "[player]"};
    }

    public String[] getAliases()
    {
        return new String[] { "reset", "counter-reset", "stats-reset", "reset-stats" };
    }

	@Override
	public void execute(final CommandSender sender, final String[] parameter) throws IllegalArgumentException
	{
		if (parameter.length <= 1)
		{
			// reset spam violations of all players
			final Set<Chatter> chatters = plugin.getChatterHandler().getChatters();
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
                    //reset spam stats for a player
                    Bukkit.getScheduler().runTaskAsynchronously(plugin, new BukkitRunnable()
                    {
                        @Override
                        public void run()
                        {
                            //noinspection deprecation
                            UUID uuid = Bukkit.getServer().getOfflinePlayer(parameter[1]).getUniqueId();

                            if(uuid != null)
                            {
                                Chatter chatter = ChatterHandler.getChatter(uuid);
                                //noinspection deprecation
                                String name = Bukkit.getOfflinePlayer(uuid).getName();

                                chatter.resetSpamViolations();
                                sender.sendMessage(ProSpam.prefixed("Spam violation counter successfully resetted for " + name));
                            }
                            else
                            {
                                sender.sendMessage(ProSpam.error("No stats for this player"));
                            }
                        }
                    });
				}
			}
			else
				throw new IllegalArgumentException();
		}
	}
}

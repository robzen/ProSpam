package de.rob1n.prospam.cmd.specific;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.cmd.Command;
import de.rob1n.prospam.cmd.CommandWithGui;
import de.rob1n.prospam.gui.Item;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class CommandLinesLocked extends Command implements CommandWithGui
{

	public CommandLinesLocked(ProSpam plugin)
	{
		super(plugin);
	}

	@Override
	public String getName()
	{
		return "lines-locked";
	}

	@Override
	public String getDescription()
	{
		return "Timespan players have to wait between their messages";
	}

	@Override
	public String getUsage()
	{
		return "lines-locked <seconds>";
	}

	@Override
	public void execute(CommandSender sender, String[] parameter) throws IllegalArgumentException
	{
        if(isPlayer(sender))
        {
            Player player = (Player)sender;

            showGui(player);
        }
        else
        {
            if(parameter.length <= 1)
                throw new IllegalArgumentException();

            final int lockTime;
            try
            {
                lockTime = Integer.parseInt(parameter[1]);
            }
            catch(NumberFormatException e)
            {
                throw new IllegalArgumentException();
            }

            if(lockTime < 0)
                throw new IllegalArgumentException();

            settings.filter_flood_lock = lockTime;

            if(!settings.save())
            {
                sender.sendMessage(ProSpam.prefixed("Could not save state in the config file!"));
            }
            else
            {
                sender.sendMessage(ProSpam.prefixed("Timespan successfully set to "+lockTime+" seconds"));
            }
        }
	}

    @Override
    public void showGui(Player player)
    {
        Set<Item> items = new HashSet<Item>();

        //time items
        for (int i = 0; i <= 8; i++)
        {
            final int secs = i * i;
            items.add(new Item(i, new ItemStack(Material.SLIME_BALL), secs+" seconds", "Wait period between msgs", new Item.ClickAction()
            {
                @Override
                public void onClick(Player player)
                {
                    settings.filter_flood_lock = secs;

                    if(!settings.save())
                    {
                        player.sendMessage(ProSpam.prefixed("Could not save state in the config file!"));
                    }
                    else
                    {
                        player.sendMessage(ProSpam.prefixed("Timespan successfully set to "+secs+" seconds"));
                    }
                }
            }));
        }

        //spacer items
        for(int i=9; i<=17; i++)
        {
            items.add(Item.getSpacerItem(i));
        }

        //back item
        items.add(new Item(18, new ItemStack(Material.ENDER_PORTAL), "Back", "Back to the filter settings", new Item.ClickAction()
        {
            @Override
            public void onClick(Player player)
            {
                CommandFilters commandFilters = new CommandFilters(plugin);
                commandFilters.execute(player, new String[0]);
            }
        }));

        plugin.getGuiManager().openInventoryGui(player, "Flood settings", items);
    }
}

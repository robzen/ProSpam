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
    public String[] getArgs()
    {
        return new String[] {"<seconds>"};
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
        int valueFilterFloodLock = settings.filter_flood_lock;

        //time items
        for (int i = 0; i <= 8; i++)
        {
            final int secs = i * i;
            ItemStack itemStack;

            if(secs == valueFilterFloodLock) { itemStack = new ItemStack(Material.ENDER_PEARL); }
            else { itemStack = new ItemStack(Material.SLIME_BALL); }

            items.add(new Item(i, itemStack, secs+" seconds", "Wait period between msgs", new Item.ClickAction()
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
                        //show window with new settings
                        showGui(player);

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
                commandFilters.showGui(player);
            }
        }));

        plugin.getGuiManager().openInventoryGui(player, "Flood settings", items);
    }
}

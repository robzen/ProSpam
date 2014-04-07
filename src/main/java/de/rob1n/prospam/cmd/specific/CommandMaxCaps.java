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

public class CommandMaxCaps extends Command implements CommandWithGui
{

	public CommandMaxCaps(ProSpam plugin)
	{
		super(plugin);
	}

	@Override
	public String getName()
	{
		return "max-caps";
	}

	@Override
	public String getDescription()
	{
		return "Maximum percent of caps a word can have";
	}

	@Override
	public String getUsage()
	{
		return "max-caps <percent>";
	}

	@Override
	public void execute(CommandSender sender, String[] parameter) throws IllegalArgumentException
	{
        if(isPlayer(sender))
        { //show inventory gui menu
            Player player = (Player)sender;

            showGui(player);
        }
        else
        { //the casual way
            if(parameter.length <= 1)
                throw new IllegalArgumentException();

            final int maxCapsPercent;
            try
            {
                maxCapsPercent = Integer.parseInt(parameter[1]);
            }
            catch(NumberFormatException e)
            {
                throw new IllegalArgumentException();
            }

            if(maxCapsPercent < 0 || maxCapsPercent > 100)
                throw new IllegalArgumentException();

            settings.filter_caps_max = maxCapsPercent;

            sender.sendMessage(ProSpam.prefixed("Max. caps per word successfully set to "+maxCapsPercent+"%"));

            if(!settings.save())
                sender.sendMessage(ProSpam.prefixed("Could not save state in the config file!"));
        }
	}

    public void showGui(Player player)
    {
        Set<Item> items = new HashSet<Item>();

        //percent items
        for(int i = 0; i <= 8; i++)
        {
            final int percent = (int)Math.floor(i*(100f/8f));
            items.add(new Item(i, new ItemStack(Material.SLIME_BALL), percent+"%", "Max. "+percent+"% caps till lowercase", new Item.ClickAction()
            {
                @Override
                public void onClick(Player player)
                {
                    settings.filter_caps_max = percent;

                    if(!settings.save())
                    {
                        player.sendMessage(ProSpam.prefixed("Could not save state in the config file!"));
                    }
                    else
                    {
                        player.sendMessage(ProSpam.prefixed("Max. caps per word successfully set to "+percent+"%"));
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

        plugin.getGuiManager().openInventoryGui(player, "Max. caps till lowercase", items);
    }
}

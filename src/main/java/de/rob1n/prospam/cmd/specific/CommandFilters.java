package de.rob1n.prospam.cmd.specific;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.cmd.Command;
import de.rob1n.prospam.cmd.CommandWithGui;
import de.rob1n.prospam.gui.Item;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class CommandFilters extends Command implements CommandWithGui
{

	public CommandFilters(ProSpam plugin)
	{
		super(plugin);
	}

	@Override
	public String getName()
	{
		return "filters";
	}

	@Override
	public String getDescription()
	{
		return "Display filter states";
	}

	@Override
	public String getUsage()
	{
		return "filters";
	}

	@Override
	public void execute(CommandSender sender, String[] parameter) throws IllegalArgumentException
	{
        if(isPlayer(sender))
        { //open inventory gui
            Player player = (Player)sender;

            showGui(player);
        }
        else
        { //show the conventional way
            sender.sendMessage(ProSpam.prefixed("Filter states"));
            sender.sendMessage("|  Caps Filter"+ChatColor.GRAY+" is "+ChatColor.RESET+(settings.filter_enabled_caps ? ChatColor.DARK_GREEN+"enabled" : ChatColor.DARK_RED+"disabled"));
            sender.sendMessage("|  Char Filter"+ChatColor.GRAY+" is "+ChatColor.RESET+(settings.filter_enabled_chars ? ChatColor.DARK_GREEN+"enabled" : ChatColor.DARK_RED+"disabled"));
            sender.sendMessage("|  Flood Filter"+ChatColor.GRAY+" is "+ChatColor.RESET+(settings.filter_enabled_flood ? ChatColor.DARK_GREEN+"enabled" : ChatColor.DARK_RED+"disabled"));
            sender.sendMessage("|  Similar Filter"+ChatColor.GRAY+" is "+ChatColor.RESET+(settings.filter_enabled_similar ? ChatColor.DARK_GREEN+"enabled" : ChatColor.DARK_RED+"disabled"));
            sender.sendMessage("|  Url Filter"+ChatColor.GRAY+" is "+ChatColor.RESET+(settings.filter_enabled_urls ? ChatColor.DARK_GREEN+"enabled" : ChatColor.DARK_RED+"disabled"));
            sender.sendMessage("|  Blacklist Filter"+ChatColor.GRAY+" is "+ChatColor.RESET+(settings.filter_enabled_blacklist ? ChatColor.DARK_GREEN+"enabled" : ChatColor.DARK_RED+"disabled"));
        }
	}

    @SuppressWarnings("deprecation")
    public void showGui(Player player)
    {
        Set<Item> items = new HashSet<Item>();
        final CommandFilterEnable commandFilterEnable = new CommandFilterEnable(plugin);
        final CommandFilterDisable commandFilterDisable = new CommandFilterDisable(plugin);
        final CommandTriggerEnable commandTriggerEnable = new CommandTriggerEnable(plugin);
        final CommandTriggerDisable commandTriggerDisable = new CommandTriggerDisable(plugin);

        //caps filter
        if(settings.filter_enabled_caps)
        {
            items.add(new Item(0, new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.GREEN.getData()), "Caps filter", "Click to disable", new Item.ClickAction()
            {
                @Override
                public void onClick(Player player)
                {
                    commandFilterDisable.execute(player, new String[]{"", "caps"});

                    //reopen with new settings
                    showGui(player);
                }
            }));
        }
        else
        {
            items.add(new Item(0, new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.RED.getData()), "Caps filter", "Click to enable", new Item.ClickAction()
            {
                @Override
                public void onClick(Player player)
                {
                    commandFilterEnable.execute(player, new String[]{"", "caps"});

                    //reopen with new settings
                    showGui(player);
                }
            }));
        }

        //chars filter
        if(settings.filter_enabled_chars)
        {
            items.add(new Item(1, new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.GREEN.getData()), "Chars filter", "Click to disable", new Item.ClickAction()
            {
                @Override
                public void onClick(Player player)
                {
                    commandFilterDisable.execute(player, new String[]{"", "chars"});

                    //reopen with new settings
                    showGui(player);
                }
            }));
        }
        else
        {
            items.add(new Item(1, new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.RED.getData()), "Chars filter", "Click to enable", new Item.ClickAction()
            {
                @Override
                public void onClick(Player player)
                {
                    commandFilterEnable.execute(player, new String[]{"", "chars"});

                    //reopen with new settings
                    showGui(player);
                }
            }));
        }

        items.add(Item.getSpacerItem(2));

        //flood filter
        if(settings.filter_enabled_flood)
        {
            items.add(new Item(3, new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.GREEN.getData()), "Flood filter", "Click to disable", new Item.ClickAction()
            {
                @Override
                public void onClick(Player player)
                {
                    commandFilterDisable.execute(player, new String[]{"", "flood"});

                    //reopen with new settings
                    showGui(player);
                }
            }));
        }
        else
        {
            items.add(new Item(3, new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.RED.getData()), "Flood filter", "Click to enable", new Item.ClickAction()
            {
                @Override
                public void onClick(Player player)
                {
                    commandFilterEnable.execute(player, new String[]{"", "flood"});

                    //reopen with new settings
                    showGui(player);
                }
            }));
        }

        //similar filter
        if(settings.filter_enabled_similar)
        {
            items.add(new Item(4, new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.GREEN.getData()), "Similar filter", "Click to disable", new Item.ClickAction()
            {
                @Override
                public void onClick(Player player)
                {
                    commandFilterDisable.execute(player, new String[]{"", "similar"});

                    //reopen with new settings
                    showGui(player);
                }
            }));
        }
        else
        {
            items.add(new Item(4, new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.RED.getData()), "Similar filter", "Click to enable", new Item.ClickAction()
            {
                @Override
                public void onClick(Player player)
                {
                    commandFilterEnable.execute(player, new String[]{"", "similar"});

                    //reopen with new settings
                    showGui(player);
                }
            }));
        }

        items.add(Item.getSpacerItem(5));
        items.add(Item.getSpacerItem(6));

        //url filter
        if(settings.filter_enabled_urls)
        {
            items.add(new Item(7, new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.GREEN.getData()), "URL filter", "Click to disable", new Item.ClickAction()
            {
                @Override
                public void onClick(Player player)
                {
                    commandFilterDisable.execute(player, new String[]{"", "urls"});

                    //reopen with new settings
                    showGui(player);
                }
            }));
        }
        else
        {
            items.add(new Item(7, new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.RED.getData()), "URL filter", "Click to enable", new Item.ClickAction()
            {
                @Override
                public void onClick(Player player)
                {
                    commandFilterEnable.execute(player, new String[]{"", "urls"});

                    //reopen with new settings
                    showGui(player);
                }
            }));
        }

        //blacklist filter
        if(settings.filter_enabled_blacklist)
        {
            items.add(new Item(8, new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.GREEN.getData()), "Blacklist filter", "Click to disable", new Item.ClickAction()
            {
                @Override
                public void onClick(Player player)
                {
                    commandFilterDisable.execute(player, new String[]{"", "blacklist"});

                    //reopen with new settings
                    showGui(player);
                }
            }));
        }
        else
        {
            items.add(new Item(8, new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.RED.getData()), "Blacklist filter", "Click to enable", new Item.ClickAction()
            {
                @Override
                public void onClick(Player player)
                {
                    commandFilterEnable.execute(player, new String[]{"", "blacklist"});

                    //reopen with new settings
                    showGui(player);
                }
            }));
        }

        //max caps settings
        items.add(new Item(9, new ItemStack(Material.PAPER), "Max. % caps per word", "if more % -> toLowercase", new Item.ClickAction()
        {
            @Override
            public void onClick(Player player)
            {
                CommandMaxCaps commandMaxCaps = new CommandMaxCaps(plugin);
                commandMaxCaps.execute(player, new String[0]);
            }
        }));

        items.add(Item.getSpacerItem(11));

        //flood settings
        items.add(new Item(12, new ItemStack(Material.PAPER), "Min. time between messages", "time < min, text gets ignored", new Item.ClickAction()
        {
            @Override
            public void onClick(Player player)
            {
                CommandLinesLocked commandLinesLocked = new CommandLinesLocked(plugin);
                commandLinesLocked.execute(player, new String[0]);
            }
        }));

        //similar settings
        items.add(new Item(13, new ItemStack(Material.PAPER), "Time between similar messages", "if similar, text gets ignored", new Item.ClickAction()
        {
            @Override
            public void onClick(Player player)
            {
                CommandLinesSimilar commandLinesSimilar = new CommandLinesSimilar(plugin);
                commandLinesSimilar.execute(player, new String[0]);
            }
        }));

        items.add(Item.getSpacerItem(14));
        items.add(Item.getSpacerItem(15));

        //caps actions enable/disable
        if(settings.trigger_enabled_caps)
        {
            items.add(new Item(18, new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.GREEN.getData()), "Caps actions", "Click to disable", new Item.ClickAction()
            {
                @Override
                public void onClick(Player player)
                {
                    commandTriggerDisable.execute(player, new String[]{"", "caps"});

                    //reopen with new settings
                    showGui(player);
                }
            }));
        }
        else
        {
            items.add(new Item(18, new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.RED.getData()), "Caps actions", "Click to enable", new Item.ClickAction()
            {
                @Override
                public void onClick(Player player)
                {
                    commandTriggerEnable.execute(player, new String[]{"", "caps"});

                    //reopen with new settings
                    showGui(player);
                }
            }));
        }

        //char actions enable/disable
        if(settings.trigger_enabled_chars)
        {
            items.add(new Item(19, new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.GREEN.getData()), "Chars actions", "Click to disable", new Item.ClickAction()
            {
                @Override
                public void onClick(Player player)
                {
                    commandTriggerDisable.execute(player, new String[]{"", "chars"});

                    //reopen with new settings
                    showGui(player);
                }
            }));
        }
        else
        {
            items.add(new Item(19, new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.RED.getData()), "Chars actions", "Click to enable", new Item.ClickAction()
            {
                @Override
                public void onClick(Player player)
                {
                    commandTriggerEnable.execute(player, new String[]{"", "chars"});

                    //reopen with new settings
                    showGui(player);
                }
            }));
        }

        items.add(Item.getSpacerItem(20));

        //flood actions enable/disable
        if(settings.trigger_enabled_flood)
        {
            items.add(new Item(21, new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.GREEN.getData()), "Flood actions", "Click to disable", new Item.ClickAction()
            {
                @Override
                public void onClick(Player player)
                {
                    commandTriggerDisable.execute(player, new String[]{"", "flood"});

                    //reopen with new settings
                    showGui(player);
                }
            }));
        }
        else
        {
            items.add(new Item(21, new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.RED.getData()), "Flood actions", "Click to enable", new Item.ClickAction()
            {
                @Override
                public void onClick(Player player)
                {
                    commandTriggerEnable.execute(player, new String[]{"", "flood"});

                    //reopen with new settings
                    showGui(player);
                }
            }));
        }

        //similar actions enable/disable
        if(settings.trigger_enabled_similar)
        {
            items.add(new Item(22, new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.GREEN.getData()), "Similar txt actions", "Click to disable", new Item.ClickAction()
            {
                @Override
                public void onClick(Player player)
                {
                    commandTriggerDisable.execute(player, new String[]{"", "similar"});

                    //reopen with new settings
                    showGui(player);
                }
            }));
        }
        else
        {
            items.add(new Item(22, new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.RED.getData()), "Similar txt actions", "Click to enable", new Item.ClickAction()
            {
                @Override
                public void onClick(Player player)
                {
                    commandTriggerEnable.execute(player, new String[]{"", "similar"});

                    //reopen with new settings
                    showGui(player);
                }
            }));
        }

        items.add(Item.getSpacerItem(23));
        items.add(Item.getSpacerItem(24));

        //url actions enable/disable
        if(settings.trigger_enabled_urls)
        {
            items.add(new Item(25, new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.GREEN.getData()), "URL actions", "Click to disable", new Item.ClickAction()
            {
                @Override
                public void onClick(Player player)
                {
                    commandTriggerDisable.execute(player, new String[]{"", "urls"});

                    //reopen with new settings
                    showGui(player);
                }
            }));
        }
        else
        {
            items.add(new Item(25, new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.RED.getData()), "URL actions", "Click to enable", new Item.ClickAction()
            {
                @Override
                public void onClick(Player player)
                {
                    commandTriggerEnable.execute(player, new String[]{"", "urls"});

                    //reopen with new settings
                    showGui(player);
                }
            }));
        }

        //blacklist actions enable/disable
        if(settings.trigger_enabled_blacklist)
        {
            items.add(new Item(26, new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.GREEN.getData()), "Blacklist actions", "Click to disable", new Item.ClickAction()
            {
                @Override
                public void onClick(Player player)
                {
                    commandTriggerDisable.execute(player, new String[]{"", "blacklist"});

                    //reopen with new settings
                    showGui(player);
                }
            }));
        }
        else
        {
            items.add(new Item(26, new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.RED.getData()), "Blacklist actions", "Click to enable", new Item.ClickAction()
            {
                @Override
                public void onClick(Player player)
                {
                    commandTriggerEnable.execute(player, new String[]{"", "blacklist"});

                    //reopen with new settings
                    showGui(player);
                }
            }));
        }

        //caps actions
        items.add(new Item(27, new ItemStack(Material.WRITTEN_BOOK), "Actions on caps spam", "Click to edit", new Item.ClickAction()
        {
            @Override
            public void onClick(Player player)
            {
                CommandTriggerCaps commandTriggerCaps = new CommandTriggerCaps(plugin);
                commandTriggerCaps.showGui(player);
            }
        }));

        //chars actions
        items.add(new Item(28, new ItemStack(Material.WRITTEN_BOOK), "Actions on char spam", "Click to edit", new Item.ClickAction()
        {
            @Override
            public void onClick(Player player)
            {
                CommandTriggerChars commandTriggerChars = new CommandTriggerChars(plugin);
                commandTriggerChars.showGui(player);
            }
        }));

        items.add(Item.getSpacerItem(29));

        //flood actions
        items.add(new Item(30, new ItemStack(Material.WRITTEN_BOOK), "Actions on flood spam", "Click to edit", new Item.ClickAction()
        {
            @Override
            public void onClick(Player player)
            {
                CommandTriggerFlood commandTriggerFlood = new CommandTriggerFlood(plugin);
                commandTriggerFlood.showGui(player);
            }
        }));

        //similar actions
        items.add(new Item(31, new ItemStack(Material.WRITTEN_BOOK), "Actions on similar spam", "Click to edit", new Item.ClickAction()
        {
            @Override
            public void onClick(Player player)
            {
                CommandTriggerSimilar commandTriggerSimilar = new CommandTriggerSimilar(plugin);
                commandTriggerSimilar.showGui(player);
            }
        }));

        items.add(Item.getSpacerItem(32));
        items.add(Item.getSpacerItem(33));

        //url actions
        items.add(new Item(34, new ItemStack(Material.WRITTEN_BOOK), "Actions on URL spam", "Click to edit", new Item.ClickAction()
        {
            @Override
            public void onClick(Player player)
            {
                CommandTriggerUrls commandTriggerUrls = new CommandTriggerUrls(plugin);
                commandTriggerUrls.showGui(player);
            }
        }));

        //blacklist actions
        items.add(new Item(35, new ItemStack(Material.WRITTEN_BOOK), "Actions on blacklist spam", "Click to edit", new Item.ClickAction()
        {
            @Override
            public void onClick(Player player)
            {
                CommandTriggerBlacklist commandTriggerBlacklist = new CommandTriggerBlacklist(plugin);
                commandTriggerBlacklist.showGui(player);
            }
        }));

        plugin.getGuiManager().openInventoryGui(player, "Spamfilter Management", items);
    }

}

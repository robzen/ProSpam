package de.rob1n.prospam.cmd.specific;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.cmd.Command;
import de.rob1n.prospam.gui.Item;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

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
        //open filter gui if parameters are missing
        if (isPlayer(sender) && parameter.length <= 1)
        {
            CommandFilters commandFilters = new CommandFilters((plugin));
            commandFilters.execute(sender, new String[0]);
            return;
        }

        if (parameter.length <= 1) throw new IllegalArgumentException();

        int vNumber = 0;
        List<String> cmds;

        try
        {
            vNumber = Integer.parseInt(parameter[1]);
            parameter[1] = ""; //strip number
        }
        catch (NumberFormatException ignored) { }

        if (vNumber < 0) throw new IllegalArgumentException();

        parameter[0] = ""; //strip "trigger-caps"
        cmds = getTriggerCmds(parameter);

        saveInSettings(vNumber, cmds);

        if (cmds.size() > 0) sender.sendMessage(ProSpam.prefixed("Trigger successfully modified"));
        else sender.sendMessage(ProSpam.prefixed("Trigger successfully removed"));

        if (!settings.save()) sender.sendMessage(ProSpam.prefixed("Could not save state in the config file!"));
    }

    private List<String> getTriggerCmds(final String[] params)
    {
        final String paramString = StringUtils.join(params, " ");
        final String[] cmds = paramString.split(",");

        List<String> cmdList = new ArrayList<String>();

        if (paramString.trim().isEmpty()) return cmdList;

        for (String cmd : cmds)
        {
            cmd = cmd.trim();

            if (cmd.length() <= 0) throw new IllegalArgumentException();

            if (cmd.charAt(0) != '/') cmdList.add("/" + cmd);
            else cmdList.add(cmd);
        }

        return cmdList;
    }

    public void showGui(Player player, final String filter, final HashMap<Integer, List<String>> triggers)
    {
        Set<Item> items = new HashSet<Item>();

        for (int i = 0; i < 9; i = i + 2)
        {
            final int violationCount = (i / 2 + 1) >= 5 ? 0 : i / 2 + 1;

            if(violationCount == 0)
            {
                items.add(new Item(i, new ItemStack(Material.SKULL_ITEM), "EVERY OTHER time", "Executed on all other occasions", Item.NO_CLICK_ACTION));
            }
            else
            {
                items.add(new Item(i, new ItemStack(Material.SKULL_ITEM), "At " + violationCount + ". time", "Executed on " + violationCount + ". violation", Item.NO_CLICK_ACTION));
            }

            items.add(new Item(i + 2 * 9, new ItemStack(Material.BOOK_AND_QUILL), "Edit commands", new Item.ClickAction()
            {
                @Override
                public void onClick(Player player)
                {
                    showEditCommandsGui(player, filter, triggers, violationCount);
                }
            }));
        }

        for (int i = 1; i < 8; i = i + 2)
        {
            items.add(Item.getSpacerItem(i));
            items.add(Item.getSpacerItem(i + 9));
            items.add(Item.getSpacerItem(i + 2 * 9));
        }

        //back item
        items.add(new Item(27, new ItemStack(Material.ENDER_PORTAL), "Back to filter actions", new Item.ClickAction()
        {
            @Override
            public void onClick(Player player)
            {
                CommandFilters commandFilters = new CommandFilters(plugin);
                commandFilters.showGui(player);
            }
        }));

        plugin.getGuiManager().openInventoryGui(player, "Actions - " + filter + " spam", items);
    }

    private void showEditCommandsGui(Player player, final String filter, final HashMap<Integer, List<String>> triggers, final int violationKey)
    {
        Set<Item> items = new HashSet<Item>();

        if(triggers.get(violationKey) != null)
        {
            List<String> cmds = triggers.get(violationKey);
            int iter = 0;

            for (final String cmd : cmds)
            {
                //don't view more than this many commands
                if (iter >= 26)
                {
                    break;
                }

                items.add(new Item(iter++, new ItemStack(Material.YELLOW_FLOWER), cmd, "Click to remove", new Item.ClickAction()
                {
                    @Override
                    public void onClick(Player player)
                    {
                        triggers.get(violationKey).remove(cmd);

                        settings.save();

                        showEditCommandsGui(player, filter, triggers, violationKey);
                    }
                }));
            }
        }

        //add command
        items.add(new Item(35, new ItemStack(Material.NETHER_STAR), "Add command", new Item.ClickAction()
        {
            @Override
            public void onClick(Player player)
            {
                player.closeInventory();

                player.sendMessage(ProSpam.prefixed("Add a command, executed if a player violates the " + filter + " filter the " + violationKey + ". time"));
                player.sendMessage(ProSpam.prefixed("Type " + ChatColor.AQUA + "/prospam " + getName() + " " + violationKey + " <command>"));
                player.sendMessage(ProSpam.prefixed(ChatColor.GRAY + "Example: " + ChatColor.ITALIC + "/prospam " + getName() + " " + violationKey + " /raw Hey {u}, No Spam!"));

                /*FancyMessage fancyMessage = new FancyMessage("CLICK ME ").suggest("/prospam "+getName()+ violationKey + "/").then("to add a new command");
                player.sendRawMessage(fancyMessage.toJSONString());
                fancyMessage = new FancyMessage("Click me").suggest("/prospam "+getName()).then("to go back to the command overview");
                player.sendRawMessage(fancyMessage.toJSONString());*/
            }
        }));

        //back item
        items.add(new Item(27, new ItemStack(Material.ENDER_PORTAL), "Back to filter actions", new Item.ClickAction()
        {
            @Override
            public void onClick(Player player)
            {
                showGui(player, filter, triggers);
            }
        }));

        plugin.getGuiManager().openInventoryGui(player, "Edit - " + filter + " commands", items);
    }
}

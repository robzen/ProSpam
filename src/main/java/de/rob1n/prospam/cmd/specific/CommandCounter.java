package de.rob1n.prospam.cmd.specific;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.chatter.Chatter;
import de.rob1n.prospam.chatter.ChatterHandler;
import de.rob1n.prospam.cmd.Command;
import de.rob1n.prospam.cmd.CommandWithGui;
import de.rob1n.prospam.exception.PlayerNotOnlineException;
import de.rob1n.prospam.gui.Item;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class CommandCounter extends Command implements CommandWithGui
{

	public CommandCounter(ProSpam plugin)
	{
		super(plugin);
	}

	@Override
	public String getName()
	{
		return "counter";
	}

	@Override
	public String getDescription()
	{
		return "Displays spam history of the player. Since last restart";
	}

	@Override
	public String getUsage()
	{
		return "counter <player>";
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

            final Set<Chatter> chatters = ChatterHandler.getChatter(parameter[1]);

            for (Chatter chatter : chatters)
            {
                printStats(sender, chatter);
            }
        }
	}

    @Override
    public void showGui(Player player)
    {
        Set<Item> items = new HashSet<Item>();
        ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (byte)3);

        int iter = 0;
        for(final Player p: Bukkit.getServer().getOnlinePlayers())
        {
            //noinspection deprecation
            items.add(new Item(iter++, itemStack, p.getName(), "Click to print spam statistics", new Item.ClickAction()
            {
                @Override
                public void onClick(Player player)
                {
                    printStats(player, ChatterHandler.getChatter(p.getUniqueId()));
                }
            }));
        }

        plugin.getGuiManager().openInventoryGui(player, "Player spam stats", items);
    }

    private void printStats(CommandSender sender, Chatter chatter)
    {
        String name;
        try
        {
            //noinspection deprecation
            name = chatter.getPlayer().getName();
        }
        catch (PlayerNotOnlineException e)
        {
            //noinspection deprecation
            name = Bukkit.getServer().getOfflinePlayer(chatter.getUUID()).getName();
        }

        sender.sendMessage(ProSpam.prefixed("Spam violations of '"+name + "' since last restart"));
        sender.sendMessage(ProSpam.prefixed(ChatColor.DARK_GRAY+""+chatter.getUUID()));
        sender.sendMessage("|  "+ChatColor.GRAY+"Caps: "+ChatColor.RESET+chatter.getSpamCountCaps());
        sender.sendMessage("|  "+ChatColor.GRAY+"Char spam: "+ChatColor.RESET+chatter.getSpamCountChars());
        sender.sendMessage("|  "+ChatColor.GRAY+"Flood: "+ChatColor.RESET+chatter.getSpamCountFlood());
        sender.sendMessage("|  "+ChatColor.GRAY+"Similar messages: "+ChatColor.RESET+chatter.getSpamCountSimilar());
        sender.sendMessage("|  "+ChatColor.GRAY+"Urls: "+ChatColor.RESET+chatter.getSpamCountUrls());
        sender.sendMessage("|  "+ChatColor.GRAY+"Blacklisted words: "+ChatColor.RESET+chatter.getSpamCountBlacklist());

        final long spamStarted = chatter.getSpamStarted();
        if(spamStarted != 0)
        {
            int nextReset = (int) ((spamStarted + (settings.trigger_counter_reset*1000*60)) - (new Date()).getTime())/(1000*60);

            sender.sendMessage("|  "+ChatColor.DARK_PURPLE+"Reset "+(nextReset > 0 ? "in "+nextReset+" minutes" : "with the next spam"));
        }
    }
}

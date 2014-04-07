package de.rob1n.prospam.gui;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.exception.NotFoundException;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by: robin
 * Date: 09.03.14
 */
public class GuiManager
{
    private Set<InventoryGui> guis = new HashSet<InventoryGui>();

    public GuiManager(ProSpam plugin)
    {
        GuiListener guiListener = new GuiListener();
        Bukkit.getPluginManager().registerEvents(guiListener, plugin);
    }

    private InventoryGui getInventoryGuiById(String id) throws Exception
    {
        for (InventoryGui inventory : guis)
        {
            if(inventory.getId().equals(id))
                return inventory;
        }

        throw new NotFoundException("Inventory not found");
    }

    private boolean isInventoryGui(String id)
    {
        for (InventoryGui inventory : guis)
        {
            if(inventory.getId().equals(id))
                return true;
        }

        return false;
    }

    public boolean openInventoryGui(Player player, String name, Set<Item> items)
    {
        InventoryGui gui = new InventoryGui(name, items);
        gui.open(player);

        return guis.add(gui);
    }

    private class GuiListener implements Listener
    {
        @EventHandler(ignoreCancelled = true)
        public void onInventoryClick(InventoryClickEvent event)
        {
            Inventory inventory = event.getInventory();
            HumanEntity humanEntity = event.getWhoClicked();
            Player player;

            if(!(humanEntity instanceof Player))
                return;

            player = (Player)event.getWhoClicked();

            try
            {
                InventoryGui gui = getInventoryGuiById(HiddenId.grabId(inventory.getTitle()));

                gui.getClickAction(event.getRawSlot()).onClick(player);
                event.setCancelled(true);
            }
            catch (Exception ignored)
            {
                //inventory is not a gui -> do nothing
            }
        }

        @EventHandler
        public void onInventoryClose(InventoryCloseEvent event)
        {
            Inventory inventory = event.getInventory();

            try
            {
                InventoryGui gui = getInventoryGuiById(HiddenId.grabId(inventory.getTitle()));

                guis.remove(gui);
            }
            catch (Exception ignored)
            {
                //inventory is not a gui -> do nothing
            }
        }

        @EventHandler(ignoreCancelled = true)
        public void onInventoryDrag(InventoryDragEvent event)
        {
            Inventory inventory = event.getInventory();

            if(isInventoryGui(HiddenId.grabId(inventory.getTitle())))
            {
                event.setCancelled(true);
            }

        }
    }
}

package de.rob1n.prospam.gui;

import de.rob1n.prospam.ProSpam;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.logging.Level;

/**
 * Created by: robin
 * Date: 08.03.14
 */
public class InventoryGui
{
    public static final int MAX_ITEMS = 4 * 9;
    public static final int MAX_PAGES = 20;
    public static final int NAVIGATION_START = 4 * 9;

    private String id;
    private Map<Integer, Set<Item>> itemMap = new HashMap<Integer, Set<Item>>();
    private List<ItemStack[]> inventories = new ArrayList<ItemStack[]>();
    private Inventory inventory;
    private int activePage;
    private Item navigatePrevious, navigateNext;

    public InventoryGui(String name, Set<Item> items)
    {
        this.id = HiddenId.getNext();
        this.activePage = 0;

        //name can't be longer than 32 chars
        if(name.length() + id.length() > 32)
        {
            name = name.substring(0, 32 - id.length());
        }

        inventory = Bukkit.createInventory(null, MAX_ITEMS + 9, name + id);

        initInventories(items);
        fillInventories(items);

        initNavigationItems();
        addNavigationItemsToInventories();
    }

    public void open(Player player)
    {
        open(0);
        player.openInventory(inventory);
    }

    private void open(int page)
    {
        ItemStack[] itemStacks = inventories.get(page);

        inventory.clear();
        for(int i=0, n=itemStacks.length; i<n; i++)
        {
            inventory.setItem(i, itemStacks[i]);
        }
    }

    public String getId()
    {
        return id;
    }

    public Item.ClickAction getClickAction(int slot)
    {
        Set<Item> items;
        Item.ClickAction dummyClickAction = new Item.ClickAction()
        {
            @Override
            public void onClick(Player player)
            {
                //do nothing
            }
        };

        try
        {
            items = itemMap.get(activePage);
        }
        catch (Exception e)
        {
            ProSpam.log(Level.WARNING, "Couldn't get ClickAction: "+e.getMessage());
            return dummyClickAction;
        }

        for (Item item : items)
        {
            if(item.getSlot() == slot)
                return item.getClickAction();
        }

        return dummyClickAction;
    }

    private void initNavigationItems()
    {
        ItemStack itemStack = new ItemStack(Material.BOOK);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addEnchant(Enchantment.SILK_TOUCH, 1, true);
        itemStack.setItemMeta(itemMeta);

        navigatePrevious = new Item(NAVIGATION_START, itemStack, "Vorherige Seite", new Item.ClickAction()
        {
            @Override
            public void onClick(Player player)
            {
                int previousPage = activePage - 1;
                if(previousPage >= 0)
                {
                    activePage = previousPage;
                    open(activePage);
                }
            }
        });

        itemStack = new ItemStack(itemStack);
        navigateNext = new Item(NAVIGATION_START + 8, itemStack, "Nächste Seite", new Item.ClickAction()
        {
            @Override
            public void onClick(Player player)
            {
                int nextPage = activePage + 1;
                if(nextPage <= inventories.size() - 1)
                {
                    activePage = nextPage;
                    open(activePage);
                }
            }
        });
    }

    private void initInventories(Set<Item> items)
    {
        int max = 0;

        for (Item item : items)
        {
            if(item.getSlot() > max)
                max = item.getSlot();
        }

        for(int i=0, m = max / MAX_ITEMS; i<=m; i++)
        {
            if(i > MAX_PAGES)
                break;

            inventories.add(new ItemStack[MAX_ITEMS + 9]);
            itemMap.put(i, new HashSet<Item>());
        }
    }

    private void fillInventories(Set<Item> items)
    {
        for(Item item : items)
        {
            if(item.getSlot() < 0 || item.getSlot() > MAX_PAGES * MAX_ITEMS)
                break;

            int invNumber = item.getSlot() / MAX_ITEMS;
            int slotNumber = item.getSlot() % MAX_ITEMS;

            try
            {
                itemMap.get(invNumber).add(item);
                inventories.get(invNumber)[slotNumber] = item.getItemStack();
            }
            catch (Exception e)
            {
                ProSpam.log(Level.WARNING, "Error while filling up the GUI: " + e.getMessage());
            }
        }
    }

    private void addNavigationItemsToInventories()
    {
        int page = 0;
        for (ItemStack[] inventory : inventories)
        {
            if(page > 0)
            {
                inventory[navigatePrevious.getSlot()] = navigatePrevious.getItemStack();
                itemMap.get(page).add(navigatePrevious);
            }

            if(page < inventories.size() - 1)
            {
                inventory[navigateNext.getSlot()] = navigateNext.getItemStack();
                itemMap.get(page).add(navigateNext);
            }

            page++;
        }
    }
}

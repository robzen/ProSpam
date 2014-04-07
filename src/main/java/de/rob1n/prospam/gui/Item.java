package de.rob1n.prospam.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

/**
 * Created by: robin
 * Date: 09.03.14
 */
public class Item
{
    public interface ClickAction
    {
        public void onClick(Player player);
    }

    private int slot;
    private ItemStack itemStack;
    private ClickAction clickAction;

    private static final ItemStack spacerItemStack = new ItemStack(Material.STONE_BUTTON);
    public static final NoClickAction NO_CLICK_ACTION = new NoClickAction();

    public int getSlot()
    {
        return slot;
    }

    public ItemStack getItemStack()
    {
        return itemStack;
    }

    public ClickAction getClickAction()
    {
        return clickAction;
    }

    /*public Item(int slot, ItemStack itemStack, ClickAction clickAction)
    {
        this(slot, itemStack, "", clickAction);
    }*/

    public Item(int slot, ItemStack itemStack, String name, ClickAction clickAction)
    {
        this(slot, itemStack, name, "", clickAction);
    }

    public Item(int slot, ItemStack itemStack, String name, String description, ClickAction clickAction)
    {
        this.slot = slot;
        this.itemStack = itemStack == null ? new ItemStack(Material.AIR) : itemStack;
        this.clickAction = clickAction;

        ItemMeta itemMeta = this.itemStack.getItemMeta();

        if(!name.isEmpty())
        {
            if(itemMeta != null)
            {
                itemMeta.setDisplayName(name);
                this.itemStack.setItemMeta(itemMeta);
            }
        }

        if(!description.isEmpty())
        {
            if(itemMeta != null)
            {
                itemMeta.setLore(Arrays.asList(description));
                this.itemStack.setItemMeta(itemMeta);
            }
        }
    }

    public static Item getSpacerItem(int slot)
    {
        return new Item(slot, spacerItemStack, "-", NO_CLICK_ACTION);
    }

    private static class NoClickAction implements Item.ClickAction
    {
        @Override
        public void onClick(Player player)
        {
            //do nothing
        }
    }
}

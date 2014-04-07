package de.rob1n.prospam.gui;

import org.bukkit.ChatColor;

/**
 * Created by: robin
 * Date: 08.03.14
 */
public class HiddenId
{
    private static int counter = 0;

    private static final String[] HIDDEN_NUMBER = {
            /* 0 */ ChatColor.AQUA+"",
            /* 1 */ ChatColor.BLUE+"",
            /* 2 */ ChatColor.DARK_AQUA+"",
            /* 3 */ ChatColor.DARK_GRAY+"",
            /* 4 */ ChatColor.DARK_BLUE+"",
            /* 5 */ ChatColor.DARK_PURPLE+"",
            /* 6 */ ChatColor.DARK_GREEN+"",
            /* 7 */ ChatColor.DARK_RED+"",
            /* 8 */ ChatColor.GOLD+"",
            /* 9 */ ChatColor.GREEN+""
    };

    /**
     * Creates a unique invisible id
     * @return id in form of ChatColors
     */
    public static String getNext()
    {
        StringBuilder sb = new StringBuilder();
        int rest = counter++;

        do
        {
            sb.append(HIDDEN_NUMBER[rest % 10]);
            rest = rest / 10;
        }
        while(rest >= 10);

        return sb.append(ChatColor.RESET).toString();
    }

    /**
     * Extracts the id from the beginning of a string
     * @param txt the string
     * @return the id in form of ChatColors
     */
    public static String grabId(String txt)
    {
        StringBuilder sb = new StringBuilder();

        try
        {
            txt = txt.substring(0, txt.indexOf(ChatColor.RESET+""));

            while(txt.indexOf(ChatColor.COLOR_CHAR) > -1)
            {
                int index = txt.indexOf(ChatColor.COLOR_CHAR);

                sb.append(txt.substring(index, index + 2));
                txt = txt.substring(0, index);
            }
        }
        catch (Exception ignored) { }

        return sb.append(ChatColor.RESET).toString();
    }
/*
    public static String print(String id)
    {
        StringBuilder sb = new StringBuilder();

        sb.append("[");
        while(id.indexOf(ChatColor.COLOR_CHAR) > -1)
        {
            int index = id.indexOf(ChatColor.COLOR_CHAR);

            sb.append(id.substring(index, index + 2)).append("|");
            id = id.substring(0, index);
        }

        return sb.append("]").toString();
    }*/
}

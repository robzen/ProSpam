package de.rob1n.prospam.cmd;

import org.bukkit.entity.Player;

/**
 * Created by: robin
 * Date: 06.04.2014
 */
public interface CommandWithGui extends CommandInterface
{
    public void showGui(Player player);
}

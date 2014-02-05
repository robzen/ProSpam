package de.rob1n.prospam;

import de.rob1n.prospam.chatter.ChatterHandler;
import de.rob1n.prospam.cmd.CommandHandler;
import de.rob1n.prospam.data.DataHandler;
import de.rob1n.prospam.filter.FilterHandler;
import de.rob1n.prospam.listener.ChatListener;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class ProSpam extends JavaPlugin
{
	public static final String VERSION = "0.9.93";
	
	private CommandHandler mCommandHandler = null;
	private DataHandler mDataHandler = null;
	private ChatListener mChatListener = null;
	private FilterHandler mFilterHandler = null;
	private ChatterHandler mChatterHandler = null;
	
	@Override
	public void onEnable()
	{
		mDataHandler = new DataHandler(this);
		mChatterHandler = new ChatterHandler(this);
		mChatListener = new ChatListener(this);
		mFilterHandler = new FilterHandler(this);
		mCommandHandler = new CommandHandler(this);
		
		getServer().getPluginManager().registerEvents(mChatListener, this);
	}
	
	@Override
	public void onDisable()
	{
		mCommandHandler = null;
		mDataHandler = null;
		mChatListener = null;
		mFilterHandler = null;
		mChatterHandler = null;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		return mCommandHandler.execute(sender, label, args);
	}
	
	/**
	 * do not use this with getLogger() - colors wont work
	 * @param message the message
	 * @return colored plugin prefix and the message
	 */
	public String prefixed(final String message)
	{
		return "[" + ChatColor.ITALIC+ChatColor.YELLOW + "ProSpam" + ChatColor.RESET + "] "+ message;
	}
	
	/**
	 * do not use this with getLogger() - colors wont work
	 * @param message the message
	 * @return colored error prefix and the message
	 */
	public String error(final String message)
	{
		return (prefixed("[" + ChatColor.DARK_RED + "Error" + ChatColor.RESET + "] " + message));
	}

	public DataHandler getDataHandler()
	{
		return mDataHandler;
	}

	public FilterHandler getFilterHandler()
	{
		return mFilterHandler;
	}
	
	public ChatterHandler getChatterHandler()
	{
		return mChatterHandler;
	}
	
	public CommandHandler getCommandHandler()
	{
		return mCommandHandler;
	}
}

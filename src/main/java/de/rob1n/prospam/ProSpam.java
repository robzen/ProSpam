package de.rob1n.prospam;

import de.rob1n.prospam.chatter.ChatterHandler;
import de.rob1n.prospam.cmd.CommandHandler;
import de.rob1n.prospam.data.DataHandler;
import de.rob1n.prospam.filter.FilterHandler;
import de.rob1n.prospam.gui.GuiManager;
import de.rob1n.prospam.listener.ChatListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProSpam extends JavaPlugin
{
	public static final String VERSION = "0.9.95";

    public static final String LOG_PREFIX = "[ProSpam] ";
    private static final Logger LOGGER = Logger.getLogger("Minecraft.ProSpam");
	
	private CommandHandler mCommandHandler = null;
	private DataHandler mDataHandler = null;
	private ChatListener mChatListener = null;
	private FilterHandler mFilterHandler = null;
	private ChatterHandler mChatterHandler = null;
    private GuiManager mGuiManager = null;
	
	@Override
	public void onEnable()
	{
        //check if the bukkit version is greater than 1.7.4
        try
        {
            //check if this new method is supported
            Bukkit.getPlayer(new UUID(2, 1));
        }
        catch (NoSuchMethodError e)
        {
            ProSpam.log(Level.SEVERE, "Server version not supported. Please download an earlier version of ProSpam.");
            this.setEnabled(false);
            return;
        }

		mDataHandler = new DataHandler(this);
		mChatterHandler = new ChatterHandler(this);
		mChatListener = new ChatListener(this);
		mFilterHandler = new FilterHandler(this);
		mCommandHandler = new CommandHandler(this);
        mGuiManager = new GuiManager(this);

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
        mGuiManager = null;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		return mCommandHandler.execute(sender/*, label*/, args);
	}
	
	/**
	 * do not use this with getLogger() - colors wont work
	 * @param message the message
	 * @return colored plugin prefix and the message
	 */
	public static String prefixed(final String message)
	{
		return "[" + ChatColor.ITALIC+ChatColor.YELLOW + "ProSpam" + ChatColor.RESET + "] "+ message;
	}
	
	/**
	 * do not use this with getLogger() - colors wont work
	 * @param message the message
	 * @return colored error prefix and the message
	 */
	public static String error(final String message)
	{
		return (prefixed("[" + ChatColor.DARK_RED + "Error" + ChatColor.RESET + "] " + message));
	}

    /**
     * Log all the stuff
     * @param level log level
     * @param txt the text to log
     */
    public static void log(Level level, String txt) { LOGGER.log(level, LOG_PREFIX + txt); }

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

    public GuiManager getGuiManager()
    {
        return mGuiManager;
    }
}

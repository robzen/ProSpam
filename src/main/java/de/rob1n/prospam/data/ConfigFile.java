package de.rob1n.prospam.data;

import de.rob1n.prospam.ProSpam;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public abstract class ConfigFile
{
	protected final ProSpam plugin;
	
	private final String fileName;
	private final File configFile;
	private FileConfiguration fileConfiguration = null;
	
	public ConfigFile(ProSpam plugin, String fileName)
	{
		this.plugin = plugin;
		this.fileName = fileName;
		
		File dataFolder = plugin.getDataFolder();
		if(dataFolder == null)
			throw new IllegalStateException("Data Folder not found");
		
		configFile = new File(plugin.getDataFolder(), fileName);
		
		loadSettings();
		
		//save here to create config files on first start 
		save();
	}
	
	public FileConfiguration getConfig()
	{
		if(fileConfiguration == null)
			load();
		
		return fileConfiguration;
	}
	
	public void load()
	{
		try
		{
			fileConfiguration = YamlConfiguration.loadConfiguration(configFile);
			
			//load defaults from .jar file
			InputStream configStream = plugin.getResource(fileName);
			if(configStream != null)
			{
				YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(configStream);
				fileConfiguration.setDefaults(defaultConfig);
			}
		}
		catch(IllegalArgumentException e)
		{
			plugin.getLogger().severe("Could not load Config File");
		}
	}
	
	protected abstract void loadSettings();
	protected abstract void saveSettings();
	
	public boolean save()
	{
		if(fileConfiguration != null)
		{
			saveSettings();
			
			try
			{
				getConfig().save(configFile);
				return true;
			}
			catch (IOException e)
			{
				plugin.getLogger().severe("Could not save Config File");
			}
		}
		
		return false;
	}
}

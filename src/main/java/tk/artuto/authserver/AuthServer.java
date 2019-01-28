package tk.artuto.authserver;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class AuthServer extends JavaPlugin
{
    private ProtocolManager protocolManager;
    private String pluginTag;

    @Override
    public void onEnable()
    {
        this.pluginTag = getDescription().getName() + " " + getDescription().getVersion();
        getLogger().info("Enabling " + pluginTag + "...");

        // Save config
        saveDefaultConfig();
        initConfig();

        PluginManager plManag = getServer().getPluginManager();

        // Register listeners
        plManag.registerEvents(new PluginListener(this), this);

        // Register commands
        getCommand("authserver").setExecutor(new AuthServerCmd(this));

        // Initialize managers
        this.protocolManager = ProtocolLibrary.getProtocolManager();

        getLogger().info(ChatColor.GREEN + "Enabled " + pluginTag);
    }

    @Override
    public void onDisable()
    {
        getLogger().info("Disabled " + pluginTag);
    }

    private void initConfig()
    {
        File file = new File(getDataFolder(), "config.yml");
        if(!(file.exists()))
        {
            getConfig().options().copyDefaults(true);
            saveConfig();
        }
    }

    ProtocolManager getProtocolManager()
    {
        return protocolManager;
    }
}

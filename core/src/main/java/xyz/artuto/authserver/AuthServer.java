package xyz.artuto.authserver;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.artuto.authserver.interfaces.IScoreboardModule;
import xyz.artuto.authserver.modules.ChatModule;
import xyz.artuto.authserver.modules.DamageModule;
import xyz.artuto.authserver.modules.JoinLeaveMessageModule;
import xyz.artuto.authserver.modules.MovementModule;
import xyz.artuto.authserver.modules.TabListModule;
import xyz.artuto.authserver.v1_12_2.ScoreboardModule_v1_12_2;
import xyz.artuto.authserver.v1_13_2.ScoreboardModule_v1_13_2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("ConstantConditions")
public class AuthServer extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        // Save config
        saveDefaultConfig();

        PluginManager plManag = getServer().getPluginManager();

        // Register modules
        if(!(getConfig().getBoolean("enable_chat")))
        {
            getLogger().info("Enabled chat module");
            plManag.registerEvents(new ChatModule(), this);
        }

        if(!(getConfig().getBoolean("enable_damage")))
        {
            getLogger().info("Enabled damage module");
            plManag.registerEvents(new DamageModule(), this);
        }

        // Group them
        {
            getLogger().info("Enabled join and leave message module");
            plManag.registerEvents(new JoinLeaveMessageModule(this), this);
        }

        if(!(getConfig().getBoolean("enable_movement")))
        {
            getLogger().info("Enabled movement module");
            plManag.registerEvents(new MovementModule(), this);
        }

        if(getConfig().getConfigurationSection("scoreboard").getBoolean("enabled"))
        {
            getLogger().info("Enabled scoreboard module");
            plManag.registerEvents(enableScoreboardModule(), this);
        }

        if(getConfig().getConfigurationSection("tablist").getBoolean("enabled"))
        {
            getLogger().info("Enabled tablist module");
            plManag.registerEvents(new TabListModule(this), this);
        }

        // Register commands
        getCommand("authserver").setExecutor(new AuthServerCmd(this));
    }

    private IScoreboardModule enableScoreboardModule()
    {
        Pattern pattern = Pattern.compile("1.(1\\d).(\\d)?(-R0\\.1)?(-SNAPSHOT)?");
        Matcher matcher = pattern.matcher(getServer().getBukkitVersion());

        getLogger().info("Detecting your Minecraft version...");

        if(!(matcher.matches()))
            throw new IllegalStateException("Incorrect version string.");

        switch(matcher.group(1))
        {
            case "12":
            {
                getLogger().info("Detected Minecraft 1.12");
                return new ScoreboardModule_v1_12_2(); // API changed between 1.12.2 and 1.13.2
            }
            case "13":
            default:
            {
                getLogger().info("Detected Minecraft 1.13+");
                return new ScoreboardModule_v1_13_2();
            }
        }
    }
}

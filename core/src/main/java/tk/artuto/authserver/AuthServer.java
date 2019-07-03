package tk.artuto.authserver;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import tk.artuto.authserver.interfaces.IChatModule;
import tk.artuto.authserver.interfaces.IDamageModule;
import tk.artuto.authserver.interfaces.IJoinLeaveMessageModule;
import tk.artuto.authserver.interfaces.IMovementModule;
import tk.artuto.authserver.interfaces.IScoreboardModule;
import tk.artuto.authserver.interfaces.ITabListModule;
import tk.artuto.authserver.modules.ChatModule_v1_12_2_13_2_14_3;
import tk.artuto.authserver.modules.DamageModule_v1_12_2_13_2_14_3;
import tk.artuto.authserver.modules.JoinLeaveMessageModule_v1_12_2_13_2_14_3;
import tk.artuto.authserver.modules.MovementModule_v1_12_2_13_2_14_3;
import tk.artuto.authserver.modules.TabListModule_v1_12_2_13_2_14_3;
import tk.artuto.authserver.v1_12_2.ScoreboardModule_v1_12_2;
import tk.artuto.authserver.v1_13_2.ScoreboardModule_v1_13_2;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthServer extends JavaPlugin
{
    private String pluginTag;

    // Modules
    private IChatModule chatModule;
    private IDamageModule damageModule;
    private IJoinLeaveMessageModule joinLeaveMessageModule;
    private IMovementModule movementModule;
    private IScoreboardModule scoreboardModule;
    private ITabListModule tabListModule;

    @Override
    public void onEnable()
    {
        this.pluginTag = getDescription().getName() + " " + getDescription().getVersion();
        getLogger().info("Enabling " + pluginTag + "...");

        // Save config
        saveDefaultConfig();
        initConfig();

        PluginManager plManag = getServer().getPluginManager();

        detectVersionAndEnableModules();

        // Register modules
        if(getConfig().getBoolean("enable_chat"))
        {
            getLogger().info("Enabled chat module");
            plManag.registerEvents(chatModule, this);
        }
        else
            this.chatModule = null;

        if(getConfig().getBoolean("enable_damage"))
        {
            getLogger().info("Enabled damage module");
            plManag.registerEvents(damageModule, this);
        }
        else
            this.damageModule = null;

        // Group them
        {
            getLogger().info("Enabled join and leave message module");
            plManag.registerEvents(joinLeaveMessageModule, this);
        }

        if(getConfig().getBoolean("enable_movement"))
        {
            getLogger().info("Enabled movement module");
            plManag.registerEvents(movementModule, this);
        }
        else
            this.movementModule = null;

        //noinspection ConstantConditions
        if(getConfig().getConfigurationSection("scoreboard").getBoolean("enabled"))
        {
            getLogger().info("Enabled scoreboard module");
            plManag.registerEvents(scoreboardModule, this);
        }
        else
            this.scoreboardModule = null;

        //noinspection ConstantConditions
        if(getConfig().getConfigurationSection("tablist").getBoolean("enabled"))
        {
            getLogger().info("Enabled tablist module");
            plManag.registerEvents(tabListModule, this);
        }
        else
            this.tabListModule = null;

        // Register commands
        //noinspection ConstantConditions
        getCommand("authserver").setExecutor(new AuthServerCmd(this));

        getLogger().info("Enabled " + pluginTag);
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

    private void detectVersionAndEnableModules()
    {
        Pattern pattern = Pattern.compile("1.(1\\d).(\\d)?(-R0\\.1)?(-SNAPSHOT)?");
        Matcher matcher = pattern.matcher(getServer().getBukkitVersion());

        getLogger().info("Detecting your Minecraft version...");

        if(!(matcher.matches()))
            throw new IllegalStateException("Incorrect version string.");

        // Common multiversion modules
        this.chatModule = new ChatModule_v1_12_2_13_2_14_3();
        this.damageModule = new DamageModule_v1_12_2_13_2_14_3();
        this.joinLeaveMessageModule = new JoinLeaveMessageModule_v1_12_2_13_2_14_3();
        this.movementModule = new MovementModule_v1_12_2_13_2_14_3();
        this.tabListModule = new TabListModule_v1_12_2_13_2_14_3();

        switch(matcher.group(1))
        {
            case "12":
            {
                getLogger().info("Detected Minecraft 1.12");
                this.scoreboardModule = new ScoreboardModule_v1_12_2(); // API changed between 1.12.2 and 1.13.2
            }
            case "13":
            {
                getLogger().info("Detected Minecraft 1.13");
                this.scoreboardModule = new ScoreboardModule_v1_13_2();
            }
            case "14":
            {
                getLogger().info("Detected Minecraft 1.14");
                this.scoreboardModule = new ScoreboardModule_v1_13_2(); // No API changes here, reuse
            }
            default:
                throw new IllegalStateException("Unsupported Minecraft version.");
        }
    }
}

package tk.artuto.authserver.modules;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import tk.artuto.authserver.interfaces.IJoinLeaveMessageModule;

import java.util.List;

@SuppressWarnings("ConstantConditions") // Can't be null
public class JoinLeaveMessageModule_v1_12_2_13_2_14_3 implements IJoinLeaveMessageModule
{
    private final Plugin plugin = Bukkit.getPluginManager().getPlugin("AuthServer");
    private final ConfigurationSection joinSection = plugin.getConfig().getConfigurationSection("join_message");
    private final ConfigurationSection leaveSection = plugin.getConfig().getConfigurationSection("leave_message");

    @Override
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        String globalMessage = joinSection.getString("global");
        List<String> lines = joinSection.getStringList("message");

        event.setJoinMessage(prepareMessage(globalMessage, player));

        if(!(joinSection.getBoolean("enabled")) || lines.isEmpty())
            return;

        StringBuilder sb = new StringBuilder();
        for(String line : lines)
            sb.append(line).append("\n");

        TextComponent text = new TextComponent(prepareMessage(sb.toString(), player));
        player.sendMessage(text);
    }

    @Override
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        String globalMessage = leaveSection.getString("global");
        event.setQuitMessage(prepareMessage(globalMessage, event.getPlayer()));
    }

    private String prepareMessage(String message, Player player)
    {
        return ChatColor.translateAlternateColorCodes('&', message)
                .replace("%player%", player.getName());
    }
}

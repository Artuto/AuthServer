package xyz.artuto.authserver.modules;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.artuto.authserver.AuthServer;

import java.util.List;

import static net.md_5.bungee.api.ChatColor.translateAlternateColorCodes;

public class JoinLeaveMessageModule implements Listener
{
    private final ConfigurationSection joinSection;
    private final ConfigurationSection leaveSection;

    public JoinLeaveMessageModule(AuthServer plugin)
    {
        this.joinSection = plugin.getConfig().getConfigurationSection("join_message");
        this.leaveSection = plugin.getConfig().getConfigurationSection("leave_message");
    }

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

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        String globalMessage = leaveSection.getString("global");
        event.setQuitMessage(prepareMessage(globalMessage, event.getPlayer()));
    }

    private String prepareMessage(String message, Player player)
    {
        return translateAlternateColorCodes('&', message).replace("%player%", player.getName());
    }
}

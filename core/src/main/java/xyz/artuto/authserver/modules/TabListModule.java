package xyz.artuto.authserver.modules;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import xyz.artuto.authserver.AuthServer;

import java.lang.reflect.InvocationTargetException;

import static com.comphenix.protocol.PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER;
import static com.comphenix.protocol.wrappers.WrappedChatComponent.fromText;
import static net.md_5.bungee.api.ChatColor.translateAlternateColorCodes;

public class TabListModule implements Listener
{
    private final ConfigurationSection section;
    private final ProtocolManager protocolManager;

    public TabListModule(AuthServer plugin)
    {
        this.section = plugin.getConfig().getConfigurationSection("tablist");
        this.protocolManager = ProtocolLibrary.getProtocolManager();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws InvocationTargetException
    {
        PacketContainer pc = protocolManager.createPacket(PLAYER_LIST_HEADER_FOOTER);
        String header = section.getString("header", "");
        String footer = section.getString("footer", "");

        pc.getChatComponents()
                .write(0, fromText(prepareMessage(header, event.getPlayer())))
                .write(1, fromText(prepareMessage(footer, event.getPlayer())));

        protocolManager.sendServerPacket(event.getPlayer(), pc);
    }

    private String prepareMessage(String message, Player player)
    {
        return translateAlternateColorCodes('&', message).replace("%player%", player.getName());
    }
}

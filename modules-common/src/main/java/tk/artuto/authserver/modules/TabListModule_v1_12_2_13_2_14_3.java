package tk.artuto.authserver.modules;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import tk.artuto.authserver.interfaces.ITabListModule;

import java.lang.reflect.InvocationTargetException;

@SuppressWarnings("ConstantConditions") // Can't be null
public class TabListModule_v1_12_2_13_2_14_3 implements ITabListModule
{
    private final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
    private final Plugin plugin = Bukkit.getPluginManager().getPlugin("AuthServer");
    private final ConfigurationSection section = plugin.getConfig().getConfigurationSection("tablist");

    @Override
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) throws InvocationTargetException
    {
        // if(!(section.getBoolean("enabled")))
        //    return;

        PacketContainer pc = protocolManager.createPacket(PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);
        String header = section.getString("header", "");
        String footer = section.getString("footer", "");

        pc.getChatComponents()
                .write(0, WrappedChatComponent.fromText(prepareMessage(header, event.getPlayer())))
                .write(1, WrappedChatComponent.fromText(prepareMessage(footer, event.getPlayer())));

        protocolManager.sendServerPacket(event.getPlayer(), pc);
    }

    private String prepareMessage(String message, Player player)
    {
        return ChatColor.translateAlternateColorCodes('&', message)
                .replace("%player%", player.getName());
    }
}

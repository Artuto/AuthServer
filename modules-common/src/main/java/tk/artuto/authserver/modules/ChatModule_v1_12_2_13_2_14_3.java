package tk.artuto.authserver.modules;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;
import tk.artuto.authserver.interfaces.IChatModule;

// @SuppressWarnings("ConstantConditions") // Can't be null
public class ChatModule_v1_12_2_13_2_14_3 implements IChatModule
{
    // private final Plugin plugin = Bukkit.getPluginManager().getPlugin("AuthServer");

    @Override
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerChat(AsyncPlayerChatEvent event)
    {
        Player player = event.getPlayer();

        // if(plugin.getConfig().getBoolean("enable_chat"))
        //    return;

        if(player.getGameMode() == GameMode.CREATIVE)
            return;

        event.setCancelled(true);
    }
}

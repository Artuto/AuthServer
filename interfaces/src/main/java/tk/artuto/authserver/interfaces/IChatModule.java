package tk.artuto.authserver.interfaces;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public interface IChatModule extends Listener
{
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    void onPlayerChat(AsyncPlayerChatEvent event);
}

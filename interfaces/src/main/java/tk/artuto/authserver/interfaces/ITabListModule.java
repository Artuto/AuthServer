package tk.artuto.authserver.interfaces;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.lang.reflect.InvocationTargetException;

public interface ITabListModule extends Listener
{
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    void onPlayerJoin(PlayerJoinEvent event) throws InvocationTargetException;
}

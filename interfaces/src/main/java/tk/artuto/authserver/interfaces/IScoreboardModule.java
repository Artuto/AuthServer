package tk.artuto.authserver.interfaces;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public interface IScoreboardModule extends Listener
{
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    void onPlayerJoin(PlayerJoinEvent event);
}

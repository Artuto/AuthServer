package xyz.artuto.authserver.interfaces;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public interface IScoreboardModule extends Listener
{
    @EventHandler
    void onPlayerJoin(PlayerJoinEvent event);
}

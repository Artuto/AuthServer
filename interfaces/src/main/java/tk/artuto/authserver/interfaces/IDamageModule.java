package tk.artuto.authserver.interfaces;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public interface IDamageModule extends Listener
{
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    void onEntityDamage(EntityDamageEvent event);

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    void onFoodLevelChange(FoodLevelChangeEvent event);
}

package tk.artuto.authserver.modules;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.plugin.Plugin;
import tk.artuto.authserver.interfaces.IDamageModule;

// @SuppressWarnings("ConstantConditions") // Can't be null
public class DamageModule_v1_12_2_13_2_14_3 implements IDamageModule
{
    // private final Plugin plugin = Bukkit.getPluginManager().getPlugin("AuthServer");

    @Override
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent event)
    {
        if(!(event.getEntityType() == EntityType.PLAYER))
            return;

        event.setCancelled(true /*!(plugin.getConfig().getBoolean("enable_damage"))*/);
    }

    @Override
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onFoodLevelChange(FoodLevelChangeEvent event)
    {
        if(!(event.getEntityType() == EntityType.PLAYER))
            return;

        event.setCancelled(true /*!(plugin.getConfig().getBoolean("enable_damage"))*/);
    }
}

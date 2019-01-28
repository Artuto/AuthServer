package tk.artuto.authserver;

import org.bukkit.ChatColor;

class Utils
{
    static String fixColors(String string)
    {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}

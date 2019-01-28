package tk.artuto.authserver;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;

class Utils
{
    static String fixColors(String string)
    {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    static PacketContainer getTablist()
    {
        AuthServer plugin = JavaPlugin.getPlugin(AuthServer.class);
        PacketContainer pc = plugin.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);

        String header = plugin.getConfig().getString("tablist.header", "");
        String footer = plugin.getConfig().getString("tablist.footer", "");

        pc.getChatComponents().write(0, WrappedChatComponent.fromText(fixColors(header)))
                .write(1, WrappedChatComponent.fromText(fixColors(footer)));

        return pc;
    }

    static void sendPacket(Player player, PacketContainer pc)
    {
        try
        {
            AuthServer plugin = JavaPlugin.getPlugin(AuthServer.class);
            plugin.getProtocolManager().sendServerPacket(player, pc);
        }
        catch(InvocationTargetException e)
        {
            e.printStackTrace();
        }
    }

    static Scoreboard getScoreboard()
    {
        AuthServer plugin = JavaPlugin.getPlugin(AuthServer.class);
        ScoreboardManager manager = Bukkit.getScoreboardManager();

        String name = fixColors(plugin.getConfig().getString("scoreboard.title", "AuthServer"));

        Scoreboard board = manager.getNewScoreboard();
        Objective obj = board.registerNewObjective("Kills", "Player", name);
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        List<String> lines = plugin.getConfig().getStringList("scoreboard.lines");
        Collections.reverse(lines);

        for(int i = 0; i < lines.size(); i++)
            obj.getScore(fixColors(lines.get(i))).setScore(i);

        return board;
    }
}

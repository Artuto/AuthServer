package tk.artuto.authserver.v1_12_2;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import tk.artuto.authserver.interfaces.IScoreboardModule;

import java.util.Collections;
import java.util.List;

public class ScoreboardModule_v1_12_2 implements IScoreboardModule
{
    private final Plugin plugin = Bukkit.getPluginManager().getPlugin("AuthServer");
    private final ConfigurationSection section = plugin.getConfig().getConfigurationSection("scoreboard");

    @Override
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        if(!(section.getBoolean("enabled")))
            return;

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Player player = event.getPlayer();

        String name = prepareMessage(section.getString("title", "AuthServer"), player);

        Scoreboard board = manager.getNewScoreboard();
        Objective obj = board.registerNewObjective("Kills", "Player");
        obj.setDisplayName(name);
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        List<String> lines = section.getStringList("lines");
        Collections.reverse(lines);

        for(int i = 0; i < lines.size(); i++)
            obj.getScore(prepareMessage(lines.get(i), player)).setScore(i);

        player.setScoreboard(board);
    }

    private String prepareMessage(String message, Player player)
    {
        return ChatColor.translateAlternateColorCodes('&', message)
                .replace("%player%", player.getName());
    }
}
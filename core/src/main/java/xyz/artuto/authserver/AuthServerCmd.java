package xyz.artuto.authserver;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

@SuppressWarnings("NullableProblems")
public class AuthServerCmd implements CommandExecutor
{
    private final AuthServer plugin;

    AuthServerCmd(AuthServer plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(!(args.length > 0))
            return false;

        if(args[0].toLowerCase().equals("reload"))
        {
            plugin.getServer().getPluginManager().disablePlugin(plugin);
            plugin.getServer().getPluginManager().enablePlugin(plugin);
            plugin.reloadConfig();
            sender.sendMessage(ChatColor.GREEN + "Successfully reloaded plugin!");
            return true;
        }

        return false;
    }
}


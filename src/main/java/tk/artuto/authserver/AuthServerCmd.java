package tk.artuto.authserver;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

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
        if(!(sender.hasPermission("authserver.command")))
        {
            sender.sendMessage(ChatColor.RED + "You don't have permission!");
            return true;
        }

        if(!(args.length > 0))
            return false;

        if(args[0].toLowerCase().equals("reload"))
        {
            plugin.reloadConfig();
            sender.sendMessage(ChatColor.GREEN + "Successfully reloaded config!");
            return true;
        }

        return false;
    }
}


package me.oyuncozucu.mjet.commands;

import me.oyuncozucu.mjet.MJet;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(sender instanceof Player)
        {
            Player player = (Player) sender;
            if(player.hasPermission("mjet.admin"))
            {
                if(args.length == 1 && args[0].equalsIgnoreCase("reload"))
                {
                    MJet.getInstance().reloadConfig();
                    player.sendMessage(ChatColor.GREEN + "Yapılandırma dosyası yenilendi!");
                }
            }
        }

        return true;
    }
}

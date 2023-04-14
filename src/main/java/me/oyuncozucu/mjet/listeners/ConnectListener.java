package me.oyuncozucu.mjet.listeners;

import me.oyuncozucu.mjet.utils.JetUtils;
import me.oyuncozucu.mjet.MJet;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e)
    {
        Player player = e.getPlayer();
        for(String enable : MJet.getInstance().getConfig().getStringList("enable-worlds"))
        {
            if(player.getWorld().getName().equalsIgnoreCase(enable))
            {
                int slot = MJet.getInstance().getConfig().getInt("item-slot");
                player.getInventory().setItem(slot, JetUtils.isJet());
            }
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e)
    {
        Player player = e.getPlayer();
        for(String enable : MJet.getInstance().getConfig().getStringList("enable-worlds"))
        {
            if (player.getWorld().getName().equalsIgnoreCase(enable))
            {
                player.getInventory().removeItem(JetUtils.isJet());
            }
        }
    }

}

package me.oyuncozucu.mjet.listeners;

import me.oyuncozucu.mjet.utils.JetUtils;
import me.oyuncozucu.mjet.MJet;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class BugListener implements Listener {

    @EventHandler
    public void onJet(PlayerInteractEvent e)
    {
        Player player = e.getPlayer();
        String itemName = MJet.getInstance().getConfig().getString("item-name");
        for(String enable : MJet.getInstance().getConfig().getStringList("enable-worlds"))
        {
            if(player.getWorld().getName().equals(enable))
            {
                if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&',itemName)))
                {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent e)
    {
        for(String enable : MJet.getInstance().getConfig().getStringList("enable-worlds")) {
            int slot = MJet.getInstance().getConfig().getInt("item-slot");
            if(!e.getPlayer().getWorld().getName().equals(enable))
            {
                e.getPlayer().getInventory().setItem(slot, null);
            }
            else
            {
                e.getPlayer().getInventory().setItem(slot, JetUtils.isJet());
            }
        }
    }
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {

        Player player = (Player) e.getWhoClicked();
        String itemName = MJet.getInstance().getConfig().getString("item-name");
        if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', itemName))) {
            e.setCancelled(true);
            player.closeInventory();
        }

    }


}

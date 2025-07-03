package me.oyuncozucu.mjet.listeners;

import me.oyuncozucu.mjet.MJet;
import me.oyuncozucu.mjet.utils.JetUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BugListener implements Listener {

    @EventHandler
    public void onJet(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemStack itemInHand = player.getInventory().getItemInHand();
        if (itemInHand == null || itemInHand.getType() == Material.AIR || !itemInHand.hasItemMeta()) {
            return;
        }

        ItemMeta itemMeta = itemInHand.getItemMeta();
        if (!itemMeta.hasDisplayName()) {
            return;
        }

        String itemName = MJet.getInstance().getConfig().getString("item-name");
        String formattedItemName = ChatColor.translateAlternateColorCodes('&', itemName);

        for (String enable : MJet.getInstance().getConfig().getStringList("enable-worlds")) {
            if (player.getWorld().getName().equals(enable)) {
                if (itemMeta.getDisplayName().equalsIgnoreCase(formattedItemName)) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent e) {
        Player player = e.getPlayer();
        int slot = MJet.getInstance().getConfig().getInt("item-slot");
        boolean isInEnabledWorld = false;

        for (String enable : MJet.getInstance().getConfig().getStringList("enable-worlds")) {
            if (player.getWorld().getName().equalsIgnoreCase(enable)) {
                isInEnabledWorld = true;
                break;
            }
        }

        if (isInEnabledWorld) {
            player.getInventory().setItem(slot, JetUtils.isJet());
        } else {
            // Sadece jet ise silmek daha güvenli
            ItemStack currentItem = player.getInventory().getItem(slot);
            if (currentItem != null && currentItem.isSimilar(JetUtils.isJet())) {
                player.getInventory().setItem(slot, null);
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        ItemStack clickedItem = e.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() == Material.AIR || !clickedItem.hasItemMeta()) {
            return;
        }

        ItemMeta itemMeta = clickedItem.getItemMeta();
        if (!itemMeta.hasDisplayName()) {
            return;
        }

        Player player = (Player) e.getWhoClicked();
        String itemName = MJet.getInstance().getConfig().getString("item-name");
        String formattedItemName = ChatColor.translateAlternateColorCodes('&', itemName);

        if (itemMeta.getDisplayName().equalsIgnoreCase(formattedItemName)) {
            e.setCancelled(true);
            player.closeInventory();
        }
    }
}

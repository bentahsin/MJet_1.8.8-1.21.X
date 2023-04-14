package me.oyuncozucu.mjet.utils;

import me.oyuncozucu.mjet.MJet;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class JetUtils {

    public static ItemStack isJet() {

        ItemStack jet = new ItemStack(Material.FIREWORK_ROCKET);
        ItemMeta jetMeta = jet.getItemMeta();
        String displayName = MJet.getInstance().getConfig().getString("item-name");
        jetMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',displayName));
        jet.setItemMeta(jetMeta);

        return jet;
    }
}

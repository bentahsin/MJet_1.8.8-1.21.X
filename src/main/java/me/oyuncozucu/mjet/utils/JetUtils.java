package me.oyuncozucu.mjet.utils;

import com.cryptomorin.xseries.XMaterial; // İçe aktar
import me.oyuncozucu.mjet.MJet;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Optional;

public class JetUtils {

    public static ItemStack isJet() {
        ItemStack jet = XMaterial.FIREWORK_ROCKET.parseItem();

        if (jet == null) {
            return null;
        }

        ItemMeta jetMeta = jet.getItemMeta();
        String displayName = MJet.getInstance().getConfig().getString("item-name");
        jetMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
        jet.setItemMeta(jetMeta);

        return jet;
    }
}

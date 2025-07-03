package me.oyuncozucu.mjet.listeners;

import com.cryptomorin.xseries.XSound;
import me.oyuncozucu.mjet.MJet;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JetListener implements Listener {

    private final Map<UUID, Long> rightClickCooldown = new HashMap<>();
    private final Map<UUID, Long> leftClickCooldown = new HashMap<>();
    private static final int COOLDOWN_SECONDS = 3;
    private final MJet plugin;

    public JetListener(MJet plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJetInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        Action action = e.getAction();

        ItemStack itemInHand = player.getInventory().getItemInHand();
        if (itemInHand == null || itemInHand.getType() == Material.AIR || !itemInHand.hasItemMeta() || !itemInHand.getItemMeta().hasDisplayName()) {
            return;
        }

        String configItemName = MJet.getInstance().getConfig().getString("item-name");
        String formattedItemName = ChatColor.translateAlternateColorCodes('&', configItemName);

        if (!itemInHand.getItemMeta().getDisplayName().equalsIgnoreCase(formattedItemName)) {
            return;
        }

        boolean inEnabledWorld = MJet.getInstance().getConfig().getStringList("enable-worlds").stream()
                .anyMatch(worldName -> player.getWorld().getName().equalsIgnoreCase(worldName));

        if (!inEnabledWorld) {
            return;
        }

        // Sağ Tıklama
        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            handleRightClick(player);
        }
        // Sol Tıklama
        else if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
            handleLeftClick(player);
        }
    }

    private void handleRightClick(Player player) {
        if (rightClickCooldown.containsKey(player.getUniqueId())) {
            return;
        }

        XSound.ENTITY_ENDER_DRAGON_SHOOT.play(player);

        Vector playerDir = player.getEyeLocation().getDirection();
        Vector launchVector = playerDir.clone().multiply(-0.3).setY(1.5);
        player.setVelocity(launchVector);

        rightClickCooldown.put(player.getUniqueId(), System.currentTimeMillis() + (COOLDOWN_SECONDS * 1000L));

        new BukkitRunnable() {
            @Override
            public void run() {
                rightClickCooldown.remove(player.getUniqueId());
            }
        }.runTaskLater(plugin, COOLDOWN_SECONDS * 20L);
    }

    private void handleLeftClick(Player player) {
        if (leftClickCooldown.containsKey(player.getUniqueId())) {
            return;
        }

        XSound.ENTITY_ENDER_DRAGON_SHOOT.play(player);

        player.setVelocity(player.getLocation().getDirection().multiply(10));

        leftClickCooldown.put(player.getUniqueId(), System.currentTimeMillis() + (COOLDOWN_SECONDS * 1000L));

        new BukkitRunnable() {
            @Override
            public void run() {
                leftClickCooldown.remove(player.getUniqueId());
            }
        }.runTaskLater(plugin, COOLDOWN_SECONDS * 20L);
    }
}

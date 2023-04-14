package me.oyuncozucu.mjet.listeners;

import me.oyuncozucu.mjet.MJet;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JetListener implements Listener {

    private Map<UUID, Long> rightTimes = new HashMap<>();
    private Map<UUID, Long> leftTimes = new HashMap<>();
    private final int RIGHT_DURATION_SECONDS = 3;
    private final int LEFT_DURATION_SECONDS = 3;
    private MJet plugin;

    public JetListener(MJet plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onRightClickJet(PlayerInteractEvent e)
    {
        Player player = e.getPlayer();
        String itemName = MJet.getInstance().getConfig().getString("item-name");
        for(String enable : MJet.getInstance().getConfig().getStringList("enable-worlds"))
        {
            if(e.getPlayer().getWorld().getName().equals(enable))
            {
                if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)
                {
                    if(e.getPlayer().getInventory().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&',itemName)))
                    {
                        if (rightTimes.containsKey(player.getUniqueId())) {
                            return;
                        }
                        e.getPlayer().playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_SHOOT, 10F, 20);
                        Vector playerDir = player.getEyeLocation().getDirection().setY(player.getLocation().getY());
                        Vector otherDir = playerDir.clone().multiply(-0.3);
                        otherDir.setY(1.5);
                        player.setVelocity(otherDir);

                        rightTimes.put(player.getUniqueId(), System.currentTimeMillis() + (RIGHT_DURATION_SECONDS * 1000));
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                e.setCancelled(true);
                                rightTimes.remove(player.getUniqueId());
                            }
                        }.runTaskLater(plugin, RIGHT_DURATION_SECONDS * 20);
                        e.setCancelled(false);
                    }
                }
            }
        }

    }

    @EventHandler
    public void onLeftClickJet(PlayerInteractEvent e)
    {
        Player player = e.getPlayer();
        String itemName = MJet.getInstance().getConfig().getString("item-name");
        for(String enable : MJet.getInstance().getConfig().getStringList("enable-worlds"))
        {
            if(e.getPlayer().getWorld().getName().equals(enable))
            {
                if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK)
                {
                    if(e.getPlayer().getInventory().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&',itemName)))
                    {
                        if (leftTimes.containsKey(player.getUniqueId())) {
                            return;
                        }

                        e.getPlayer().playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_SHOOT, 10F, 20);
                        Vector playerDir = player.getEyeLocation().getDirection();
                        Vector otherDir = playerDir.clone().multiply(-0.1);
                        otherDir.setY(0.1);

                        player.setVelocity(player.getLocation().getDirection().multiply(10));

                        leftTimes.put(player.getUniqueId(), System.currentTimeMillis() + (LEFT_DURATION_SECONDS * 1000));
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                e.setCancelled(true);
                                leftTimes.remove(player.getUniqueId());
                            }
                        }.runTaskLater(plugin, LEFT_DURATION_SECONDS * 20);
                        e.setCancelled(false);
                    }
                }
            }
        }

    }

}

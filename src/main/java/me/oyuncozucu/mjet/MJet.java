package me.oyuncozucu.mjet;

import me.oyuncozucu.mjet.commands.ReloadCommand;
import me.oyuncozucu.mjet.listeners.BugListener;
import me.oyuncozucu.mjet.listeners.ConnectListener;
import me.oyuncozucu.mjet.listeners.JetListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class MJet extends JavaPlugin {

    private static MJet instance;

    public static MJet getInstance()
    {
        return instance;
    }

    @Override
    public void onEnable()
    {
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        instance = this;

        getCommand("mjet").setExecutor(new ReloadCommand());

        registerEvents();
    }

    public void registerEvents()
    {
        Bukkit.getPluginManager().registerEvents(new ConnectListener(), this);
        Bukkit.getPluginManager().registerEvents(new JetListener(this), this);
        Bukkit.getPluginManager().registerEvents(new BugListener(), this);
    }

}

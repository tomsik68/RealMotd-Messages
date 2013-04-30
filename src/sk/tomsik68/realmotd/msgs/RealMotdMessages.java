package sk.tomsik68.realmotd.msgs;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.PluginEvent;
import org.bukkit.plugin.java.JavaPlugin;

import sk.tomsik68.realmotd.RealMotd;
import sk.tomsik68.realmotd.Util;
import sk.tomsik68.realmotd.api.MotdManager;

public class RealMotdMessages extends JavaPlugin implements Listener {
    private MotdManager manager;

    private Logger log;

    @Override
    public void onEnable() {
        super.onEnable();

        log = getLogger();
        manager = RealMotd.handler;
        if (!getDataFolder().exists()) {
            log.info("Creating default files...");
            getDataFolder().mkdir();
            try {
                new File(getDataFolder(), "join.txt").createNewFile();
                Util.writeFile(new File(getDataFolder(), "join.txt"), ChatColor.YELLOW + "%player% joined the game");
                new File(getDataFolder(), "quit.txt").createNewFile();
                Util.writeFile(new File(getDataFolder(), "quit.txt"), ChatColor.YELLOW + "%player% left the game");
            } catch (IOException e) {
                log.severe("File creation failed.");
                e.printStackTrace();
            }
        }
        boolean join = true, quit = true;
        if (!new File(getDataFolder(), "config.yml").exists()) {
            log.info("Creating configuration file...");
            YamlConfiguration config = YamlConfiguration.loadConfiguration(getResource("defconfig.yml"));
            try {
                config.save(new File(getDataFolder(), "config.yml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        reloadConfig();
        join = getConfig().getBoolean("join");
        quit = getConfig().getBoolean("quit");
        getServer().getPluginManager().registerEvents(new MessageListener(getDataFolder(), join, quit,manager), this);
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPluginDisable(PluginDisableEvent event) {
        onPluginChange(event);
    }

    @EventHandler
    public void onPluginEnable(PluginEnableEvent event) {
        onPluginChange(event);
    }

    public void onPluginChange(PluginEvent event) {
        if (getServer().getPluginManager().getPlugin("RealMotd") != null && getServer().getPluginManager().isPluginEnabled("RealMotd")) {
            if (manager == null)
                manager = RealMotd.handler;
        } else {
            manager = null;
            getLogger().severe("RealMotd not found. Disabling...");
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    public static RealMotd getRealMotd() {
        return (RealMotd) Bukkit.getPluginManager().getPlugin("RealMotd");
    }
}

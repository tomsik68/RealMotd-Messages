package sk.tomsik68.realmotd.msgs;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.PluginEvent;
import org.bukkit.plugin.java.JavaPlugin;

import sk.tomsik68.realmotd.RealMotd;
import sk.tomsik68.realmotd.api.FilesManager;
import sk.tomsik68.realmotd.api.MotdManager;

public class RealMotdMessages extends JavaPlugin implements Listener {
    private MotdManager manager;
    private FilesManager joinMessages = null, firstJoinMessages = null, quitMessages = null;
    private Logger log;
    private ConfigFile config;
    private SectionalConfig join, fJoin, quit;

    @Override
    public void onEnable() {
        super.onEnable();
        config = new ConfigFile(new File(getDataFolder(), "config.yml"));
        try {
            config.load(this);
        } catch (Exception e) {
            log.severe("Couldn't load configuration file!");
            e.printStackTrace();
        }
        log = getLogger();
        manager = RealMotd.handler;
        join = config.getSection("join");
        fJoin = config.getSection("first-join");
        quit = config.getSection("quit");
        if (join.isEnabled())
            joinMessages = new FilesManager(config.getSection("join"), new File(getDataFolder(), "messages"), "join", "motd", "txt");
        if (fJoin.isEnabled())
            firstJoinMessages = new FilesManager(config.getSection("first-join"), new File(getDataFolder(), "messages"), "first-join", "motd", "txt");
        if (quit.isEnabled())
            quitMessages = new FilesManager(config.getSection("quit"), new File(getDataFolder(), "messages"), "quit", "motd", "txt");

        getServer().getPluginManager().registerEvents(new MessageListener(getDataFolder(), firstJoinMessages, joinMessages, quitMessages, manager),
                this);
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
        if (getRealMotd() != null && getRealMotd().isEnabled()) {
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

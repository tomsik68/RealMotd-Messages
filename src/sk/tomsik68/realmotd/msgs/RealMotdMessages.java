package sk.tomsik68.realmotd.msgs;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import sk.tomsik68.realmotd.RealMotd;
import sk.tomsik68.realmotd.Util;

public class RealMotdMessages extends JavaPlugin {
    private ConfigFile config;

    @Override
    public void onEnable() {
        config = new ConfigFile(getDataFolder());
        try {
            config.load(this);

        } catch (Exception e) {
            getLogger().severe("Failed to load configuration :(");
            e.printStackTrace();
        }
        try {
            getLogger().info("Creating default files...");
            createDefaultFiles();
        } catch (Exception e) {
            getLogger().severe("Failed to create default files :(");
            e.printStackTrace();
        }
        MessageListener listener = new MessageListener(this, config);
        getServer().getPluginManager().registerEvents(listener, this);
    }

    private void createDefaultFiles() throws IOException {
        File dataFolder = getDataFolder();

        String[] msgs = new String[] {
                "join", "firstjoin", "leave"
        };
        for (String msg : msgs) {
            File dir = new File(dataFolder, msg);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File defaultFile = new File(dir, msg.concat(".txt"));
            if (!defaultFile.exists()) {
                Util.writeFile(defaultFile, "This is default message. Please change this file:", defaultFile.getPath());
            }

        }
    }

    public static RealMotd getRealMotd() {
        return (RealMotd) Bukkit.getPluginManager().getPlugin("RealMotd");
    }

    public static Plugin getInstance() {
        return Bukkit.getPluginManager().getPlugin("RealMotd-Messages");
    }
}

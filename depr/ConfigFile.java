package sk.tomsik68.realmotd.msgs;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import sk.tomsik68.realmotd.api.IConfig;

public class ConfigFile {
    private FileConfiguration config;
    private final File configFile;

    public ConfigFile(File file) {
        configFile = file;
    }

    public void load(Plugin plugin) throws Exception {
        if (configFile.exists())
            config = YamlConfiguration.loadConfiguration(configFile);
        else {
            config = YamlConfiguration.loadConfiguration(plugin.getResource("defconfig.yml"));
            save();
        }
    }

    public SectionalConfig getSection(String section) {
        return new SectionalConfig(config.getConfigurationSection(section));
    }

    public void save() throws Exception {
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

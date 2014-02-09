package sk.tomsik68.realmotd.msgs;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigFile {
    private FileConfiguration config;
    private final File configFile;

    public ConfigFile(File dataFolder) {
        configFile = new File(dataFolder, "config.yml");
    }

    public void load(Plugin plugin) throws Exception {
        if (configFile.exists()) {
            config = YamlConfiguration.loadConfiguration(configFile);
        } else {
            config = YamlConfiguration.loadConfiguration(plugin.getResource("defconfig.yml"));
            save();
        }
    }

    public ConfigSection getSection(String key) {
        return new ConfigSection(config.getConfigurationSection(key));
    }

    public void save() throws Exception {
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            configFile.createNewFile();
        }
        config.save(configFile);
    }
}

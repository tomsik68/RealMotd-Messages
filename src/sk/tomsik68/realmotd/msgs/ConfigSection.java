package sk.tomsik68.realmotd.msgs;

import org.bukkit.configuration.ConfigurationSection;

import sk.tomsik68.realmotd.api.EMotdMode;
import sk.tomsik68.realmotd.api.IConfig;

public class ConfigSection implements IConfig {
    private final ConfigurationSection cs;

    public ConfigSection(ConfigurationSection cs) {
        this.cs = cs;
    }

    @Override
    public boolean isGroupSpecific() {
        return cs.getBoolean("group-specific");
    }

    @Override
    public boolean isWorldSpecific() {
        return cs.getBoolean("world-specific");
    }

    public boolean isEnabled() {
        return cs.getBoolean("enabled");
    }

    public EMotdMode getMode() {
        return EMotdMode.valueOf(cs.getString("mode").toUpperCase());
    }
    
    public long getDelay(){
        return cs.getLong("delay");
    }

}

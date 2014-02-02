package sk.tomsik68.realmotd.msgs;

import org.bukkit.configuration.ConfigurationSection;

import sk.tomsik68.realmotd.api.IConfig;

public class SectionalConfig implements IConfig {
    private final ConfigurationSection section;

    public SectionalConfig(ConfigurationSection cs) {
        section = cs;
    }

    @Override
    public boolean isGroupSpecific() {
        return section.getBoolean("group-specific");
    }

    @Override
    public boolean isWorldSpecific() {
        return section.getBoolean("world-specific");
    }

    public boolean isEnabled() {
        return section.getBoolean("enabled");
    }

}

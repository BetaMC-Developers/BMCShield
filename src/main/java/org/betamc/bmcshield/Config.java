package org.betamc.bmcshield;

import org.bukkit.util.config.Configuration;

import java.io.File;

public class Config extends Configuration {

    public Config(File configFile) {
        super(configFile);
        this.reload();
    }

    private void write() {
        generateConfigOption("settings.kick-message.value", "§cPlease open a ticket at betamc.org/discord to play with this username.");
        generateConfigOption("settings.kick-message.info", "The message sent to players in the BMCShield list. Can't be longer than 100 characters.");
    }

    public void reload() {
        this.load();
        this.write();
        this.save();
    }

    public void generateConfigOption(String key, Object defaultValue) {
        if (this.getProperty(key) == null) {
            this.setProperty(key, defaultValue);
        }
        final Object value = this.getProperty(key);
        this.removeProperty(key);
        this.setProperty(key, value);
    }

    public Object getConfigOption(String key) {
        return this.getProperty(key);
    }

    public String getConfigString(String key) {
        return String.valueOf(getConfigOption(key));
    }
}

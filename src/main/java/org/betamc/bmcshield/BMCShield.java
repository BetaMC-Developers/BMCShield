package org.betamc.bmcshield;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.betamc.bmcshield.commands.BMCShieldCommand;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Logger;

public class BMCShield extends JavaPlugin {

    private static BMCShield INSTANCE;
    private static Config config;
    private static File file;
    private static ArrayList<String> kickList;
    private Logger log;
    private String pluginName;
    private PluginDescriptionFile pdf;

    @Override
    public void onEnable() {
        INSTANCE = this;
        log = getServer().getLogger();
        pdf = getDescription();
        pluginName = pdf.getName();
        log.info("[" + pluginName + "] Is Loading, Version: " + pdf.getVersion());

        config = new Config(new File(getDataFolder(), "config.yml"));
        loadKickList();
        getCommand("bmcshield").setExecutor(new BMCShieldCommand());
        getServer().getPluginManager().registerEvents(new LoginListener(), this);

        log.info("[" + pluginName + "] Is Loaded, Version: " + pdf.getVersion());
    }

    @Override
    public void onDisable() {
        log.info("[" + pluginName + "] Is Unloaded, Version: " + pdf.getVersion());
    }

    public static void loadKickList() {
        file = new File(INSTANCE.getDataFolder(), "list.txt");
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            kickList = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty()) kickList.add(line.toLowerCase().trim());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveKickList() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String name : kickList) {
                writer.write(name.toLowerCase());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Config getConfig() {
        return config;
    }

    public static ArrayList<String> getKickList() {
        return kickList;
    }
}

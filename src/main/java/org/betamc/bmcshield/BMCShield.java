package org.betamc.bmcshield;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.betamc.bmcshield.commands.BMCShieldCommand;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BMCShield extends JavaPlugin {
    private JavaPlugin plugin;
    private Logger log;
    private String pluginName;
    private PluginDescriptionFile pdf;
    private Config configuration;
    private static File file;
    private ArrayList<String> readerList;


    public void loadListFile() {
        BufferedReader fileReader;
        file = new File(getDataFolder(), "list.txt");
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            fileReader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        readerList = new ArrayList<>();
        String line;
        while(true) {
            try {
                if ((line = fileReader.readLine()) == null) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            readerList.add(line);
        }
    }

    @Override
    public void onEnable() {
        plugin = this;
        log = this.getServer().getLogger();
        pdf = this.getDescription();
        pluginName = pdf.getName();
        log.info("[" + pluginName + "] Is Loading, Version: " + pdf.getVersion());

        // Load configuration
        configuration = new Config(this, new File(getDataFolder(), "config.yml")); // Load the configuration file from the plugin's data folder

        loadListFile();

        // Register the commands
        getCommand("bmcshield").setExecutor(new BMCShieldCommand(this));

        // Register the listeners
        LoginListener listener = new LoginListener(this);
        getServer().getPluginManager().registerEvents(listener, this);

        log.info("[" + pluginName + "] Is Loaded, Version: " + pdf.getVersion());
    }

    @Override
    public void onDisable() {
        log.info("[" + pluginName + "] Is Unloading, Version: " + pdf.getVersion());

        // Save configuration
        //config.save(); // Save the configuration file to disk. This should only be necessary if the configuration cam be modified during runtime.

        log.info("[" + pluginName + "] Is Unloaded, Version: " + pdf.getVersion());
    }

    public void logger(Level level, String message) {
        Bukkit.getLogger().log(level, "[" + plugin.getDescription().getName() + "] " + message);
    }

    public Config getConfig() { return configuration; }
    public ArrayList<String> getReaderList() { return readerList; }
    public File getListFile() { return file; }
}

package net.karolek.drop.common;

import net.karolek.drop.KarolekDrop;
import net.karolek.drop.utils.IoUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

public class ConfigFile extends DropObject {

    private final String fileName;
    private final File configFile;
    private FileConfiguration config;

    public ConfigFile(String fileName) {
        super(KarolekDrop.getPlugin());
        this.fileName = fileName;
        this.configFile = new File(getPlugin().getDataFolder(), fileName);
    }

    public File getConfigFile() {
        if (!configFile.exists()) {
            getPlugin().getLogger().log(Level.INFO, "The file " + fileName + " does not exist. I make one for you");
            configFile.getParentFile().mkdirs();
            InputStream is = getPlugin().getResource(fileName);
            if (is != null) {
                getPlugin().getLogger().log(Level.INFO, "Copying " + fileName + " file from jar!");
                IoUtil.copy(is, configFile);
            }
        }
        return configFile;
    }

    public FileConfiguration getConfig() {
        if (config == null)
            loadConfig();
        return config;
    }

    public void loadConfig() {
        config = YamlConfiguration.loadConfiguration(getConfigFile());
    }

    public void saveConfig() {
        try {
            getConfig().save(getConfigFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadConfig() {
        loadConfig();
        saveConfig();
    }

}

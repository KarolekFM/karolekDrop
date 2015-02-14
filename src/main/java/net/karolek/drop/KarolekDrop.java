package net.karolek.drop;

import lombok.Getter;
import net.karolek.drop.base.Drop;
import net.karolek.drop.commands.executors.StoneExecutor;
import net.karolek.drop.common.Containable;
import net.karolek.drop.listeners.BlockBreakListener;
import net.karolek.drop.listeners.InventoryClickListener;
import net.karolek.drop.listeners.PlayerInteractListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class KarolekDrop extends JavaPlugin implements Containable {

    @Getter
    private static KarolekDrop plugin;

    private DropManager dropManager;

    @Override
    public void onLoad() {
        plugin = this;
    }

    @Override
    public void onEnable() {
        setup();
    }

    @Override
    public void onDisable() {

        dispose();
        plugin = null;
    }

    @Override
    public void setup() {
        saveDefaultConfig();
        Config.reloadConfig();
        dropManager = new DropManager(this);

        Bukkit.getPluginManager().registerEvents(new BlockBreakListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(this), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);

        new StoneExecutor().register();

    }

    @Override
    public void dispose() {
        dropManager.dispose();
        dropManager = null;
    }

    static {
        ConfigurationSerialization.registerClass(Drop.class, "Drop");
    }
}

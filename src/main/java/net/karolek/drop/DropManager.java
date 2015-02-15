package net.karolek.drop;

import lombok.Getter;
import net.karolek.drop.base.Drop;
import net.karolek.drop.base.DropMask;
import net.karolek.drop.base.masks.CancelDropMask;
import net.karolek.drop.base.masks.NormalDropMask;
import net.karolek.drop.base.masks.StoneDropMask;
import net.karolek.drop.common.ConfigFile;
import net.karolek.drop.common.Containable;
import net.karolek.drop.common.DropObject;
import net.karolek.drop.compare.Compare;
import net.karolek.drop.compare.Compares;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.*;

@Getter
public class DropManager extends DropObject implements Containable {

    private final ConfigFile configFile;

    private HashMap<Material, DropMask> dropMasks;
    private List<Drop> randomDrops;

    public DropManager(KarolekDrop plugin) {
        super(plugin);
        this.configFile = new ConfigFile("drops.yml");
        setup();
    }

    private FileConfiguration getConfig() {
        return getConfigFile().getConfig();
    }

    public DropMask getDropMask(Material material) {
        DropMask mask = dropMasks.get(material);
        if (mask == null)
            mask = NormalDropMask.INSTANCE;
        return mask;
    }

    public void reloadManager() {
        getConfigFile().reloadConfig();
        dispose();
        setup();
    }

    @Override
    public void setup() {
        dropMasks = new HashMap<>();
        randomDrops = new ArrayList<>();
        for (String s : getConfig().getStringList("drops.cancel"))
            dropMasks.put(Material.matchMaterial(s), CancelDropMask.INSTANCE);
        dropMasks.put(Material.STONE, new StoneDropMask(getPlugin()));
        randomDrops.addAll((Collection<? extends Drop>) getConfig().getList("drops.random"));
        if (randomDrops.size() < 1) {
            randomDrops.add(new Drop("Diament", "karolekdrop.diament", new ItemStack(Material.DIAMOND), "&7Trafiles na &3diament&7!", 1.15, 7, true, true, Compares.parseString("<30"), Compares.parseString("1-2"), Compares.parseString("10-25"), Arrays.asList(Material.DIAMOND_PICKAXE)));
            getConfig().set("drops.random", randomDrops);
            getConfigFile().saveConfig();
        }
        //Drop toAdd = new Drop("Diament", new ItemStack(Material.DIAMOND), "&7Trafiles na: &3diament&7!", 10.10D, 10, true, Compare.parseString(">10"), Compare.parseString("1-3"), Compare.parseString("10-25"), Arrays.asList(ItemTool.DIAMOND_PICKAXE, ItemTool.IRON_PICKAXE));


    }

    @Override
    public void dispose() {
        dropMasks.clear();
        dropMasks = null;
        randomDrops.clear();
        randomDrops = null;
    }
}

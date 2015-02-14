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
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

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
            mask = new NormalDropMask(getPlugin());
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
            dropMasks.put(Material.matchMaterial(s), new CancelDropMask(getPlugin()));
        dropMasks.put(Material.STONE, new StoneDropMask(getPlugin()));
        randomDrops.addAll((Collection<? extends Drop>) getConfig().getList("drops.random"));

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

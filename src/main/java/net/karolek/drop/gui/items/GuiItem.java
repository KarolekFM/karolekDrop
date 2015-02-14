package net.karolek.drop.gui.items;

import lombok.Getter;
import lombok.Setter;
import net.karolek.drop.KarolekDrop;
import net.karolek.drop.common.DropObject;
import net.karolek.drop.gui.ItemClickEvent;
import net.karolek.drop.utils.Util;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class GuiItem extends DropObject {

    protected final String name;
    protected final List<String> lores;
    protected final ItemStack icon;

    public GuiItem(String name, List<String> lores, ItemStack icon) {
        super(KarolekDrop.getPlugin());
        this.name = Util.fixColor(name);
        this.lores = Util.fixColor(lores);
        this.icon = icon;
    }

    public GuiItem(String name, ItemStack icon, String... lores) {
        this(name, Arrays.asList(lores), icon);
    }

    public void onItemClick(ItemClickEvent event) {
    }

    public ItemStack setItemMeta(ItemStack icon) {
        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lores);
        icon.setItemMeta(meta);
        return icon;
    }

    public ItemStack getFinalIcon(Player viever) {
        return setItemMeta(getIcon().clone());
    }

}

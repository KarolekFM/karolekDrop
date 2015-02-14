package net.karolek.drop.gui.items;

import net.karolek.drop.Config;
import net.karolek.drop.base.Drop;
import net.karolek.drop.gui.DropGuiMenu;
import net.karolek.drop.gui.ItemClickEvent;
import net.karolek.drop.utils.Util;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class DropGuiItem extends GuiItem {

    private final Drop drop;
    private final DropGuiMenu menu;

    public DropGuiItem(Drop drop, DropGuiMenu menu) {
        super(drop.getName(), drop.getItem());
        this.drop = drop;
        this.menu = menu;
    }

    @Override
    public ItemStack getFinalIcon(Player viever) {
        ItemStack icon = getIcon().clone();
        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName(Util.fixColor(drop.getName()));
        List<String> lores = new ArrayList<>();
        double bonus = drop.getChanceBonus(viever.getName());
        lores.add(replace("szansa", drop.getChance() + "%" + (bonus > 0D ? " + " + bonus + "%" : "")));
        if (drop.getExp() > 0)
            lores.add(replace("doswiadczenie", drop.getExp()));
        lores.add(replace("fortune", drop.isFortune() ? "tak" : "nie"));
        if (drop.getHeight() != null)
            lores.add(replace("wypada na poziomie", drop.getHeight().getParse()));
        if (drop.getAmount() != null)
            lores.add(replace("wypada w ilosci", drop.getAmount().getParse()));
        lores.add(replace("wydobyc mozna", StringUtils.join(drop.getTools(), ", ")));
        lores.add(replace("aktywny", drop.isDisabled(viever.getName()) ? "nie" : "tak"));
        meta.setLore(Util.fixColor(lores));
        icon.setItemMeta(meta);
        return icon;
    }

    @Override
    public void onItemClick(ItemClickEvent event) {

        drop.changeStatus(event.getPlayer().getName());
        menu.update(event.getPlayer());

    }

    private String replace(String key, Object value) {
        return Config.FORMAT_GUI_LORE.replace("{KEY}", key).replace("{VALUE}", String.valueOf(value));
    }
}

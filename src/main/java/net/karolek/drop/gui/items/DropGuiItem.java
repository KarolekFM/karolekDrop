package net.karolek.drop.gui.items;

import net.karolek.drop.Config;
import net.karolek.drop.base.Drop;
import net.karolek.drop.gui.DropGuiMenu;
import net.karolek.drop.gui.ItemClickEvent;
import net.karolek.drop.utils.MessagesUtil;
import net.karolek.drop.utils.Util;
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

        for (String lore : Config.GUI_ICON_LORE)
            lores.add(MessagesUtil.replace(lore, drop, viever));

        meta.setLore(Util.fixColor(lores));
        icon.setItemMeta(meta);
        return icon;
    }

    @Override
    public void onItemClick(ItemClickEvent event) {

        drop.changeStatus(event.getPlayer().getName());
        menu.update(event.getPlayer());

        if (Config.GUI_CLOSE$AFTER$CLICK)
            event.setWillClose(true);

    }

}

package net.karolek.drop.gui;

import net.karolek.drop.Config;
import net.karolek.drop.KarolekDrop;
import net.karolek.drop.base.Drop;
import net.karolek.drop.gui.items.DropGuiItem;
import net.karolek.drop.utils.Util;

public class DropGuiMenu extends GuiMenu {

    public DropGuiMenu() {
        super(Util.fixColor(Config.FORMAT_GUI_NAME), Size.fit(KarolekDrop.getPlugin().getDropManager().getRandomDrops().size()));
        int pos = 0;
        for (Drop drop : getDropManager().getRandomDrops()) {
            setItem(pos, new DropGuiItem(drop, this));
            pos++;
        }
    }


}

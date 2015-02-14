package net.karolek.drop.common;

import lombok.Getter;
import net.karolek.drop.DropManager;
import net.karolek.drop.KarolekDrop;

@Getter
public class DropObject {

    protected final KarolekDrop plugin;

    public DropObject(KarolekDrop plugin) {
        this.plugin = plugin;
    }

    protected final DropManager getDropManager() {
        return getPlugin().getDropManager();
    }
}

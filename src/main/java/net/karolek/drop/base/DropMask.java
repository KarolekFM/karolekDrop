package net.karolek.drop.base;

import net.karolek.drop.KarolekDrop;
import net.karolek.drop.common.DropObject;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class DropMask extends DropObject {

    public DropMask(KarolekDrop plugin) {
        super(plugin);
    }

    public abstract void breakBlock(Player player, ItemStack tool, Block block);

}

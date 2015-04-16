package net.karolek.drop.base.masks;

import net.karolek.drop.KarolekDrop;
import net.karolek.drop.base.DropMask;
import net.karolek.drop.utils.DropUtil;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class TypedDropMask extends DropMask {

    private ItemStack droppedStack;

    public TypedDropMask(KarolekDrop plugin, ItemStack droppedStack) {
        super(plugin);
        this.droppedStack = droppedStack;
    }

    @Override public void breakBlock(Player player, ItemStack tool, Block block) {
        DropUtil.recalculateDurability(player, tool);
        DropUtil.addItemsToPlayer(player, Arrays.asList(droppedStack), block);
    }
}

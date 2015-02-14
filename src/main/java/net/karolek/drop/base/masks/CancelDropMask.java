package net.karolek.drop.base.masks;

import net.karolek.drop.Config;
import net.karolek.drop.KarolekDrop;
import net.karolek.drop.base.DropMask;
import net.karolek.drop.utils.Util;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CancelDropMask extends DropMask {

    public CancelDropMask(KarolekDrop plugin) {
        super(plugin);
    }

    @Override
    public void breakBlock(Player player, ItemStack tool, Block block) {
        Util.sendMsg(player, Config.MESSAGES_CANCELDROP);
    }


}

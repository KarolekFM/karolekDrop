package net.karolek.drop.listeners;

import net.karolek.drop.Config;
import net.karolek.drop.KarolekDrop;
import net.karolek.drop.base.DropMask;
import net.karolek.drop.common.DropObject;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BlockBreakListener extends DropObject implements Listener {

    public BlockBreakListener(KarolekDrop plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        Player p = event.getPlayer();
        Block b = event.getBlock();
        ItemStack tool = p.getItemInHand();

        if (!Config.GAMEMODE_DROP && p.getGameMode() == GameMode.CREATIVE) return;

        DropMask mask = getDropManager().getDropMask(b.getType());
        mask.breakBlock(p, tool, b);
        b.setType(Material.AIR);
        event.setCancelled(true);
    }
}

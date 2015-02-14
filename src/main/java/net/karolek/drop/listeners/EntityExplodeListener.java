package net.karolek.drop.listeners;

import net.karolek.drop.KarolekDrop;
import net.karolek.drop.base.DropMask;
import net.karolek.drop.base.masks.CancelDropMask;
import net.karolek.drop.common.DropObject;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.Iterator;

public class EntityExplodeListener extends DropObject implements Listener {

    public EntityExplodeListener(KarolekDrop plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntityExplode(final EntityExplodeEvent event) {
        final Iterator<Block> blocks = event.blockList().iterator();
        Block b = null;
        while (blocks.hasNext()) {
            b = blocks.next();
            DropMask mask = getDropManager().getDropMask(b.getType());
            if (mask instanceof CancelDropMask)
                b.setType(Material.AIR);
        }
    }

}

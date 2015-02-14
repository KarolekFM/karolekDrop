package net.karolek.drop.listeners;

import net.karolek.drop.KarolekDrop;
import net.karolek.drop.common.DropObject;
import net.karolek.drop.utils.Util;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener extends DropObject implements Listener {
    public PlayerInteractListener(KarolekDrop plugin) {
        super(plugin);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        if (!e.getPlayer().isOp()) return;

        if (e.getPlayer().getItemInHand().getType() != Material.STICK) return;

        Block b = e.getClickedBlock();
        if (b == null) return;

        Util.sendMsg(e.getPlayer(), "&7" + b.getType() + "(" + b.getTypeId() + ":" + b.getData() + ")");

    }
}

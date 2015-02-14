package net.karolek.drop.listeners;

import net.karolek.drop.gui.GuiHolder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player && event.getInventory().getHolder() instanceof GuiHolder) {
            event.setCancelled(true);
            ((GuiHolder) event.getInventory().getHolder()).getMenu().onInventoryClick(event);
        }
    }

}

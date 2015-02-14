package net.karolek.drop.gui;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class GuiHolder implements InventoryHolder {

    private final GuiMenu menu;
    private final Inventory inventory;

    public GuiHolder(GuiMenu menu, Inventory inventory) {
        this.menu = menu;
        this.inventory = inventory;
    }

    public GuiMenu getMenu() {
        return menu;
    }

    @Override
    public Inventory getInventory() {
        return null;
    }
}

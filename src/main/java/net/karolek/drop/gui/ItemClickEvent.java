package net.karolek.drop.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;


public class ItemClickEvent {
    private final Player player;
    private final ClickType clickType;
    private boolean goBack = false;
    private boolean close = false;
    private boolean update = false;

    public ItemClickEvent(Player player, ClickType type) {
        this.player = player;
        this.clickType = type;
    }

    public Player getPlayer() {
        return player;
    }

    public ClickType getClickType() {
        return clickType;
    }

    public boolean willGoBack() {
        return goBack;
    }

    public void setWillGoBack(boolean goBack) {
        this.goBack = goBack;
        if (goBack) {
            close = false;
            update = false;
        }
    }

    public boolean willClose() {
        return close;
    }

    public void setWillClose(boolean close) {
        this.close = close;
        if (close) {
            goBack = false;
            update = false;
        }
    }

    public boolean willUpdate() {
        return update;
    }

    public void setWillUpdate(boolean update) {
        this.update = update;
        if (update) {
            goBack = false;
            close = false;
        }
    }
}

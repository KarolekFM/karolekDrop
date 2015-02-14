package net.karolek.drop.gui;

import net.karolek.drop.KarolekDrop;
import net.karolek.drop.common.DropObject;
import net.karolek.drop.gui.items.GuiItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class GuiMenu extends DropObject {

    protected final String name;
    protected final Size size;
    protected GuiItem[] items;
    protected GuiMenu parent;


    //private static final MenuItem EMPTY_SLOT_ITEM = new StaticMenuItem(" ", new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.GRAY.getData()));

    public GuiMenu(String name, Size size, GuiMenu parent) {
        super(KarolekDrop.getPlugin());
        this.name = name;
        this.size = size;
        this.items = new GuiItem[size.getSize()];
        this.parent = parent;
    }

    public GuiMenu(String name, Size size) {
        this(name, size, null);
    }

    public String getName() {
        return name;
    }

    public Size getSize() {
        return size;
    }

    public boolean hasParent() {
        return parent != null;
    }

    public GuiMenu getParent() {
        return parent;
    }

    public void setParent(GuiMenu parent) {
        this.parent = parent;
    }

    public GuiMenu setItem(int position, GuiItem menuItem) {
        items[position] = menuItem;
        return this;
    }

    public GuiMenu fillEmptySlots(GuiItem menuItem) {
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {
                items[i] = menuItem;
            }
        }
        return this;
    }

    public GuiMenu fillEmptySlots() {
        return fillEmptySlots(null);
    }


    public void open(Player player) {
        Inventory inventory = Bukkit.createInventory(new GuiHolder(this, Bukkit.createInventory(player, size.getSize())), size.getSize(), name);
        apply(inventory, player);
        player.openInventory(inventory);
    }


    public void update(Player player) {
        if (player.getOpenInventory() == null)
            return;

        Inventory inventory = player.getOpenInventory().getTopInventory();

        if (!(inventory.getHolder() instanceof GuiHolder) || !((GuiHolder) inventory.getHolder()).getMenu().equals(this))
            return;

        apply(inventory, player);
        player.updateInventory();
    }

    private void apply(Inventory inventory, Player player) {
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) continue;
            if (items[i] != null) {
                inventory.setItem(i, items[i].getFinalIcon(player));
            }
        }
    }

    public void onInventoryClick(InventoryClickEvent event) {
        int slot = event.getRawSlot();
        if (slot >= 0 && slot < size.getSize() && items[slot] != null) {
            Player player = (Player) event.getWhoClicked();
            ItemClickEvent itemClickEvent = new ItemClickEvent(player, event.getClick());
            items[slot].onItemClick(itemClickEvent);
            if (itemClickEvent.willUpdate()) {
                update(player);
            } else {
                player.updateInventory();
                if (itemClickEvent.willClose() || itemClickEvent.willGoBack()) {
                    player.closeInventory();
                }
                if (itemClickEvent.willGoBack() && hasParent()) {
                    parent.open(player);
                }
            }
        }

    }

    public enum Size {
        ONE_LINE(9),
        TWO_LINE(18),
        THREE_LINE(27),
        FOUR_LINE(36),
        FIVE_LINE(45),
        SIX_LINE(54);

        private final int size;

        private Size(int size) {
            this.size = size;
        }

        public static Size fit(int slots) {
            if (slots < 10) {
                return ONE_LINE;
            } else if (slots < 19) {
                return TWO_LINE;
            } else if (slots < 28) {
                return THREE_LINE;
            } else if (slots < 37) {
                return FOUR_LINE;
            } else if (slots < 46) {
                return FIVE_LINE;
            } else {
                return SIX_LINE;
            }
        }

        public int getSize() {
            return size;
        }
    }

}
